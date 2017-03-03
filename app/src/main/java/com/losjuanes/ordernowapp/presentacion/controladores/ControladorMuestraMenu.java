package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;

import com.losjuanes.ordernowapp.api_rest.pojo.Producto;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by josec on 26/02/2017.
 */

public interface ControladorMuestraMenu {

    void iniciaActivity(Activity activityInicio, Class activityDestino);

    ArrayList<Producto> obtenerMenu() throws IOException;
}
