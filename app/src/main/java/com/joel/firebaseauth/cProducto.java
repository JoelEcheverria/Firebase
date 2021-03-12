package com.joel.firebaseauth;

public class cProducto {

    String codigo,   producto,stock,  costo,   venta;

    public cProducto(String codigo, String producto, String stock, String costo, String venta) {
        this.codigo = codigo;
        this.producto = producto;
        this.stock = stock;
        this.costo = costo;
        this.venta = venta;
    }

    public cProducto() {
    }

    public cProducto(String stock) {
        this.stock = stock;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProducto() {
        return producto;
    }

    public String getStock() {
        return stock;
    }

    public String getCosto() {
        return costo;
    }

    public String getVenta() {
        return venta;
    }
}
