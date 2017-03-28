package com.losjuanes.ordernowapp.api_rest.respuestas_json;

import java.util.ArrayList;

public class RespuestaCuenta {

    private ArrayList<RespuestaDetalleCuenta> productos;
    private float monto;

    public RespuestaCuenta(ArrayList<RespuestaDetalleCuenta> productos, float monto) {
        this.productos = productos;
        this.monto = monto;
    }

    public ArrayList<RespuestaDetalleCuenta> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<RespuestaDetalleCuenta> productos) {
        this.productos = productos;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
