package com.losjuanes.ordernowapp.negocio;

import com.losjuanes.ordernowapp.api_rest.ConexionApiRest;
import com.losjuanes.ordernowapp.api_rest.EndPointApi;
import com.losjuanes.ordernowapp.api_rest.pojo.Producto;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Response;

public class ImplementacionServicioProducto implements ServicioProducto {

    /**
     * Se conectará con la Api REST para obtener los productos del menú.
     * @return ArrayList de productos, el menú.
     */
    @Override
    public ArrayList<Producto> obtenerMenu() throws IOException {
        Response<ArrayList<Producto>> respuestaObtenerMenu;

        ConexionApiRest conexionApiRest =  new ConexionApiRest();
        EndPointApi endPointApi = conexionApiRest.establecerConexionPOST();
        Call<ArrayList<Producto>> call = endPointApi.obtenerMenu();

        respuestaObtenerMenu = call.execute();

        return respuestaObtenerMenu.body();
    }
}
