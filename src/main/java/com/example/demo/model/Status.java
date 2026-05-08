package model;


public class Status {
    private int id;
    private String libelle;

    public Status(String libelle) {
        this.libelle = libelle;
    }

    public Status(int id, String libelle) {
        setId(id);
        setLibelle(libelle);
    }

    public Status() {
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


    public void afficher() {
        System.out.println(
            "id: " + this.id
                + ", nom: " + this.libelle
        );
    }
}
