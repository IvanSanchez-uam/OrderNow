package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;

import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenarDetalleOrden;

import java.io.IOException;
import java.util.ArrayList;

public interface ControladorOrdenar {
    boolean ordenar(Activity activity, ArrayList<RespuestaOrdenarDetalleOrden> productos) throws IOException;
}
