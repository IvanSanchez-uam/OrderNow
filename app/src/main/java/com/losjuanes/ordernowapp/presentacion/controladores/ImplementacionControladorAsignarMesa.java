package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.losjuanes.ordernowapp.negocio.ImplementacionServicioMesa;
import com.losjuanes.ordernowapp.negocio.ServicioMesa;
import com.losjuanes.ordernowapp.presentacion.vistas.BienvenidaActivity;
import com.losjuanes.ordernowapp.presentacion.vistas.NumPersonasActivity;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class ImplementacionControladorAsignarMesa implements ControladorAsignarMesa {

    private ServicioMesa servicio = new ImplementacionServicioMesa();

    /**
     * Inicia la transicion de activityInicio a activityDestino.
     *
     * @param activityInicio, activity que inicia la transicion.
     * @param activityDestino, activity destino de la transicion.
     */
    @Override
    public void iniciaActivity(Activity activityInicio, Class activityDestino) {
        activityInicio.startActivity(new Intent(activityInicio,activityDestino));
    }

    /****************************** HU-1 ******************************/

    /**
     * Invoca al método asigna_numeroMesa() de implementacionServicioMesa.
     * Cuando el método asigna_numeroMesa() regresa True se iniciara la actividad NumPersonasActivity,
     *
     * @param numMesa, numero de mesa ingresado por el administrador de la aplicacion
     * @return true, si el numero de mesa fue asignado, false si el numero de mesa ya existe.
     */
    @Override
    public int asignar_numeroMesa(int numMesa, Activity activity) throws IOException {

        int mesaAsignada;
        mesaAsignada = servicio.asignar_numeroMesa(numMesa);

        if (mesaAsignada == 200){
            SharedPreferences preferenciasMesa = activity.getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
            SharedPreferences.Editor editorPrefMesa = preferenciasMesa.edit();
            editorPrefMesa.putInt("num_mesa", numMesa);
            editorPrefMesa.commit();

            iniciaActivity(activity, BienvenidaActivity.class);
        }
        return mesaAsignada;
    }
}
