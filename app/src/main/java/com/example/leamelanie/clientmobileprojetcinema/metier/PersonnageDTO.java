package com.example.leamelanie.clientmobileprojetcinema.metier;

/**
 * Created by LeaC on 19/10/2017.
 */

public class PersonnageDTO {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private Acteur acteur;
    private FilmDAO film;

    public PersonnageDTO(int id,String nomPers, Acteur acteur, FilmDAO film) {
        this.id = id;
        this.nom = nomPers;
        this.acteur = acteur;
        this.film = film;
    }

    public int getId() {
        return id;
    }
    public String getNomPers() {
        return nom;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public FilmDAO getFilm() {
        return film;
    }
}
