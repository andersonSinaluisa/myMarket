package com.today.mymarket.DB;

public class Ventas {
    private String id_venta;
    private  String id_producto_tienda;
    private String fecha;
    private String id_usuario;

    public Ventas(){

    }

    public Ventas(String id_venta, String id_producto_tienda, String fecha,String id_usuario) {
        this.id_venta = id_venta;
        this.id_producto_tienda = id_producto_tienda;
        this.fecha = fecha;
        this.id_usuario = id_usuario;

    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getId_producto_tienda() {
        return id_producto_tienda;
    }

    public void setId_producto_tienda(String id_producto_tienda) {
        this.id_producto_tienda = id_producto_tienda;
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
}
