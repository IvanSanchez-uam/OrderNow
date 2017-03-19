package com.losjuanes.ordernowapp.api_rest.respuestas_json;

public class RespuestaOrdenarDetalleOrden {

    private int idProducto;
    private int persona;

    public RespuestaOrdenarDetalleOrden(int persona, int idProducto) {
        this.idProducto = idProducto;
        this.persona = persona;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getPersona() {
        return persona;
    }
}

