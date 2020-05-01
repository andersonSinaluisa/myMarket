package com.today.mymarket.DB;

public class Tipo_Producto {
    private String id_tipo_producto;
    private String nombre;
    private int codigo;

    Tipo_Producto(){

    }

    public Tipo_Producto(String id_tipo_producto, String nombre, int codigo) {
        this.id_tipo_producto = id_tipo_producto;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getId_tipo_producto() {
        return id_tipo_producto;
    }

    public void setId_tipo_producto(String id_tipo_producto) {
        this.id_tipo_producto = id_tipo_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
