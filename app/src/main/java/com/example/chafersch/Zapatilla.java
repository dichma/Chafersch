package com.example.chafersch;

public class Zapatilla {
    private String Codigo;
    private String Foto;
    private String Id_Categoria;
    private String Marca;
    private  String Nombre;
    private boolean Oferta;
    private  String Precio;
    private String Stock;
    private String Talla;




    // Define 9 variables de instancia privadas que representan las características de la zapatilla
    public Zapatilla(String Codigo, String Foto, String Categoria , String Marca,String Nombre,Boolean Oferta , String Precio, String Stock, String Talla) {
        this.Codigo = Codigo;
        this.Foto = Foto;
        this.Id_Categoria = Categoria;
        this.Marca = Marca;
        this.Nombre = Nombre;
        this.Oferta = Oferta;
        this.Precio = Precio;
        this.Stock = Stock;
        this.Talla = Talla;
    }

    public Zapatilla(String nombre, String precio, String foto, String marca) {
        this.Nombre = nombre;
        this.Precio = precio;
        this.Foto = foto;
        this.Marca = marca;
    }
    // Métodos getter y setter para acceder y modificar las variables de instancia privadas

    ///CODIGO/////
    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    ////FOTO/////
    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    ///CATEGORIA//////
    public void getId_Categoria(Boolean id_Categoria){

    }
    public String getId_Categoria() {
        return Id_Categoria;
    }

    //MARCA//

    public String getMarca(){
        return Marca;
    }
    public void setMarca(String marca) {
        Marca = marca;
    }

    ////NOMBRE///
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    //OFERTA//


    public boolean getOferta() {
        return Oferta;
    }

    public void setOferta(boolean oferta) {
        Oferta = oferta;
    }

    ////PRECIO////
    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    ////STOCK
    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    //TALLA//
    public String getTalla() {
        return Talla;
    }

    public void setTalla(String talla) {
        Talla = talla;
    }

    }
