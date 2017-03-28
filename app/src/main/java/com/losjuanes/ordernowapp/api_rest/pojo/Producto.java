package com.losjuanes.ordernowapp.api_rest.pojo;

public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String imagen;
    private float precio;

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getPrecio() {
        return precio;
    }

}
