package com.losjuanes.ordernowapp.api_rest.respuestas_json;

public class RespuestaDetalleCuenta {

    private int persona;
    private String nomProducto;
    private float precio;

    public RespuestaDetalleCuenta(int persona, String nomProducto, float precio) {
        this.persona = persona;
        this.nomProducto = nomProducto;
        this.precio = precio;
    }

    public int getPersona() {
        return persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
