package com.example.chafersch;

public class Favoritos {
    private String marca;
    private String nombre_pedido;
    private String precio;
    private String urlImagen;

    public Favoritos(String marca, String nombre, String precio, String urlImagen) {
        this.marca = marca;
        this.nombre_pedido = nombre;
        this.precio = precio;
        this.urlImagen = urlImagen;
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

}
