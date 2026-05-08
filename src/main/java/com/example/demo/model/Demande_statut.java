package com.example.demo.model;

import java.util.Date;

public class Demande_statut {
    private int id;
    private int typeDemande;
    private int typeStatut;
    private java.util.Date dateDebut;
    private java.util.Date dateFin;

    public Demande_statut(Date dateDebut, Date dateFin, int id, int typeDemande, int typeStatut) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = id;
        this.typeDemande = typeDemande;
        this.typeStatut = typeStatut;
    }
    public Demande_statut() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(int typeDemande) {
        this.typeDemande = typeDemande;
    }

    public int getTypeStatut() {
        return typeStatut;
    }

    public void setTypeStatut(int typeStatut) {
        this.typeStatut = typeStatut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
