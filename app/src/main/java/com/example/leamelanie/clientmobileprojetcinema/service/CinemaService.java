package com.example.leamelanie.clientmobileprojetcinema.service;

import com.example.leamelanie.clientmobileprojetcinema.metier.Categorie;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.Realisateur;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeaC on 04/01/2018.
 */

public class CinemaService {

    public static List<Realisateur> realisateurs = new ArrayList<>();
    public static List<Categorie> categories = new ArrayList<>();


    //Méthode à utiliser dans les doinbackgrounds -> récupère filmDAO et retourne filmDTO
    public static List<FilmDTO> filmDAOtoDTO(List<FilmDAO> filmsDAO ) {
        List<FilmDTO> mesFilmsFormates = new ArrayList<>();
        Realisateur real = null;
        Categorie cat = null;
        for (FilmDAO f : filmsDAO
                ) {
            //Sérialisation / désérialisation du JSON
            Gson g = new Gson();

            if (f.getRealisateur() instanceof Number) {
                for (Realisateur r : realisateurs) {
                    if (r.getId().equals(f.getRealisateur())){
                        real = r;
                        break;
                    }
                }
            } else {
                String realString = ""+f.getRealisateur();
                real = g.fromJson(realString, Realisateur.class);

            }

            if (f.getCategorie() instanceof String) {
                for (Categorie c : categories) {
                    if (c.getCode().equals(f.getCategorie())){
                        cat = c;
                        break;
                    }
                }
            } else {
                String catString = ""+f.getCategorie();
                cat = g.fromJson(catString, Categorie.class);

            }

            FilmDTO film = new FilmDTO(f.getId(),f.getTitre(),f.getDuree(),f.getDateSortie(),f.getBudget(),f.getMontantRecette(),real,cat);
            mesFilmsFormates.add(film);
        }
        return mesFilmsFormates;
    }
}
