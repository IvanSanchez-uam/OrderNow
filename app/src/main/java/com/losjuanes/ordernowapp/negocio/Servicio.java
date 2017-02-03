package com.losjuanes.ordernowapp.negocio;

import android.app.Activity;

import java.io.IOException;

public interface Servicio {

    boolean asigna_numeroMesa(int numMesa) throws IOException;
}
