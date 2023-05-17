package com.example.chafersch;

import android.widget.Spinner;

public class Pedido {
    private String cantidad;
    private String marca;
    private String nombre_pedido;
    private String precio;
    private String talla;
    private String urlImagen;

    public Pedido(String marca, String nombre, String precio, String urlImagen, String talla, String cantidad) {
        this.marca = marca;
        this.nombre_pedido = nombre;
        this.precio = precio;
        this.urlImagen = urlImagen;
        this.talla = talla;
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre_pedido;
    }

    public void setNombre(String nombre) {
        this.nombre_pedido = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return urlImagen;
    }

    public void setFoto(String foto) {
        this.urlImagen = urlImagen;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getCantidad() {return cantidad;}

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
