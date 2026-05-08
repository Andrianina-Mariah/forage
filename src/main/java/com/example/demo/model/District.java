package com.example.demo.model;

public class District {
    private int id;
    private String nom;
    private int id_region;

    public District(int id, String nom, int id_region) {
        this.id = id;
        this.nom = nom;
        this.id_region = id_region;
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

    public int getId_region() {
        return id_region;
    }

    public void setId_region(int id_region) {
        this.id_region = id_region;
    }
}
