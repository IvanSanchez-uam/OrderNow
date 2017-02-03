package com.losjuanes.ordernowapp.negocio;

import android.app.Activity;
import android.widget.Toast;

import com.losjuanes.ordernowapp.R;
import com.losjuanes.ordernowapp.api_rest.ConexionApiRest;
import com.losjuanes.ordernowapp.api_rest.EndPointApi;
import com.losjuanes.ordernowapp.api_rest.pojo.Mesa;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaNumMesa;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImplementacionServicio implements Servicio {

    /**
     * Se conectara con el Api REST para asignar el numero de mesa.
     *
     * @param numMesa, numero de mesa ingresado por el administrador de la aplicacion
     *                 para ser asignado a una tablet.
     * @return true, si el numero de mesa esta disponible, false en caso contrario.
     * @throws IOException, mandara una excepcion si falla la conexion con el Api REST.
     */
    @Override
    public boolean asigna_numeroMesa(int numMesa) throws IOException {
        boolean mesaAsignada = false;

        Mesa mesa = new Mesa(numMesa);
        ConexionApiRest conexionApiRest = new ConexionApiRest();
        EndPointApi endPointApi = conexionApiRest.establecerConexionPOST();
        Call<RespuestaNumMesa> call = endPointApi.asignarNumMesa(mesa);

        /**
         * Obtenemos el JSON que envia el Api REST.
         *
         * Si la mesa fue asignada el JSON seria {"mesaAsignada":true},
         * en caso contrario {"mesaAsignada":false}.
         */
         RespuestaNumMesa respuestaNumMesa = call.execute().body();
         mesaAsignada = respuestaNumMesa.getMesaAsignada();

        /*call.enqueue(new Callback<RespuestaNumMesa>() {
            @Override
            public void onResponse(Call<RespuestaNumMesa> call, Response<RespuestaNumMesa> response) {
                mesaAsignada = response.body().getMesaAsignada();
            }

            @Override
            public void onFailure(Call<RespuestaNumMesa> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.msj_falloConexion_asignarMesa), Toast.LENGTH_SHORT).show();
            }
        });*/

        return mesaAsignada;
    }
}
