package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class FilmDTO {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titre;
    private int duree;
    private String dateSortie;
    private int budget;
    private int montantRecette;
    private Realisateur realisateur;
    private Categorie categorie;

    public FilmDTO(int id, String titre, int duree, String dateSortie, int budget, int montantRecette, Realisateur realisateur, Categorie codeCategorie) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.budget = budget;
        this.montantRecette = montantRecette;
        this.realisateur = realisateur;
        this.categorie = codeCategorie;
    }

    public FilmDTO(String titre, int duree, String dateSortie, int budget, int montantRecette, Realisateur realisateur, Categorie codeCategorie) {
        this.titre = titre;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.budget = budget;
        this.montantRecette = montantRecette;
        this.realisateur = realisateur;
        this.categorie = codeCategorie;
    }

    public FilmDTO() {
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setRealisateur(Realisateur realisateur) {
        this.realisateur = realisateur;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
