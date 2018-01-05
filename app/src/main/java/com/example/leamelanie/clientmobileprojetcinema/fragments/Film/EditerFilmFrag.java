package com.example.leamelanie.clientmobileprojetcinema.fragments.Film;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.example.leamelanie.clientmobileprojetcinema.metier.Categorie;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.Realisateur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeaC on 02/11/2017.
 */

@SuppressLint("ValidFragment")
public class EditerFilmFrag extends Fragment {

    Button requeteEdition;
    Button supprimerButton;
    List<String> nomsRealisateurs = new ArrayList<>();
    List<String> codesCategories = new ArrayList<>();
    Spinner realisateurs;
    Spinner categories;

    int filmID;

    @SuppressLint("ValidFragment")
    public EditerFilmFrag(int id) {
        filmID = id;
    }

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

        new WS().execute();

        //Actions boutons
        requeteEdition = (Button) getActivity().findViewById(R.id.envoyerRequeteFilm);
        requeteEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmDTO filmEdit = editFilm();
                sendNetworkRequestEdit(filmEdit);
            }
        });


        supprimerButton = (Button) getActivity().findViewById(R.id.supprimerFilm);
        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Suppression")
                        .setMessage("Voulez-vous supprimer ce film?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendNetworkRequestDelete(filmID);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public class WS extends AsyncTask<String, Integer, FilmDAO> {

        //doInBackground nécessaire pour WS
        @Override
        protected FilmDAO doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            FilmDAO monFilm = cinemaWS.monFilm(filmID);

            return monFilm;

        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(FilmDAO result) {

            super.onPostExecute(result);
            remplirForm(result);

        }

    }

    void remplirForm(FilmDAO f){
        EditText titre = (EditText) getActivity().findViewById(R.id.editionTitre);
        EditText duree = (EditText) getActivity().findViewById(R.id.editionDuree);
        EditText dateSortie = (EditText) getActivity().findViewById(R.id.editionDate);
        EditText budget = (EditText) getActivity().findViewById(R.id.editionBudget);
        EditText recette = (EditText) getActivity().findViewById(R.id.editionRecette);
        titre.setText(f.getTitre());
        duree.setText(f.getDuree()+"");
        dateSortie.setText(f.getDateSortie());
        budget.setText(f.getBudget()+"");
        recette.setText(f.getMontantRecette()+"");
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

    private void sendNetworkRequestEdit(FilmDTO film){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(CinemaAppelWS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CinemaAppelWS cinemaAppel = retrofit.create(CinemaAppelWS.class);

        Call<FilmDTO> call = cinemaAppel.editFilm(filmID,film);
        call.enqueue(new Callback<FilmDTO>() {

            @Override
            public void onResponse(Call<FilmDTO> call, Response<FilmDTO> response) {
                if (response.isSuccessful()) {
                    System.out.println("REPONSE "+response.body());
                    Toast.makeText(getActivity(),"Edition réussie",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.d("FAILURE MSG"," : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Edition échouée",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmDTO> call, Throwable t) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendNetworkRequestDelete(int id){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(CinemaAppelWS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CinemaAppelWS cinemaAppel = retrofit.create(CinemaAppelWS.class);

        Call<FilmDAO> call = cinemaAppel.deleteFilm(id);
        call.enqueue(new Callback<FilmDAO>() {

            @Override
            public void onResponse(Call<FilmDAO> call, Response<FilmDAO> response) {
                if (response.isSuccessful()) {
                    System.out.println("REPONSE "+response.body());
                    Toast.makeText(getActivity(),"Suppression réussie",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.d("FAILURE MSG"," : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Suppression échouée",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmDAO> call, Throwable t) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public FilmDTO editFilm(){

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

        FilmDTO editionFilm = new FilmDTO(titreFilm,dureeFilm,date,budgetFilm,recetteFilm,real,cat);

        return editionFilm;
    }
}
