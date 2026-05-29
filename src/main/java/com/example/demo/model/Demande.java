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
    private String nomCommune;
    private String statut;

    public Demande(String nomCommune, java.util.Date dateDemande, int id, int idCommune, int idDemandeur, String libelleDemande, String lieu, String reference, String statut) {
        this.nomCommune = nomCommune;
        this.dateDemande = dateDemande;
        this.id = id;
        this.idCommune = idCommune;
        this.idDemandeur = idDemandeur;
        this.libelleDemande = libelleDemande;
        this.lieu = lieu;
        this.reference = reference;
        this.statut = statut;
    }

    public Demande(java.util.Date dateDemande, int id, int idCommune, int idDemandeur, String libelleDemande, String lieu, String reference) {
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

    public void setTimestampDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}

