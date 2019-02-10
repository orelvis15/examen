package com.examen.examenandroid.banco;

public class Banco {

    private String id;
    private String nombre;
    private String urlBitmap;

    public Banco(String id, String nombre, String urlBitmap) {
        this.id = id;
        this.nombre = nombre;
        this.urlBitmap = urlBitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlBitmap() {
        return urlBitmap;
    }

    public void setUrlBitmap(String urlBitmap) {
        this.urlBitmap = urlBitmap;
    }

}