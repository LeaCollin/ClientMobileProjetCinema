package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Film {
    private static final long serialVersionUID = 1L;
    private int noFilm;
    private String titre;
    private int duree;
    private String dateSortie;
    private int budget;
    private int montantRecette;
    private int noRea;
    private int codeCat;

    public Film(int noFilm, String titre, int duree, String dateSortie, int budget, int montantRecette, int noRea, int codeCat) {
        this.noFilm = noFilm;
        this.titre = titre;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.budget = budget;
        this.montantRecette = montantRecette;
        this.noRea = noRea;
        this.codeCat = codeCat;
    }

    public Film() {
    }

    //getteurs setteurs
}
