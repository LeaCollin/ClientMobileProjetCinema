package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Realisateur {
    private static final long serialVersionUID = 1L;
    private Double id;
    private String nom;
    private String prenom;

    public Realisateur(Double noRea, String nomRea, String prenomRea) {
        this.id = noRea;
        this.nom = nomRea;
        this.prenom = prenomRea;
    }

    public Realisateur(){

    }

    public Double getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
