package com.losjuanes.ordernowapp.presentacion.controladores;

import android.app.Activity;
import java.io.IOException;

public interface ControladorAsignarMesa {

    void iniciaActivity(Activity activityInicio, Class activityDestino);

    int asignar_numeroMesa(int numMesa,Activity activity) throws IOException;
}
