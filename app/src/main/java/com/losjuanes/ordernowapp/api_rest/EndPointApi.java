package com.losjuanes.ordernowapp.api_rest;

import com.losjuanes.ordernowapp.api_rest.pojo.Mesa;
import com.losjuanes.ordernowapp.api_rest.pojo.Orden;
import com.losjuanes.ordernowapp.api_rest.pojo.Producto;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenar;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EndPointApi {

    /**
     * Usado para asignar el numero de mesa a una tablet.
     */
    @POST(ConstantesRestApi.AGREGAR_MESA)
    Call<ResponseBody> asignarNumMesa(@Body Mesa mesa);

    /**
     * Usado para generar el numero de orden.
     */
    @POST(ConstantesRestApi.GENERAR_NUM_ORDEN)
    Call<Integer> generarNumOrden(@Body Orden orden);

    /**
     * Usado para enviar la orden del cliente.
     */
    @POST(ConstantesRestApi.ORDENAR)
    Call<ResponseBody> ordenar(@Body RespuestaOrdenar respuestaOrdenar);

    /**
     * Usado para obtener el menu del restaurante.
     */
    @GET(ConstantesRestApi.OBTENER_MENU)
    Call<ArrayList<Producto>> obtenerMenu();
}
