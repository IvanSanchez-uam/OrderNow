package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.Intent;

public class ImplementacionControlador implements Controlador {

    /**
     * Inicia la transicion de activityInicio a activityDestino.
     *
     * @param activityInicio, activity que inicia la transicion.
     * @param activityDestino, activity destino de la transicion.
     */
    @Override
    public void iniciaActivity(Activity activityInicio, Class activityDestino) {
        activityInicio.startActivity(new Intent(activityInicio,activityDestino));
    }





}
