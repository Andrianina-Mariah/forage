package com.example.demo.model;


public class Statut {
    private int id;
    private String libelle;

    public Statut(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }


    public void afficher() {
        System.out.println(
            "id: " + this.id
                + ", nom: " + this.libelle
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
