package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;

import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaCuenta;

import java.io.IOException;

public interface ControladorCuenta {
    void iniciaActivity(Activity activityInicio, Class activityDestino);

    RespuestaCuenta obtenerCuenta(int numOrden) throws IOException;

    int pagar(int numOrden) throws IOException;
}
