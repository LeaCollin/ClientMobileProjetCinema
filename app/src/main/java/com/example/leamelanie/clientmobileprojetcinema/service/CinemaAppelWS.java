package com.example.leamelanie.clientmobileprojetcinema.service;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.Categorie;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.Realisateur;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;


/**
 * Created by LeaC on 25/10/2017.
 */

public interface CinemaAppelWS {

    public static final String BASE_URL = "http://192.168.42.98:8080"; //42.98:8080"; //ipconfig perso ATTENTION certaines wifi ne marhce pas -> se mettre en cablé

    @GET("/Acteur/liste")
    List<Acteur> mesActeurs();

    @GET("/Film/liste")
    List<FilmDAO> mesFilms();

    @GET("/Film/acteurs/{id}")
    List<Acteur> mesActeursPourUnFilm(@retrofit.http.Path("id") int idFilm);

    @GET("/Realisateur/liste")
    List<Realisateur> mesRealisateurs();

    @GET("/Categorie/liste")
    List<Categorie> mesCategories();

    @POST("/Acteur/ajout")
    Call<Acteur> createActeur(@Body Acteur nouvelActeur);

    @POST("/Film/ajout")
    Call<FilmDTO> createFilm(@Body FilmDTO nouveauFilm);

    @PUT("/Acteur/{id}")
    Call<Acteur> editActeur(@Path("id") int id, @Body Acteur acteur);

    @PUT("/Film/{id}")
    Call<FilmDTO> editFilm(@Path("id") int id, @Body FilmDTO film);

    @GET("/Acteur/{id}")
    Acteur monActeur(@retrofit.http.Path("id") int acteurId);

    @GET("/Film/{id}")
    FilmDAO monFilm(@retrofit.http.Path("id") int filmId);

    @DELETE("/Acteur/{id}")
    Call<Acteur> deleteActeur(@retrofit2.http.Path("id") int acteurId);

    @DELETE("/Film/{id}")
    Call<FilmDAO> deleteFilm(@retrofit2.http.Path("id") int filmId);


}
