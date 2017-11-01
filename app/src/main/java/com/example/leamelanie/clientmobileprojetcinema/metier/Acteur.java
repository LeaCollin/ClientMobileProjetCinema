package com.example.leamelanie.clientmobileprojetcinema.metier;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LeaC on 18/10/2017.
 */

public class Acteur {
    private static final long serialVersionUID = 1L;
    private int id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("dateNaiss")
    private String dateNaiss;
    @SerializedName("dateDeces")
    private String dateDeces;


    public Acteur(String nomActeur, String prenomActeur, String dateNaissance, String dateDeces) {
        this.nom = nomActeur;
        this.prenom = prenomActeur;
        this.dateNaiss = dateNaissance;
        this.dateDeces= dateDeces;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public void setDateDeces(String dateDeces) {
        this.dateDeces = dateDeces;
    }
}
