package com.today.mymarket.DB;

public class Factura {

    private String id_factura;
    private String codigo;
    private double subtotal;
    private double iva;
    private double total;
    private int estado;
    private String fecha;
    private String id_tienda;
    private String nombre_tienda;
    private String img_url;
    private String id_usuario;

    public Factura(){

    }

    public Factura(String id_factura, String codigo, double subtotal, double iva, double total, String fecha,
                   String id_tienda, String id_usuario, String nombre_tienda, String img_url, int estado) {
        this.id_factura = id_factura;
        this.codigo = codigo;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.fecha = fecha;
        this.id_tienda=id_tienda;
        this.id_usuario=id_usuario;
        this.nombre_tienda= nombre_tienda;
        this.img_url = img_url;
        this.estado = estado;
    }

    public String getId_factura() {
        return id_factura;
    }

    public void setId_factura(String id_factura) {
        this.id_factura = id_factura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getNombre_tienda() {
        return nombre_tienda;
    }

    public void setNombre_tienda(String nombre_tienda) {
        this.nombre_tienda = nombre_tienda;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
