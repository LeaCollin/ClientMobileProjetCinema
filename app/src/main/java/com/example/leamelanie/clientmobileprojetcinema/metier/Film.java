package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class Film {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titre;
    private int duree;
    private String dateSortie;
    private int budget;
    private int montantRecette;
    private Realisateur realisateur;
    private Categorie codeCategorie;

    public Film(int noFilm, String titre, int duree, String dateSortie, int budget, int montantRecette, Realisateur realisateur, Categorie codeCategorie) {
        this.id = noFilm;
        this.titre = titre;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.budget = budget;
        this.montantRecette = montantRecette;
        this.realisateur = realisateur;
        this.codeCategorie = codeCategorie;
    }

    public Film() {
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return duree;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public int getBudget() {
        return budget;
    }

    public int getMontantRecette() {
        return montantRecette;
    }

    public Realisateur getRealisateur() {
        return realisateur;
    }

    public Categorie getCodeCategorie() {
        return codeCategorie;
    }
}
