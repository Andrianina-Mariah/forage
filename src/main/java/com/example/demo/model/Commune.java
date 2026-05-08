package com.example.demo.model;

public class Commune {
    private int id;
    private String nom;
    private int id_district;

    public Commune(int id, String nom, int id_district) {
        this.id = id;
        this.nom = nom;
        this.id_district = id_district;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_district() {
        return id_district;
    }

    public void setId_district(int id_district) {
        this.id_district = id_district;
    }
}
