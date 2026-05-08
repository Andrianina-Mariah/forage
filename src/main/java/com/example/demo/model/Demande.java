package com.example.demo.model;

import java.util.Date;

public class Demande {
    private int id;
    private String lieu;
    private String reference;
    private int idDemandeur;
    private int idCommune;
    private String libelleDemande;
    private java.util.Date dateDemande;

    public Demande(Date dateDemande, int id, int idCommune, int idDemandeur, String libelleDemande, String lieu, String reference) {
        this.dateDemande = dateDemande;
        this.id = id;
        this.idCommune = idCommune;
        this.idDemandeur = idDemandeur;
        this.libelleDemande = libelleDemande;
        this.lieu = lieu;
        this.reference = reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getIdDemandeur() {
        return idDemandeur;
    }

    public void setIdDemandeur(int idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public int getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(int idCommune) {
        this.idCommune = idCommune;
    }

    public String getLibelleDemande() {
        return libelleDemande;
    }

    public void setLibelleDemande(String libelleDemande) {
        this.libelleDemande = libelleDemande;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

}
