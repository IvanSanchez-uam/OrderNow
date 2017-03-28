package com.losjuanes.ordernowapp.negocio;

import com.losjuanes.ordernowapp.api_rest.respuestas_json.PeticionCuenta;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaCuenta;
import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaOrdenar;

import java.io.IOException;

public interface ServicioOrden {

    int obtener_numOrden(int numMesa, int numPersonas) throws IOException;

    boolean ordenar(RespuestaOrdenar orden) throws IOException;

    RespuestaCuenta obtenerCuenta(int numOrden) throws IOException;

    int pagar(int numOrden) throws IOException;
}
