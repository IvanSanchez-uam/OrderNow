package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.Intent;

import com.losjuanes.ordernowapp.api_rest.pojo.Producto;
import com.losjuanes.ordernowapp.negocio.ImplementacionServicioProducto;
import com.losjuanes.ordernowapp.negocio.ServicioProducto;
import com.losjuanes.ordernowapp.presentacion.vistas.PrincipalActivity;

import java.io.IOException;
import java.util.ArrayList;

public class ImplementacionControladorMuestraMenu implements ControladorMuestraMenu{

    private ServicioProducto servicioProducto = new ImplementacionServicioProducto();

    @Override
    public void iniciaActivity(Activity activityInicio, Class activityDestino) {
        activityInicio.startActivity(new Intent(activityInicio,activityDestino));
    }

    /**
     * Obtiene el menu dependiendo de la hora del día.
     * @return ArrayList de productos, el menu.
     */
    @Override
    public ArrayList<Producto> obtenerMenu() throws IOException {
        return servicioProducto.obtenerMenu();
    }
}
