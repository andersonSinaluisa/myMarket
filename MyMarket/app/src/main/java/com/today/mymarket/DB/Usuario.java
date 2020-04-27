package com.today.mymarket.DB;

public class Usuario {
    private String id_usuario;
    private String usuario;
    private String pass;
    private int tipo;
    private String id_persona;
    private int estado;

    public Usuario(){

    }

    public Usuario(String id_usuario, String usuario, String pass, int tipo, String id_persona, int estado) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.pass = pass;
        this.tipo = tipo;
        this.id_persona = id_persona;
        this.estado = estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getId_persona() {
        return id_persona;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }
}
