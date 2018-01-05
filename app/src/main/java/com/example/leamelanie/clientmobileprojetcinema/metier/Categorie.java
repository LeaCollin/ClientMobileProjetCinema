package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Categorie {
    private static final long serialVersionUID = 1L;
    private String code;
    private String libelle;

    public Categorie(String codeCat, String libelleCat) {
        this.code = codeCat;
        this.libelle = libelleCat;
    }

    public Categorie(){

    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }
}
