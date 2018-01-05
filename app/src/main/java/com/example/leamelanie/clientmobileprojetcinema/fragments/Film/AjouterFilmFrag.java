package com.example.leamelanie.clientmobileprojetcinema.fragments.Film;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.Categorie;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.Realisateur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by LeaC on 25/10/2017.
 */

public class AjouterFilmFrag extends Fragment {

    Button requeteAjout;
    List<String> nomsRealisateurs = new ArrayList<>();
    List<String> codesCategories = new ArrayList<>();
    Spinner realisateurs;
    Spinner categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_movies, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Remplir les spinners
        realisateurs = getActivity().findViewById(R.id.editionRealisateur);
        for (Realisateur r : CinemaService.realisateurs
                ) {
            nomsRealisateurs.add(r.getPrenom()+" "+r.getNom());
        }
        remplirSpinner(nomsRealisateurs,realisateurs);
        //Remplir les spinners
        categories = getActivity().findViewById(R.id.editionCategorie);
        for (Categorie c : CinemaService.categories
                ) {
            codesCategories.add(c.getLibelle());
        }
        remplirSpinner(codesCategories,categories);

        requeteAjout = (Button) getActivity().findViewById(R.id.envoyerRequeteFilm);
        requeteAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmDTO nouveauFilm = creerFilm();
                sendNetworkRequest(nouveauFilm);
            }
        });
    }

    public void remplirSpinner(List<String> items, Spinner spinner) {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private void sendNetworkRequest(FilmDTO film){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(CinemaAppelWS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CinemaAppelWS cinemaAppel = retrofit.create(CinemaAppelWS.class);

        Call<FilmDTO> call = cinemaAppel.createFilm(film);
        call.enqueue(new Callback<FilmDTO>() {

            @Override
            public void onResponse(Call<FilmDTO> call, Response<FilmDTO> response) {
                if (response.isSuccessful()) {
                    System.out.println("REPONSE "+response.body());
                    Toast.makeText(getActivity(),"Ajout réussi",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.d("FAILURE MSG"," : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Ajout échoué",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmDTO> call, Throwable t) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public FilmDTO creerFilm(){

        EditText titre = (EditText) getActivity().findViewById(R.id.editionTitre);
        final String titreFilm = titre.getText().toString();

        EditText duree = (EditText) getActivity().findViewById(R.id.editionDuree);
        final int dureeFilm = Integer.parseInt(duree.getText().toString());

        EditText dateSortie = (EditText) getActivity().findViewById(R.id.editionDate);
        final String date = dateSortie.getText().toString();

        EditText budget = (EditText) getActivity().findViewById(R.id.editionBudget);
        final int budgetFilm = Integer.parseInt(budget.getText().toString());

        EditText recette = (EditText) getActivity().findViewById(R.id.editionRecette);
        final int recetteFilm = Integer.parseInt(recette.getText().toString());

        //A transformer en spinner
        Spinner realisateur = (Spinner) getActivity().findViewById(R.id.editionRealisateur);
        final String realisateurFilm = String.valueOf(realisateur.getSelectedItem());
        Realisateur real = null;
        for (Realisateur r: CinemaService.realisateurs) {
            if (realisateurFilm.equals(r.getPrenom()+" "+r.getNom())){
                real = r;
                break;
            }
        }

        Spinner categorie = (Spinner) getActivity().findViewById(R.id.editionCategorie);
        final String categorieFilm = String.valueOf(categorie.getSelectedItem());
        Categorie cat = null;
        for (Categorie c: CinemaService.categories) {
            if (categorieFilm.equals(c.getLibelle())){
                cat = c;
                break;
            }
        }

        FilmDTO nouveauFilm = new FilmDTO(titreFilm,dureeFilm,date,budgetFilm,recetteFilm,real,cat);

        return nouveauFilm;
    }

}
