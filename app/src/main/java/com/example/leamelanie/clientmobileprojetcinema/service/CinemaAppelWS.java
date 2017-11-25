package com.example.leamelanie.clientmobileprojetcinema.service;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.Film;

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

    public static final String BASE_URL = "http://192.168.1.97:8080"; //ipconfig perso

    @GET("/Acteur/liste")
    List<Acteur> mesActeurs();

    @GET("/Film/liste")
    List<Film> mesFilms();

    @POST("/Acteur/ajout")
    Call<Acteur> createActeur(@Body Acteur nouvelActeur);

    @PUT("/Acteur/{id}")
    Call<Acteur> editActeur(@Path("id") int id, @Body Acteur acteur);

    @GET("/Acteur/{id}")
    Acteur monActeur(@retrofit.http.Path("id") int acteurId);

    @DELETE("/Acteur/{id}")
    Call<Acteur> deleteActeur(@retrofit2.http.Path("id") int acteurId);

}
