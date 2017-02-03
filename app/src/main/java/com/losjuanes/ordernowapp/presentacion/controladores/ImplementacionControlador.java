package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.losjuanes.ordernowapp.negocio.ImplementacionServicio;
import com.losjuanes.ordernowapp.negocio.Servicio;
import com.losjuanes.ordernowapp.presentacion.vistas.Bienvenida;
import com.losjuanes.ordernowapp.presentacion.vistas.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;

public class ImplementacionControlador implements Controlador {

    private Servicio servicio = new ImplementacionServicio();
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
     * Invoca al método asigna_numeroMesa() de implementacionServicio.
     * Cuando el método asigna_numeroMesa() regresa True se iniciara la actividad Bienvenida,
     *
     * @param numMesa, numero de mesa ingresado por el administrador de la aplicacion
     * @return true, si el numero de mesa fue asignado, false en caso contrario.
     */
    @Override
    public boolean asigna_numeroMesa(int numMesa, Activity activity) {
        boolean mesaAsignada = false;

        mesaAsignada = servicio.asigna_numeroMesa(numMesa);

        if (mesaAsignada){
            SharedPreferences preferenciasMesa = activity.getApplicationContext().getSharedPreferences("PreferenciasMesa", MODE_PRIVATE);
            SharedPreferences.Editor editorPrefMesa = preferenciasMesa.edit();
            editorPrefMesa.putInt("num_mesa", numMesa);
            editorPrefMesa.commit();

            iniciaActivity(activity, Bienvenida.class);

            mesaAsignada = true;
        }
        return mesaAsignada;
    }


}
