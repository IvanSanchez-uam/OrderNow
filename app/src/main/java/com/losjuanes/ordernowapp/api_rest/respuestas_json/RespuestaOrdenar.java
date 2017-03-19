package com.losjuanes.ordernowapp.api_rest.respuestas_json;

import java.util.ArrayList;

public class RespuestaOrdenar {

    private int numOrden;
    private ArrayList<RespuestaOrdenarDetalleOrden> orden;

    public RespuestaOrdenar(int numOrden, ArrayList<RespuestaOrdenarDetalleOrden> orden) {
        this.numOrden = numOrden;
        this.orden = orden;
    }

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public ArrayList<RespuestaOrdenarDetalleOrden> getOrden() {
        return orden;
    }

    public void setOrden(ArrayList<RespuestaOrdenarDetalleOrden> orden) {
        this.orden = orden;
    }
}