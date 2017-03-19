package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.SharedPreferences;

import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenar;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenarDetalleOrden;
import com.losjuanes.ordernowapp.negocio.ImplementacionServicioOrden;
import com.losjuanes.ordernowapp.negocio.ServicioOrden;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ImplementacionControladorOrdenar implements ControladorOrdenar {

    private ServicioOrden servicioOrden = new ImplementacionServicioOrden();

    /**
     * Se conectara con el Api REST para enviarle la orden de los clientes.
     * @param productos contiene los productos y las personas que pidieron el producto.
     * @return true si el Api REST recibio la orden false en caso contrario.
     */
    @Override
    public boolean ordenar(Activity activity, ArrayList<RespuestaOrdenarDetalleOrden> productos) throws IOException {
        RespuestaOrdenar orden;
        int numOrden;
        boolean ordenAtendida;

        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        numOrden = pref.getInt("num_orden", 0);

        orden = new RespuestaOrdenar(numOrden, productos);
        ordenAtendida = servicioOrden.ordenar(orden);

        return ordenAtendida;
    }
}
