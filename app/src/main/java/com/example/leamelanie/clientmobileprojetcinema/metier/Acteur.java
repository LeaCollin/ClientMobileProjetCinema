package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 18/10/2017.
 */

public class Acteur {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private String prenom;
    private String dateNaiss;
    private String dateDeces;


    public Acteur(int noActeur, String nomActeur, String prenomActeur, String dateNaissance, String dateDeces) {
        super();
        this.id = noActeur;
        this.nom = nomActeur;
        this.prenom = prenomActeur;
        this.dateNaiss = dateNaissance;
        this.dateDeces= dateDeces;
    }

    public Acteur() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public String getDateDeces() {
        return dateDeces;
    }
    //getteurs setteurs

}
