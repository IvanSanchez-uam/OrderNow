package com.losjuanes.ordernowapp.negocio;

import android.util.Log;

import com.losjuanes.ordernowapp.api_rest.ConexionApiRest;
import com.losjuanes.ordernowapp.api_rest.EndPointApi;
import com.losjuanes.ordernowapp.api_rest.pojo.Mesa;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ImplementacionServicioMesa implements ServicioMesa {

    /**
     * Se conectara con el Api REST para asignar el numero de mesa.
     *
     * @param numMesa, numero de mesa ingresado por el administrador de la aplicacion
     *                 para ser asignado a una tablet.
     * @return true, si el numero de mesa esta disponible, false en caso contrario.
     * @throws IOException, mandara una excepcion si falla la conexion con el Api REST.
     */
    @Override
    public int asignar_numeroMesa(int numMesa) throws IOException {
        int mesaAsignada;
        Response<ResponseBody> respuestaNumMesa;

        Mesa mesa = new Mesa(numMesa);
        ConexionApiRest conexionApiRest = new ConexionApiRest();
        EndPointApi endPointApi = conexionApiRest.establecerConexionPOST();
        Call<ResponseBody> call = endPointApi.asignarNumMesa(mesa);

        /**
         * El Api REST regresara los codigos 200 o 404.
         *
         * 200 = se asigno la mesa.
         * 404 = el numero de mesa ya existe.
         */

        respuestaNumMesa = call.execute();
        mesaAsignada = respuestaNumMesa.code();

        Log.d("MesaAsignada",mesaAsignada + " " + "***************");

        return mesaAsignada;
    }
}
