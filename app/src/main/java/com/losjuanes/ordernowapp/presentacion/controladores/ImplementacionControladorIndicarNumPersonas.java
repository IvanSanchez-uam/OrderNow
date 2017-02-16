package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.losjuanes.ordernowapp.negocio.ImplementacionServicioOrden;
import com.losjuanes.ordernowapp.negocio.ServicioOrden;
import com.losjuanes.ordernowapp.presentacion.vistas.PrincipalActivity;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class ImplementacionControladorIndicarNumPersonas implements ControladorIndicarNumPersonas {
    private ServicioOrden servicio = new ImplementacionServicioOrden();

    /**
     * Invoca al metodo obtener_numOrden() de implementacionServicioOrden.
     * Cuando el metodo obtener_numOrden() regresa el numOrden se iniciara la actividad PrincipalActivity.
     *
     * @param numPersonas, personas en la mesa.
     * @param activity, actividad NumPersonasActivity.
     * return : numOrden si esta fue generada, -1 en caso contrario.
     */
    @Override
    public int obtener_numOrden(int numPersonas, Activity activity) throws IOException {
        int numOrden;

        //Se obtiene el numero de mesa
        SharedPreferences pref = activity.getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
        int numMesa = pref.getInt("num_mesa", 0);

        numOrden = servicio.obtener_numOrden(numMesa,numPersonas);
        Log.d("Numero_de_orden", String.valueOf(numOrden));//Debbuging de numOrden

        //Si numOrden es -1 la orden no fue generada en la Api REST
        if (numOrden != -1) {
            //Pasamos el numOrden a la actividad PrincipalActivity
            Intent intent = new Intent(activity,PrincipalActivity.class);
            intent.putExtra("numOrden",numOrden);

            //Se inicia la actividad PrincipalActivity
            activity.startActivity(intent);
        }

        return numOrden;
    }
}
