package com.example.demo.model;

public class Devis_detail {
    private int id;
    private int idDevis;
    private String libelle;
    private double quantite;
    private double prix;

    public Devis_detail(int id, int idDevis, String libelle, double prix, double quantite) {
        this.id = id;
        this.idDevis = idDevis;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Devis_detail(String libelle, double quantite, double prix, int idDevis) {
        this(0, idDevis, libelle, prix, quantite);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDevis() {
        return idDevis;
    }

    public void setIdDevis(int idDevis) {
        this.idDevis = idDevis;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
