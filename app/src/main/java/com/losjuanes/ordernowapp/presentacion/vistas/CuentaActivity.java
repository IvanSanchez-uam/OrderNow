package com.losjuanes.ordernowapp.presentacion.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaCuenta;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorCuenta;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorCuenta;
import com.losjuanes.ordernowapp.presentacion.vistas.adaptadores_personalizados.CustomListViewCuenta;

public class CuentaActivity extends AppCompatActivity implements View.OnClickListener{

    ControladorCuenta controlador;

    Context context = this;

    //private LinearLayout llCuenta;
    private ListView lvCuenta;
    private TextView tvMonto;
    private Button btnPagar;

    private int numOrden, numPersonas;

    private RespuestaCuenta respuestaCuenta;
    private int respuestaPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        //Navegacion hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controlador = new ImplementacionControladorCuenta();

        //llCuenta = (LinearLayout) findViewById(R.id.llCuenta);
        lvCuenta = (ListView) findViewById(R.id.lvCuenta);
        tvMonto = (TextView) findViewById(R.id.tvMonto);
        btnPagar = (Button) findViewById(R.id.btn_pagar);

        btnPagar.setEnabled(false);
        btnPagar.setOnClickListener(this);

        /** Obtenemos el numero de orden y numero de personas desde las preferencias. **/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        numOrden = pref.getInt("num_orden", 0);
        Log.d("prefOrdenPersonas", "numOrden = " + numOrden);
        numPersonas = pref.getInt("num_personas", 0);
        Log.d("prefOrdenPersonas", "numPersonas = " + numPersonas);

        int x = 16;
        Cuenta obtenerCuenta = new Cuenta();
        obtenerCuenta.execute(x);//Como solo es un parametro se puede enviar como tal
                                        // sin necesidad de crear un arreglo (new int[]{numPersonasHilo})

    }

    @Override
    public void onClick(View v) {
        int x = 16;

        Pagar pagarCueta = new Pagar();
        pagarCueta.execute(x);

    }


    /**
     * Esta clase permitira que la parte de la conexion con la Api REST se ejecute en un hilo diferente
     * Este hilo sera el encargado de obtener el detalle de la cuenta.
     */
    private class Cuenta extends AsyncTask<Integer, Void, String>{

        /**
         * Este metodo contendra lo que queremos que ejecute el hilo.
         * @param params, parametros que se le envian al hilo (numOrden en este caso).
         * @return el parametro que se le envia al metodo onPostExecute()
         */
        @Override
        protected String doInBackground(Integer... params) {

            int numOrden = 0;
            String msjRespuestaApiREST = "";

            //Parametros que se le pasan al hilo.
            if (params.length > 0){
                numOrden = params[0];//Se obtiene el parametro que se le envia al hilo.
            }

            try {
                respuestaCuenta = controlador.obtenerCuenta(numOrden);

                if ((respuestaCuenta.getProductos().size() == 0)) {
                    msjRespuestaApiREST = getResources().getString(R.string.msj_numOrdenNoAsignado);
                }
            } catch (Exception e) {
                msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                e.printStackTrace();
            }

            return msjRespuestaApiREST;
        }

        /**
         * Este metodo se ejecuta inmediatamente despues de que el hilo encargado de la conexion
         * finaliza su ejecucion y este metodo lo ejecuta el hilo principal.
         *
         * @param msjRespuestaApiREST, mensaje a mostrar al usuario.
         */
        @Override
        protected void onPostExecute(String msjRespuestaApiREST) {
            if (msjRespuestaApiREST.compareTo("") != 0){
                Toast.makeText(context, msjRespuestaApiREST, Toast.LENGTH_LONG).show();
            }else {

                btnPagar.setEnabled(true);
                tvMonto.setText( "$" + respuestaCuenta.getMonto() + " MXN.");

                CustomListViewCuenta adapter = new CustomListViewCuenta(context, numPersonas, respuestaCuenta.getProductos());
                lvCuenta.setAdapter(adapter);


            }
        }
    }

    /**
     * Esta clase permitira que la parte de la conexion con la Api REST se ejecute en un hilo diferente
     * Este hilo sera el encargado de pagar la orden.
     */
    private class Pagar extends AsyncTask<Integer, Void, String>{

        /**
         * Este metodo contendra lo que queremos que ejecute el hilo.
         * @param params, parametros que se le envian al hilo (numOrden en este caso).
         * @return el parametro que se le envia al metodo onPostExecute()
         */
        @Override
        protected String doInBackground(Integer... params) {

            int numOrden = 0;
            String msjRespuestaApiREST = "";

            //Parametros que se le pasan al hilo.
            if (params.length > 0){
                numOrden = params[0];//Se obtiene el parametro que se le envia al hilo.
            }

            try {
                respuestaPagar = controlador.pagar(numOrden);

                if (respuestaPagar != 200) {
                    msjRespuestaApiREST = getResources().getString(R.string.msj_numOrdenNoAsignado);
                }
            } catch (Exception e) {
                msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                e.printStackTrace();
            }

            return msjRespuestaApiREST;
        }

        /**
         * Este metodo se ejecuta inmediatamente despues de que el hilo encargado de la conexion
         * finaliza su ejecucion y este metodo lo ejecuta el hilo principal.
         *
         * @param msjRespuestaApiREST, mensaje a mostrar al usuario.
         */
        @Override
        protected void onPostExecute(String msjRespuestaApiREST) {
            if (msjRespuestaApiREST.compareTo("") != 0){
                Toast.makeText(context, msjRespuestaApiREST, Toast.LENGTH_LONG).show();
            }else{
                if(respuestaPagar == 200){
                    controlador.iniciaActivity(CuentaActivity.this, BienvenidaActivity.class);
                }
            }
        }
    }

}
