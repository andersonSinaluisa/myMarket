package com.today.mymarket.DB;

public class Detalle_factura {
    private String id_detalle_factura;
    private String id_compra;
    private String id_venta;
    private String id_factura;
    private String fecha;
    private String id_usuario;
    private String id_tienda;

    public Detalle_factura(){


    }

    public Detalle_factura(String id_detalle_factura, String id_compra, String id_venta, String id_fectura, String fecha, String id_usuario, String id_tienda) {
        this.id_detalle_factura = id_detalle_factura;
        this.id_compra = id_compra;
        this.id_venta = id_venta;
        this.id_factura = id_fectura;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.id_tienda = id_tienda;
    }

    public String getId_detalle_factura() {
        return id_detalle_factura;
    }

    public void setId_detalle_factura(String id_detalle_factura) {
        this.id_detalle_factura = id_detalle_factura;
    }

    public String getId_compra() {
        return id_compra;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getId_fectura() {
        return id_factura;
    }

    public void setId_fectura(String id_fectura) {
        this.id_factura = id_fectura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }
}
