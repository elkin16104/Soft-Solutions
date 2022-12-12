package com.damd.sirop.model;

public class Tratador {
    private String uid;
    private String Division;
    private String Batallon;
    private String Caso;
    private String Bienes;
    private String Fecha;
    private String Enemigo;
    private String Total;

    public Tratador() {
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getBatallon() {
        return Batallon;
    }

    public void setBatallon(String batallon) {
        Batallon = batallon;
    }

    public String getCaso() {
        return Caso;
    }

    public void setCaso(String caso) {
        Caso = caso;
    }

    public String getBienes() {
        return Bienes;
    }

    public void setBienes(String bienes) {
        Bienes = bienes;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEnemigo() {
        return Enemigo;
    }

    public void setEnemigo(String enemigo) {
        Enemigo = enemigo;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Operacion: " + '\'' +
//                "uid='" + uid + '\'' +
                "Division='" + Division + '\'' +
                ", Batallon='" + Batallon + '\'' +
                ", Caso='" + Caso + '\'' +
                ", Bienes='" + Bienes + '\'' +
                ", Fecha=" + Fecha +
                ", Enemigo='" + Enemigo + '\'' +
                ", Total='" + Total + '\'';
    }
}
