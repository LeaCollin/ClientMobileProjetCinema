package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Personnage {
    private static final long serialVersionUID = 1L;
    private int noFilm;
    private int noAct;
    private String nomPers;

    public Personnage(int noFilm, int noAct, String nomPers) {
        this.noFilm = noFilm;
        this.noAct = noAct;
        this.nomPers = nomPers;
    }

    public Personnage() {
    }

    //getteurs and setteurs
}
