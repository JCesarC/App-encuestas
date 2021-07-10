package com.develop.appprotov1;

public class Apiario {
    private String nombre;
    private String colmenas;
    private String rendimiento;

    public Apiario(String nombre, String colmenas, String rendimiento) {
        this.nombre = nombre;
        this.colmenas = colmenas;
        this.rendimiento = rendimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColmenas() {
        return colmenas;
    }

    public void setColmenas(String colmenas) {
        this.colmenas = colmenas;
    }

    public String getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(String rendimiento) {
        this.rendimiento = rendimiento;
    }
}
