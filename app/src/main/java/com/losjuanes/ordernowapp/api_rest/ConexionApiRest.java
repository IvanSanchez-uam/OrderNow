package com.losjuanes.ordernowapp.api_rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionApiRest {

    /**
     * Se conectara con el Api REST enviandole un JSON
     */
    public EndPointApi establecerConexionPOST(){

        /** Sirve para debuggear la comunicacion de la tablet con el servidor*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        /** Se hace la conexion con el servidor*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EndPointApi.class);
    }
}
