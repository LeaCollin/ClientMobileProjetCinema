package com.example.leamelanie.clientmobileprojetcinema.metier;

import com.google.gson.Gson;

/**
 * Created by LeaC on 19/10/2017.
 */

public class FilmDAO {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titre;
    private int duree;
    private String dateSortie;
    private int budget;
    private int montantRecette;
    private Object realisateur;
    private Object categorie;

    public FilmDAO(String titre, int duree, String dateSortie, int budget, int montantRecette, int realisateur, String codeCategorie) {
        this.titre = titre;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.budget = budget;
        this.montantRecette = montantRecette;
        this.realisateur = realisateur;
        this.categorie = codeCategorie;
    }

    public FilmDAO() {
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

    public Object getRealisateur() {
        return realisateur;
    }

    public Object getCategorie() {
        return categorie;
    }
}
