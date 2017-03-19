package com.losjuanes.ordernowapp.presentacion.vistas;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.pojo.Producto;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenarDetalleOrden;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorMuestraMenu;
import com.losjuanes.ordernowapp.presentacion.controladores.ControladorOrdenar;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorMuestraMenu;
import com.losjuanes.ordernowapp.presentacion.controladores.ImplementacionControladorOrdenar;
import com.losjuanes.ordernowapp.presentacion.vistas.adaptadores_personalizados.CustomListViewDetalleOrdenPersona;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private ControladorMuestraMenu controladorMuestraMenu;
    private ControladorOrdenar controladorOrdenar;
    private ObtenerMenu obtenerMenu;

    private Activity menuActivity = this;
    private Context context = this;
    private int numOrden = 0;
    private int numPersonas = 0;

    private ListView lvPlatillos, lvBebidas;//Listas que muestran el menú
    private TextView tvNomPlatilloBebida,tvDescripcionPB,tvMontoPB; //elementos para la descripcion del producto
    private ImageView ivPlatilloBebida; //Imagn del producto
    private FloatingActionButton fab_agregarProducto;
    private ArrayList<Producto> menu; //Contiene el menú completo(platillos/bebidas) de los productos
    private ArrayList<String> platillos; //lista de todos los nombres de los platillos
    private ArrayList<String> bebidas; //lista de todos los nombres de las bebidas

    private ListView lv_clientes, lv_productosOrden; //Listas para mostrarle al usuario el detalle de la orden de cada persona
    private TextView tv_titulo_descripcionOrdenCliente; //Indica de que persona se esta mostrando el detalle de la orden.
    private Button btn_ordenar; //Boton para enviar la orden al Api REST
    private ArrayList<RespuestaOrdenarDetalleOrden> detalleOrden; //Arreglo para almacenar los platillos con la persona que lo ordenó
    private List<String> personas; //lista para pasarlo en el adapter de lv_clientes
    private HashMap<String,ArrayList<Integer>>  detalleOrdenPersona; //mapa para guardar la relacion de Persona-Los produtos que eligió

    private Producto productoActual; //guarda el producto acutalmente seleccionado
    private String personaSeleccionada = ""; //guarda el nombre de la persona actualmente seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Navegacion hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controladorMuestraMenu = new ImplementacionControladorMuestraMenu();
        controladorOrdenar = new ImplementacionControladorOrdenar();
        /****************** PRUEBAS ******************/
        /*SharedPreferences preferenciasMesa = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        SharedPreferences.Editor editorPrefMesa = preferenciasMesa.edit();
        editorPrefMesa.putInt("num_mesa", 5 );
        editorPrefMesa.putInt("num_personas", 3 );
        editorPrefMesa.commit();*/

        /****************** PRUEBAS ******************/

        /** Obtenemos el numero de orden y numero de personas desde las preferencias. **/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        numOrden = pref.getInt("num_orden", 0);
        Log.d("prefOrdenPersonas", "numOrden = " + numOrden);
        numPersonas = pref.getInt("num_personas", 0);
        Log.d("prefOrdenPersonas", "numPersonas = " + numPersonas);

        /****** funcionamiento del boton Ordenar ******/
        btn_ordenar = (Button) findViewById(R.id.btn_ordenar);
        btn_ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmacionOrdenarBuilder = new AlertDialog.Builder(menuActivity);
                confirmacionOrdenarBuilder.setTitle(R.string.alrt_tituloOrdenar)
                        .setMessage(R.string.alrt_descripcionOrdenar)
                        .setPositiveButton(R.string.alrt_si, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Ordenar ordenar = new Ordenar();
                               ordenar.execute();
                           }
                        })
                        .setNegativeButton(R.string.alrt_no, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                });

                AlertDialog confirmacionOrdenar = confirmacionOrdenarBuilder.create();
                confirmacionOrdenar.show();
            }
        });

        /****** Listas para mostrarle el menú al usuario ******/
        lvPlatillos = (ListView) findViewById(R.id.lv_platillos);
        lvBebidas = (ListView) findViewById(R.id.lv_bebidas);

        ManejadorListasMenu manejadorListasMenu = new ManejadorListasMenu();
        lvPlatillos.setOnItemClickListener(manejadorListasMenu);
        lvBebidas.setOnItemClickListener(manejadorListasMenu);

        /****** Listas para mostrarle el detalle de la orden de cada persona ******/
        lv_clientes = (ListView) findViewById(R.id.lv_clientes);
        lv_productosOrden = (ListView) findViewById(R.id.lv_productosOrden);
        tv_titulo_descripcionOrdenCliente = (TextView) findViewById(R.id.titulo_descripcionOrdenCliente);
        fab_agregarProducto = (FloatingActionButton) findViewById(R.id.fab_agregarProducto);

        detalleOrdenPersona = new HashMap<String,ArrayList<Integer>>();
        personas = new ArrayList<String>();
        for(int numPersona = 1; numPersona <= numPersonas; numPersona++){
            detalleOrdenPersona.put("Persona " + numPersona, new ArrayList<Integer>());
            personas.add("Persona " + numPersona);
        }

        lv_productosOrden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Debug", "MenuActivity onItemClick lv_productosOrden: Inicio");
                DescargarImagen descargarImagen = new DescargarImagen();
                Producto productoSeleccionado = new Producto();
                int idProducto = detalleOrdenPersona.get(personaSeleccionada).get(position);// POSIBLE ERROR XXXXXXXXXXXXXXXXXXXXXXXXX

                for (Producto producto : menu){
                    if(producto.getIdProducto() == idProducto){
                        productoSeleccionado = producto;
                    }
                }

                if (productoActual.getIdProducto() != productoSeleccionado.getIdProducto()) {
                    descargarImagen.execute(new String[]{productoSeleccionado.getImagen()});
                    tvNomPlatilloBebida.setText(productoSeleccionado.getNombre());
                    tvDescripcionPB.setText(productoSeleccionado.getDescripcion());
                    tvMontoPB.setText(productoSeleccionado.getPrecio() + "$");
                }
                Log.d("Debug", "MenuActivity onItemClick lv_productosOrden: Fin");
            }
        });

        /****** Lista de personas *****/
        lv_clientes.setAdapter(new ArrayAdapter<String>(context,R.layout.custom_lv_platillos_bebidas,personas));
        lv_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSeleccionada = personas.get(position);
                tv_titulo_descripcionOrdenCliente.setText("Orden " + personaSeleccionada);
                Log.d("Debug", "MenuActivity setAdapter Custom1: Inicio");
                lv_productosOrden.setAdapter(new CustomListViewDetalleOrdenPersona(context, R.layout.custom_lv_detalle_orden_persona, getNomProductos(detalleOrdenPersona.get(personas.get(position))), personaSeleccionada, menu, detalleOrdenPersona, btn_ordenar));
                Log.d("Debug", "MenuActivity setAdapter Custom1: Fin");
            }
        });

        /***** Funcionamiento del boton agregar producto *****/
        //En esta parte se muestra una ventana para poder seleccionar a que persona se le asociará el platillo/bebida seleccionado.
        fab_agregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] personasLV = new String[personas.size()];
                personasLV = personas.toArray(personasLV);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(menuActivity.getResources().getString(R.string.titulo_productoDe));

                builder.setItems(personasLV, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Integer> aux  = detalleOrdenPersona.get(personas.get(which));
                        aux.add(productoActual.getIdProducto());
                        detalleOrdenPersona.put(personas.get(which), aux);

                        //si actualmente esta seleccionada la persona a la que se le asocio un producto entonces se actualiza la lisa.
                        if (personaSeleccionada.equals(personas.get(which))) {
                            Log.d("Debug", "MenuActivity setAdapter Custom2: Inicio");
                            lv_productosOrden.setAdapter(new CustomListViewDetalleOrdenPersona(context, R.layout.custom_lv_detalle_orden_persona, getNomProductos(detalleOrdenPersona.get(personas.get(which))), personaSeleccionada, menu, detalleOrdenPersona, btn_ordenar));
                            Log.d("Debug", "MenuActivity setAdapter Custom2: Fin");
                        }
                        btn_ordenar.setEnabled(true);
                    }
                }).setCancelable(true).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        /****** Imagen y texto que describen el platillo o la bebida ******/
        ivPlatilloBebida = (ImageView) findViewById(R.id.iv_platillo_bebida);
        tvNomPlatilloBebida = (TextView) findViewById(R.id.tv_nomPlatilloBebida);
        tvDescripcionPB = (TextView) findViewById(R.id.tv_descripcionPB);
        tvMontoPB = (TextView) findViewById(R.id.tv_montoPB);

        ivPlatilloBebida.setScaleType(ImageView.ScaleType.CENTER_CROP);

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

                ArrayAdapter<String> adapterPlatillos = new ArrayAdapter<String>(menuActivity.getApplicationContext(), R.layout.custom_lv_platillos_bebidas, platillos);
                ArrayAdapter<String> adapterBebidas = new ArrayAdapter<String>(menuActivity.getApplicationContext(), R.layout.custom_lv_platillos_bebidas, bebidas);

                lvPlatillos.setAdapter(adapterPlatillos);
                lvBebidas.setAdapter(adapterBebidas);

                productoActual = menu.get(0);
                descargarImagen.execute(new String[]{productoActual.getImagen()});
                tvNomPlatilloBebida.setText(productoActual.getNombre());
                tvDescripcionPB.setText(productoActual.getDescripcion());
                tvMontoPB.setText(productoActual.getPrecio() + "$");
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
                    productoActual = producto;
                    break;
                }
            }

            if (productoActual != null){
                descargarImagen.execute(new String[]{productoActual.getImagen()});
                tvNomPlatilloBebida.setText(productoActual.getNombre());
                tvDescripcionPB.setText(productoActual.getDescripcion());
                tvMontoPB.setText(productoActual.getPrecio() + "$");
            }
        }
    }

    /**
     * Esta clase permitira que la parte de la conexion con la Api REST se ejecute en un hilo diferente
     */
    private class Ordenar extends AsyncTask<Void,Void,Boolean>{

        /**
         * Este metodo se ejecuta inmediatamente despues de que el hilo finaliza su ejecucion.
         *
         * @param ordenAtendida, true si la orden fue enviada al Api REST exitosamente, false en otro caso.
         */
        @Override
        protected void onPostExecute(Boolean ordenAtendida) {
            if (ordenAtendida){
                Toast.makeText(menuActivity, getResources().getString(R.string.msj_ordenEnviada), Toast.LENGTH_SHORT).show();
                Log.d("Debug", "MenuActivity setAdapter Custom3: Inicio");
                CustomListViewDetalleOrdenPersona customAdapter = new CustomListViewDetalleOrdenPersona(context, R.layout.custom_lv_detalle_orden_persona, new ArrayList<String>(), personaSeleccionada, menu, detalleOrdenPersona, btn_ordenar);
                lv_productosOrden.setAdapter(customAdapter);
                Log.d("Debug", "MenuActivity setAdapter Custom3: Fin");
                menuActivity.finish();
            }else
                Toast.makeText(menuActivity, getResources().getString(R.string.msj_ordenEnviada_ERROR), Toast.LENGTH_SHORT).show();
        }

        /**
         * El hilo ejecuta esta parte del código.
         * @return El mensaje a mostrar al usuario.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ordenAtendida = false;
            int numPersonaAux = 0;
            detalleOrden = new ArrayList<>();
            try {
                for (Map.Entry<String,ArrayList<Integer>> productoPersona : detalleOrdenPersona.entrySet()){
                    numPersonaAux = Integer.parseInt(productoPersona.getKey().substring(8));
                    Log.d("Debug", "doInBackground: " + productoPersona.getValue());
                    for (Integer idProducto : productoPersona.getValue()){
                        detalleOrden.add(new RespuestaOrdenarDetalleOrden(numPersonaAux,idProducto));
                    }
                }
                ordenAtendida = controladorOrdenar.ordenar(menuActivity, detalleOrden);
                if(ordenAtendida) {
                    detalleOrden.clear();
                    detalleOrdenPersona.clear();
                    for(numPersonaAux = 1; numPersonaAux <= numPersonas; numPersonaAux++){
                        detalleOrdenPersona.put("Persona " + numPersonaAux, new ArrayList<Integer>());
                    }
                }
            }catch (IOException e){
                Log.d("ordenar", "doInBackground: orden no enviada... error de conexión");
            }
            return ordenAtendida;
        }
    }

    /**
     * Dado un arreglo de ID´s de productos este devuelve un arreglo con los nombres de los productos con dichos ID´s,
     * @param productos ID´s de los productos
     * @return arreglo con los nombres de los productos con los ID´s recibidos.
     */
    private ArrayList<String> getNomProductos(ArrayList<Integer> productos){
        ArrayList<String> nomProductos = new ArrayList<String>();
        for (Producto producto : menu) {
            for (Integer idProducto : productos) {
                if (producto.getIdProducto() == idProducto) {
                    nomProductos.add(producto.getNombre());
                }
            }
        }
        return nomProductos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (obtenerMenu != null) {
            obtenerMenu.cancel(true);
        }
    }
}