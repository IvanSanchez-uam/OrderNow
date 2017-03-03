package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.pojo.Producto;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorMuestraMenu;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorMuestraMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ControladorMuestraMenu controladorMuestraMenu;
    private ObtenerMenu obtenerMenu;

    private Activity Menu = this;
    private int numOrden = 0;

    private ListView lvPlatillos, lvBebidas;
    private TextView tvNomPltilloBebida,tvDescripcionPB,tvMontoPB;
    private ImageView ivPlatilloBebida;

    private ListView lv_clientes, lv_productosOrden;

    private ArrayList<Producto> menu;
    private ArrayList<String> platillos;
    private ArrayList<String> bebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Navegacion hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controladorMuestraMenu = new ImplementacionControladorMuestraMenu();

        /** Obtenemos el numero de orden desde las preferencias. **/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        numOrden = pref.getInt("num_orden", 0);
        Log.d("numOrden", "numOrden = " + numOrden);

        /****** Listas para mostrarle el menú al usuario ******/
        lvPlatillos = (ListView) findViewById(R.id.lv_platillos);
        lvBebidas = (ListView) findViewById(R.id.lv_bebidas);

        ManejadorListasMenu manejadorListasMenu = new ManejadorListasMenu();
        lvPlatillos.setOnItemClickListener(manejadorListasMenu);
        lvBebidas.setOnItemClickListener(manejadorListasMenu);

        /****** Imagen y texto que describen el platillo o la bebida ******/
        ivPlatilloBebida = (ImageView) findViewById(R.id.iv_platillo_bebida);
        tvNomPltilloBebida = (TextView) findViewById(R.id.tv_nomPlatilloBebida);
        tvDescripcionPB = (TextView) findViewById(R.id.tv_descripcionPB);
        tvMontoPB = (TextView) findViewById(R.id.tv_montoPB);

        ivPlatilloBebida.setScaleType(ImageView.ScaleType.FIT_XY);

        obtenerMenu = new ObtenerMenu();
        obtenerMenu.execute();
    }

    /**
     * Esta clase permitira que la parte de la conexion con la Api REST se ejecute en un hilo diferente
     */
    private class ObtenerMenu extends AsyncTask<Void,Void,String>{

        /**
         * Este metodo se ejecuta inmediatamente despues de que el hilo finaliza su ejecucion.
         *
         * @param respuestaAPiRest, mensaje a mostrar al usuario.
         */
        @Override
        protected void onPostExecute(String respuestaAPiRest) {
            if(respuestaAPiRest.compareTo("") != 0 || menu.size() == 0 || platillos.size() == 0){
                Toast.makeText(MenuActivity.this, respuestaAPiRest, Toast.LENGTH_SHORT).show();
                ObtenerMenu reintentar = new ObtenerMenu();reintentar.execute();
            }else{
                DescargarImagen descargarImagen = new DescargarImagen();

                ArrayAdapter<String> adapterPlatillos = new ArrayAdapter<String>(Menu.getApplicationContext(), R.layout.custom_lv_platillos_bebidas, platillos);
                ArrayAdapter<String> adapterBebidas = new ArrayAdapter<String>(Menu.getApplicationContext(), R.layout.custom_lv_platillos_bebidas, bebidas);

                lvPlatillos.setAdapter(adapterPlatillos);
                lvBebidas.setAdapter(adapterBebidas);

                for (Producto producto: menu){
                    if (producto.getNombre().equals(platillos.get(0))){
                        descargarImagen.execute(new String[]{producto.getImagen()});
                        tvNomPltilloBebida.setText(producto.getNombre());
                        tvDescripcionPB.setText(producto.getDescripcion());
                        tvMontoPB.setText(producto.getPrecio() + "$");
                    }
                }
            }
        }

        /**
         * El hilo ejecuta esta parte del código.
         * @param params
         * @return El mensaje a mostrar al usuario.
         */
        @Override
        protected String doInBackground(Void... params) {
            String msjRespuestaApiREST = "";
            try{
                menu = controladorMuestraMenu.obtenerMenu();
                inicializaPlatillosBebidas();
            }catch (IOException e){
                msjRespuestaApiREST = getResources().getString(R.string.msj_falloConexion);
            }

            return msjRespuestaApiREST;
        }
    }

    /**
     * Inicializa los arreglos "platillos" y "bebidas" para posteriormente mostrarlos al usuario.
     */
    public void inicializaPlatillosBebidas(){
        platillos = new ArrayList<>();
        bebidas = new ArrayList<>();

        for (Producto producto : menu){
            if(producto.getCategoria().compareTo("Bebida") == 0){
                bebidas.add(producto.getNombre());
            }else{
                platillos.add(producto.getNombre());
            }
        }
    }

    /**
     * Esta clase permitira que la descarga de la imagen del platillo/bebida se ejecute en un hilo diferente
     */
    private class DescargarImagen extends AsyncTask<String,Void,Bitmap>{

        /**** Despues de haber descargado la imagen, esta se carga en la image view ivPlatilloBebida ****/
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null)
                ivPlatilloBebida.setImageBitmap(bitmap);//Se inserta la imagen en la image view
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap imgPlatilloBebida = null;
            String urlImagen;

            if(url.length > 0) {
                urlImagen = url[0];
                try {
                    InputStream in = new java.net.URL(urlImagen).openStream();//Se establece la conexión
                    imgPlatilloBebida = BitmapFactory.decodeStream(in);//Se descarga la imagen
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return imgPlatilloBebida;

        }
    }

    /**
     * Manejador para cuando el usuario selecciona un platiilo o bebida.
     */
    private class ManejadorListasMenu implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DescargarImagen descargarImagen = new DescargarImagen();
            String nomProductoSeleccionado = "";
            Producto productoSeleccionado = null;

            switch (parent.getId()){
                case R.id.lv_platillos:
                    nomProductoSeleccionado = (String) lvPlatillos.getItemAtPosition(position);
                    break;

                case R.id.lv_bebidas:
                    nomProductoSeleccionado = (String) lvBebidas.getItemAtPosition(position);
                    break;
            }

            for (Producto producto: menu){
                if (producto.getNombre().equals(nomProductoSeleccionado)){
                    productoSeleccionado = producto;
                    break;
                }
            }

            if (productoSeleccionado != null){
                descargarImagen.execute(new String[]{productoSeleccionado.getImagen()});
                tvNomPltilloBebida.setText(productoSeleccionado.getNombre());
                tvDescripcionPB.setText(productoSeleccionado.getDescripcion());
                tvMontoPB.setText(productoSeleccionado.getPrecio() + "$");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (obtenerMenu != null) {
            obtenerMenu.cancel(true);
        }
    }
}
