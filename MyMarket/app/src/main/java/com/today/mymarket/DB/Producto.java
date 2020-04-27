package com.today.mymarket.DB;

public class Producto {
    private String id_producto;
    private int codigo;
    private String nombre;
    private String detalle;
    private String url_img;
    private double precio;
    private double pvp;
    private String id_tienda;
    private String id_proveedor;


    public Producto(){

    }

    public Producto(String id_producto, int codigo, String nombre, String detalle, String url_img, double precio, double pvp, String id_tienda, String id_proveedor) {
        this.id_producto = id_producto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.detalle = detalle;
        this.url_img = url_img;
        this.precio = precio;
        this.pvp = pvp;
        this.id_tienda = id_tienda;
        this.id_proveedor = id_proveedor;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
}
