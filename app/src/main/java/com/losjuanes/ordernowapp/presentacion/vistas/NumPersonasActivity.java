package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorIndicarNumPersonas;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorIndicarNumPersonas;

import java.io.IOException;

public class NumPersonasActivity extends AppCompatActivity  implements View.OnClickListener{
    private TextView tvNumPersonas;
    private Button btnContinuar;
    private FloatingActionButton btnMas, btnMenos;

    private int numPersonas = 1;

    private ControladorIndicarNumPersonas controlador;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numpersonas);

        controlador = new ImplementacionControladorIndicarNumPersonas();

        tvNumPersonas = (TextView) findViewById(R.id.tv_numPersonas);
        btnMas = (FloatingActionButton) findViewById(R.id.btnMas);
        btnMenos = (FloatingActionButton) findViewById(R.id.btnMenos);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);

        btnMas.setOnClickListener(this);
        btnMenos.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);
    }

    /**
     * Método de la interface OnClickListener
     * @param v, vista que acciona el listener
     */
    @Override
    public void onClick(View v) {
        int numPersonasHilo;

        switch (v.getId()){
            case R.id.btnMas: //Botón Mas
                numPersonas += 1;
                tvNumPersonas.setText(String.valueOf(numPersonas));
                break;
            case R.id.btnMenos: //Botón Menos
                if(numPersonas > 1) {
                    numPersonas -= 1;
                    tvNumPersonas.setText(String.valueOf(numPersonas));
                }
                break;
            case R.id.btnContinuar: //Botón Continuar
                numPersonasHilo = Integer.parseInt(String.valueOf(tvNumPersonas.getText()));

                /**
                 * Ejecutamos el hilo que se encargara de la conexion con la Api REST.
                 * Se le manda al hilo el numPersonas el cual fue asignado por el usuario.
                 */
                ObtenerNumOrden obtenerNumOrden = new ObtenerNumOrden();
                obtenerNumOrden.execute(numPersonasHilo);//Como solo es un parametro se puede enviar como tal
                                                         // sin necesidad de crear un arreglo (new int[]{numPersonasHilo})
                break;
        }
    }

    /**
     * Esta clase permitira que la parte de la conexion con la Api REST se ejecute en un hilo diferente
     */
    private class ObtenerNumOrden extends AsyncTask<Integer,Void,String> {

        /**
         * Este metodo se ejecuta inmediatamente despues de que el hilo finaliza su ejecucion.
         *
         * @param msjRespuestaApiREST, mensaje a mostrar al usuario.
         */
        @Override
        protected void onPostExecute(String msjRespuestaApiREST) {
            if (msjRespuestaApiREST.compareTo("") != 0){
                Toast.makeText(activity, msjRespuestaApiREST, Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Este metodo contendra lo que queremos que ejecute el hilo.
         * @param params, parametros que se le envian al hilo.
         * @return el parametro que se le envia al metodo onPostExecute()
         */
        @Override
        protected String doInBackground(Integer... params) {
            int numPersonas = 0;
            int respuestaNumOrden;
            String msjRespuestaApiREST = "";

            //Parametros que se le pasan al hilo.
            if (params.length > 0){
                numPersonas = params[0];//Se obtiene el parametro que se le envia al hilo.
            }

            try {
                respuestaNumOrden = controlador.obtener_numOrden(numPersonas,activity);
                Log.d("NumPersonas",String.valueOf(numPersonas));

                if (respuestaNumOrden != -1){
                    SharedPreferences preferenciasMesa = activity.getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
                    SharedPreferences.Editor editorPrefMesa = preferenciasMesa.edit();
                    editorPrefMesa.putInt("num_personas", numPersonas);
                    editorPrefMesa.commit();
                    finish();
                }else{//No se genero la orden
                    msjRespuestaApiREST = getResources().getString(R.string.msj_numOrdenNoAsignado);
                }
            } catch (IOException e) {
                msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
                e.printStackTrace();
            }

            return msjRespuestaApiREST;
        }
    }
}