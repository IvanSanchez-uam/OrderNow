package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;

import java.io.IOException;

public interface Controlador{

    void iniciaActivity(Activity activityInicio, Class activityDestino);

    boolean asigna_numeroMesa(int numMesa,Activity activity) throws IOException;
}
