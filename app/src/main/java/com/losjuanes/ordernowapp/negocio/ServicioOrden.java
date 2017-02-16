package com.losjuanes.ordernowapp.negocio;

import java.io.IOException;

public interface ServicioOrden {

    int obtener_numOrden(int numMesa, int numPersonas) throws IOException;
}
