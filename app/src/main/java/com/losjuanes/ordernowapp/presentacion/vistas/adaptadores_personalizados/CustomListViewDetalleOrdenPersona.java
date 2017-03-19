package com.losjuanes.ordernowapp.presentacion.vistas.adaptadores_personalizados;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.pojo.Producto;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenarDetalleOrden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para mostrar un texto y un botón en la lv_productosOrden de la Activity MenuActivity.
 */
public class CustomListViewDetalleOrdenPersona extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<String> datos;
    private HashMap<String,ArrayList<Integer>> detalleOrdenPersona;
    private String persona;
    private ArrayList<Producto> menu;
    private Button btnOrdenar;

    public CustomListViewDetalleOrdenPersona(Context context, int resource, ArrayList<String> datos, String persona, ArrayList<Producto> menu, HashMap<String,ArrayList<Integer>> detalleOrdenPersona, Button btnOrdenar) {
        this.context = context;
        this.resource = resource;
        this.datos = datos;
        this.persona = persona;
        this.menu = menu;
        this.detalleOrdenPersona = detalleOrdenPersona;
        this.btnOrdenar = btnOrdenar;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("Debug", "Custom getView: inicio");
        LayoutInflater inflater;
        View view = convertView;
        Log.d("Debug", "Custom getView: durante1");

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_lv_detalle_orden_persona,parent , false);//Se infla con un layout que contenga un texto y un FloatingActionButton
        }
        Log.d("Debug", "Custom getView: durante2");
        TextView tv_detalleOrden = (TextView) view.findViewById(R.id.tv_detalleOrden);
        tv_detalleOrden.setText(datos.get(position));

        FloatingActionButton fab_eliminarProducto = (FloatingActionButton) view.findViewById(R.id.fab_eliminarProducto);

        //cada que el usuario presione el boton "quitar" de cada elemento de la listview este debera desaparecer de la misma y del detalleOrdenPersona.
        fab_eliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "Custom onClick: inicio");
                ArrayList<Integer> productosPersona = detalleOrdenPersona.get(persona);
                boolean ordenVacia = true;

                for (Producto producto : menu){
                    if (producto.getNombre().equals(datos.get(position))){
                        for (int i = 0; i < productosPersona.size(); i++){
                            if (productosPersona.get(i) == producto.getIdProducto()){
                                productosPersona.remove(i);
                                break;
                            }
                        }
                        break;
                    }
                }

                detalleOrdenPersona.put(persona, productosPersona);
                datos.remove(position);

                for (Map.Entry<String,ArrayList<Integer>> productoPersona : detalleOrdenPersona.entrySet()){
                    if (productoPersona.getValue().size() != 0){
                        ordenVacia = false;
                        break;
                    }
                }

                if (ordenVacia)
                    btnOrdenar.setEnabled(false);

                notifyDataSetChanged();
                Log.d("Debug", "Custom onClick: fin");
            }
        });
        Log.d("Debug", "Custom getView: inicio");
        return view;
    }
}
