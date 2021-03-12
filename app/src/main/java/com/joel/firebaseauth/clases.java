package com.joel.firebaseauth;

public class clases {
    String id, correo,cedula,nombre,pais,provincia,sexo;



    public clases(String id, String correo) {
        this.id = id;
        this.correo = correo;
    }

    public clases(String cedula, String nombre, String pais, String provincia, String sexo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pais = pais;
        this.provincia = provincia;
        this.sexo = sexo;
    }



    public String getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getSexo() {
        return sexo;
    }
}
