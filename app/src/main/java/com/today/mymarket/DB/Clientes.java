package com.today.mymarket.DB;

public class Clientes  {
    private String id_cliente;
    private String nombre;
    private String apellido;
    private String direccion;
    private long latitud;
    private long longitud;
    private String id_tienda;
    private String id_usuario;


    public Clientes(){

    }

    public Clientes(String id_cliente, String nombre, String apellido, String direccion, long latitud, long longitud, String id_tienda, String id_usuario) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id_tienda = id_tienda;
        this.id_usuario = id_usuario;
    }


    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
