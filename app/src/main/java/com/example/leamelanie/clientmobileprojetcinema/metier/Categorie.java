package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Categorie {
    private static final long serialVersionUID = 1L;
    private int codeCat;
    private String libelleCat;

    public Categorie(int codeCat, String libelleCat) {
        this.codeCat = codeCat;
        this.libelleCat = libelleCat;
    }

    public Categorie(){

    }

    public int getCodeCat() {
        return codeCat;
    }

    public String getLibelleCat() {
        return libelleCat;
    }
}
