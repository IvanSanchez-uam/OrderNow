package com.losjuanes.ordernowapp.negocio;

import android.util.Log;

import com.losjuanes.ordernowapp.api_rest.ConexionApiRest;
import com.losjuanes.ordernowapp.api_rest.EndPointApi;
import com.losjuanes.ordernowapp.api_rest.pojo.Orden;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenar;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ImplementacionServicioOrden implements ServicioOrden {

    /**
     * Se conectara con la Api REST para generar un numero de orden.
     *
     * @param numMesa, mesa que solicita la orden.
     * @param numPersonas, numero de personas en la mesa
     */
    @Override
    public int obtener_numOrden(int numMesa, int numPersonas) throws IOException {
        int numOrden;
        Response<Integer> respuestaNumOrden;//Usada para guardar la respuesta de la Api REST.

        //Se crea la conexion en el Api REST
        Orden orden = new Orden(numMesa, numPersonas);//Se generara un JSON de tipo Orden.
        ConexionApiRest conexionApiRest = new ConexionApiRest();
        EndPointApi endPointApi = conexionApiRest.establecerConexionPOST();
        Call<Integer> call = endPointApi.generarNumOrden(orden);

        /**
         * La Api REST regresara el numero de la orden si esta fue generada,
         * -1 en caso contrario.
         */
        respuestaNumOrden = call.execute();
        numOrden = respuestaNumOrden.body();
        Log.d("Numero_de_orden", String.valueOf(numOrden));//Debbuging de numOrden

        return numOrden;
    }

    /**
     * Se conectara con el Api REST para enviarle la orden de los clientes.
     * @param orden contiene el numero de orden, los productos y las personas que pidieron el producto.
     * @return true si el Api REST recibio la orden false en caso contrario.
     */
    @Override
    public boolean ordenar(RespuestaOrdenar orden) throws IOException {
        Response<ResponseBody> response;
        int codigoHttp;
        ConexionApiRest conexionApiRest = new ConexionApiRest();
        EndPointApi endPointApi = conexionApiRest.establecerConexionPOST();
        Call<ResponseBody> call = endPointApi.ordenar(orden);

        response = call.execute();
        codigoHttp = response.code();

        if (codigoHttp == 200){
            return true;
        }else//El código recibido fue 500
            return false;
    }
}
