package com.example.demo.model;

import java.util.Date;

public class Demande_statut {
    private int id;
    private int typeDemande;
    private int typeStatut;
    private String observation;
    private java.util.Date dateDebut;
    private java.util.Date dateFin;
    private String libelleStatut;
    private String libelleDemande;

    public Demande_statut(String observation, Date dateDebut, Date dateFin, int id, int typeDemande, int typeStatut) {
        this.observation = observation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = id;
        this.typeDemande = typeDemande;
        this.typeStatut = typeStatut;
    }
        public Demande_statut(String observation, Date dateDebut, Date dateFin, int id, int typeDemande, int typeStatut, String libelleStatut, String libelleDemande) {
        this.observation = observation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = id;
        this.typeDemande = typeDemande;
        this.typeStatut = typeStatut;
        this.libelleStatut = libelleStatut;
        this.libelleDemande = libelleDemande;
    }

    public Demande_statut() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }
    
    public void setObservation(String observation) {
        this.observation = observation;
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

    public void setTimestampDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setTimestampFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLibelleStatut() {
        return libelleStatut;
    }

    public void setLibelleStatut(String libelleStatut) {
        this.libelleStatut = libelleStatut;
    }

    public String getLibelleDemande() {
        return libelleDemande;
    }

    public void setLibelleDemande(String libelleDemande) {
        this.libelleDemande = libelleDemande;
    }
}
