package com.today.mymarket.DB;

public class Compras  {
private String id_compra;
private String fecha;
private String observacion;
private String id_tienda;
private int n_compra;
private String id_producto;


    public Compras(){

    }


    public Compras(String id_compra, String fecha, String observacion, String id_tienda, String id_producto, String id_factura, int n_compra) {
        this.id_compra = id_compra;
        this.fecha = fecha;
        this.observacion = observacion;
        this.id_tienda = id_tienda;
        this.id_producto = id_producto;
        this.n_compra=n_compra;

    }

    public String getId_compra() {
        return id_compra;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public int getN_compra() {
        return n_compra;
    }

    public void setN_compra(int n_compra) {
        this.n_compra = n_compra;
    }
}
