package com.losjuanes.ordernowapp.presentacion.controladores;


import android.app.Activity;
import android.content.Intent;

import com.losjuanes.ordernowapp.api_rest.respuestas_json.RespuestaCuenta;
import com.losjuanes.ordernowapp.negocio.ImplementacionServicioOrden;
import com.losjuanes.ordernowapp.negocio.ServicioOrden;

import java.io.IOException;

public class ImplementacionControladorCuenta implements ControladorCuenta {

    ServicioOrden servicio = new ImplementacionServicioOrden();

    @Override
    public void iniciaActivity(Activity activityInicio, Class activityDestino) {
        activityInicio.startActivity(new Intent(activityInicio,activityDestino));
    }

    /**
     * Se conectara con el servicio para obtener todo el detalle de una orden, es decir,
     * todos los productos y el monto total de la orden.
     * @param numOrden, id de la orden de la cual se desea obtener la cuenta.
     * @return el detalle de la orden con id: numOrden.
     */
    @Override
    public RespuestaCuenta obtenerCuenta(int numOrden) throws IOException {
        return servicio.obtenerCuenta(numOrden);
    }

    /**
     * Se conectara con el servicio para pagar una orden.
     * @param numOrden, id de la orden a pagar.
     * @return 200 si la orden fue pagada, 500 en caso contrario.
     */
    @Override
    public int pagar(int numOrden) throws IOException {
        return servicio.pagar(numOrden);
    }
}
