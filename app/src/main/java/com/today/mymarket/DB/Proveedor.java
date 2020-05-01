package com.today.mymarket.DB;

public class Proveedor {
    private String id_proveedor;
    private String RUC;
    private String nombre_empresa;
    private String representante;
    private String id_tienda;


    public Proveedor(){
        
    }

    public Proveedor(String id_proveedor, String RUC, String nombre_empresa, String representante, String id_tienda) {
        this.id_proveedor = id_proveedor;
        this.RUC = RUC;
        this.nombre_empresa = nombre_empresa;
        this.representante = representante;
        this.id_tienda = id_tienda;
    }


    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }
}
