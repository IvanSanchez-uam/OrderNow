package com.losjuanes.ordernowapp.negocio;

import com.losjuanes.ordernowapp.api_rest.pojo.Producto;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by josec on 01/03/2017.
 */

public interface ServicioProducto {

    ArrayList<Producto> obtenerMenu() throws IOException;

}
