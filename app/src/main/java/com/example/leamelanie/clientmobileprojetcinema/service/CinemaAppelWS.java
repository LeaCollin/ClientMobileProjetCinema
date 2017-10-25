package com.example.leamelanie.clientmobileprojetcinema.service;


import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by LeaC on 25/10/2017.
 */

public interface CinemaAppelWS {

    public static final String BASE_URL = "http://192.168.1.16:8080";

    @GET("/Acteur/liste")
    List<Acteur> mesActeurs();


}
