package com.losjuanes.ordernowapp.presentacion.vistas.adaptadores_personalizados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaDetalleCuenta;

import java.util.ArrayList;

public class CustomListViewCuenta extends BaseAdapter{

    private Context context;
    private ArrayList<RespuestaDetalleCuenta> productos;
    private int numPersonas;
    private int persona;

    public CustomListViewCuenta(Context context, int numPersonas, ArrayList<RespuestaDetalleCuenta> productos) {
        this.context = context;
        this.numPersonas = numPersonas;
        this.productos = productos;
        persona = productos.get(0).getPersona();
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_lv_cuenta, parent, false);

        TextView tvPersona = (TextView) view.findViewById(R.id.tvPersona);
        TextView tvProducto = (TextView) view.findViewById(R.id.tvProducto);
        TextView tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);

        String numPersona;
        String producto = productos.get(position).getNomProducto();
        String precio = "$" + productos.get(position).getPrecio();

        if(persona == productos.get(position).getPersona()) {
            numPersona = "Persona " + productos.get(position).getPersona();
            persona++;
        }else
            numPersona = "";


        tvPersona.setText(numPersona);
        tvProducto.setText(producto);
        tvPrecio.setText(precio);

        return view;
    }
}
