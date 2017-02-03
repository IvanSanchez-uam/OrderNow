package com.losjuanes.ordernowapp.api_rest;

import com.losjuanes.ordernowapp.api_rest.pojo.Mesa;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaNumMesa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EndPointApi {

    /**
     * Usado para asignar el numero de mesa a una tablet.
     */
    @POST(ConstantesRestApi.NUMERO_MESA)
    Call<RespuestaNumMesa> asignarNumMesa(@Body Mesa mesa);
}
