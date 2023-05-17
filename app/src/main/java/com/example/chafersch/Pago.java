package com.example.chafersch;

public class Pago {
    private String nombre_pago;
    private String apellidos_pago;
    private String correo_pago;
    private String nif_pago;
    private String direccion_pago;
    private String codigo_postal_pago;
    private String pais_pago;
    private String telefono_pago;
    private String metodo_envio;
    private String metodo_pago;

    //Constructor
    public Pago(String nombre_pago, String apellidos_pago, String correo_pago, String nif_pago, String direccion_pago, String codigo_postal_pago, String pais_pago, String telefono_pago, String metodo_envio, String metodo_pago) {
        this.nombre_pago = nombre_pago;
        this.apellidos_pago = apellidos_pago;
        this.correo_pago = correo_pago;
        this.nif_pago = nif_pago;
        this.direccion_pago = direccion_pago;
        this.codigo_postal_pago = codigo_postal_pago;
        this.pais_pago = pais_pago;
        this.telefono_pago = telefono_pago;
        this.metodo_envio = metodo_envio;
        this.metodo_pago = metodo_pago;
    }
    public Pago(String nombre_pago, String apellidos_pago, String correo_pago, String nif_pago, String direccion_pago, String codigo_postal_pago, String pais_pago, String telefono_pago) {
        this.nombre_pago = nombre_pago;
        this.apellidos_pago = apellidos_pago;
        this.correo_pago = correo_pago;
        this.nif_pago = nif_pago;
        this.direccion_pago = direccion_pago;
        this.codigo_postal_pago = codigo_postal_pago;
        this.pais_pago = pais_pago;
        this.telefono_pago = telefono_pago;;
    }

    public String getNombre_pago() { return nombre_pago;}

    public void setNombre_pago(String nombre_pago) {
        this.nombre_pago = nombre_pago;
    }

    public String getApellidos_pago() {
        return apellidos_pago;
    }

    public void setApellidos_pago(String apellidos_pago) {
        this.apellidos_pago = apellidos_pago;
    }

    public String getCorreo_pago() {
        return correo_pago;
    }

    public void setCorreo_pago(String correo_pago) {
        this.correo_pago = correo_pago;
    }

    public String getNif_pago() {
        return nif_pago;
    }

    public void setNif_pago(String nif_pago) {
        this.nif_pago = nif_pago;
    }

    public String getDireccion_pago() {
        return direccion_pago;
    }

    public void setDireccion_pago(String direccion_pago) {
        this.direccion_pago = direccion_pago;
    }

    public String getCodigo_postal_pago() {
        return codigo_postal_pago;
    }

    public void setCodigo_postal_pago(String codigo_postal_pago) { this.codigo_postal_pago = codigo_postal_pago;}

    public String getPais_pago() {
        return pais_pago;
    }

    public void setPais_pago(String pais_pago) {
        this.pais_pago = pais_pago;
    }

    public String getTelefono_pago() {
        return telefono_pago;
    }

    public void setTelefono_pago(String telefono_pago) {
        this.telefono_pago = telefono_pago;
    }

    public String getMetodo_envio() { return metodo_envio; }

    public void setMetodo_envio(String metodo_envio) { this.metodo_envio = metodo_envio; }

    public String getMetodo_pago() { return metodo_pago; }

    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; }
}
