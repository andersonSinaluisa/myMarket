package com.today.mymarket.DB;

public class Tienda {
    private String id_tienda;
    private String nombre;
    private String tipo_id;
    private String identificacion;
    private int estado;
    private String id_usuario;
    private double longitud;
    private double latitud;
    private String direccion;
    private String telefono;


    public Tienda(){

    }

    public Tienda(String id_tienda, String nombre, String tipo_id, String identificacion, int estado, String id_usuario, double longitud, double latitud, String direccion, String telefono) {
        this.id_tienda = id_tienda;
        this.nombre = nombre;
        this.tipo_id = tipo_id;
        this.identificacion = identificacion;
        this.estado = estado;
        this.id_usuario = id_usuario;
        this.longitud = longitud;
        this.latitud = latitud;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
