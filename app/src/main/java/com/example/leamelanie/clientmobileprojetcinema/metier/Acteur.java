package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 18/10/2017.
 */

public class Acteur {
    private static final long serialVersionUID = 1L;
    private int noActeur;
    private String nomActeur;
    private String prenomActeur;
    private String dateNaissance;
    private String dateDeces;


    public Acteur(int noActeur, String nomActeur, String prenomActeur, String dateNaissance, String dateDeces) {
        super();
        this.noActeur= noActeur;
        this.nomActeur = nomActeur;
        this.prenomActeur = prenomActeur;
        this.dateNaissance = dateNaissance;
        this.dateDeces= dateDeces;
    }

    public Acteur() {
    }

    public int getNoActeur() {
        return noActeur;
    }

    public String getNomActeur() {
        return nomActeur;
    }

    public String getPrenomActeur() {
        return prenomActeur;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getDateDeces() {
        return dateDeces;
    }
    //getteurs setteurs

}
