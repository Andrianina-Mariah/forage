package com.example.demo.model;

import java.time.LocalDateTime;

public class Devis {
    private int id;
    private int idDemande;
    private String type;
    private java.time.LocalDateTime date;
    private String description;

    public Devis(LocalDateTime date, String description, int id, int idDemande, String type) {
        this.date = date;
        this.description = description;
        this.id = id;
        this.idDemande = idDemande;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setTimestamp(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
