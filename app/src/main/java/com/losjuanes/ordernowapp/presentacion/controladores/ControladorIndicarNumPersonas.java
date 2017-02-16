package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;

import java.io.IOException;

public interface ControladorIndicarNumPersonas {

    int obtener_numOrden(int numPersonas, Activity activity) throws IOException;
}
