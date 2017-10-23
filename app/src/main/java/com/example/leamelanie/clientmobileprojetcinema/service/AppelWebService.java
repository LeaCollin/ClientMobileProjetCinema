package com.example.leamelanie.clientmobileprojetcinema.service;

import com.example.leamelanie.clientmobileprojetcinema.meserreurs.MonException;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeaC on 18/10/2017.
 */

public class AppelWebService {

    private Gson unGson = new Gson();

    public AppelWebService() {
    }

    // méthode qui va lire le flux Json et le stocker dans un tableau
    // Les données sont ensuite transférées du tableau json dans la collection de l'objet;
    public List<Acteur> getListeActeurs(String monUrl) throws MonException {
        List<Acteur> mesActeurs = null;

        try {
            URL uneUrl = new URL(monUrl);
            System.out.println("URL: "+monUrl);

            HttpURLConnection connection = (HttpURLConnection) uneUrl.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            // On récupère les données au format json
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            // On récupère les données dans une chaîne de caractères
            // sans passer par la bibliothèque Gson
            // On utilise JSONArray
            String json = sb.toString();
            System.out.println("JSON: "+json);

            Type typeOfObjectsList = new TypeToken<ArrayList<Acteur>>() {
            }.getType();
            mesActeurs = new Gson().fromJson(json, typeOfObjectsList);
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {


            new MonException(e.getMessage(), "Erreur Appel WS");
            return null;
        }
        return mesActeurs;

    }
}
