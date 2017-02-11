package com.losjuanes.ordernowapp.api_rest;

import com.losjuanes.ordernowapp.api_rest.pojo.Mesa;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaNumMesa;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EndPointApi {

    /**
     * Usado para asignar el numero de mesa a una tablet.
     */
    @POST(ConstantesRestApi.AGREGAR_MESA)
    Call<ResponseBody> asignarNumMesa(@Body Mesa mesa);
}
