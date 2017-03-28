package com.losjuanes.ordernowapp.api_rest.respuestas_json;

public class PeticionCuenta {
    int idOrden;

    public PeticionCuenta(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }
}
