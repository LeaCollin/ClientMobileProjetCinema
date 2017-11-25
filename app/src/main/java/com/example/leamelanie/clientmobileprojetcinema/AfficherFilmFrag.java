package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.Film;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class AfficherFilmFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_movies, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new WS().execute();
    }

    //Lorsqu'on appuie sur le bouton AWS
    public class WS extends AsyncTask<String, Integer, List<Film>> {

        //doInBackground nécessaire pour WS
        @Override
        protected List<Film> doInBackground(String... params) {
                 CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<Film> mesFilms = cinemaWS.mesFilms();
            return mesFilms;

        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(List<Film> result) {

            super.onPostExecute(result);
            afficherFilms(result);

        }

    }

    public void afficherFilms(List<Film> films) {
        TableLayout films_table = (TableLayout) getActivity().findViewById(R.id.tableau);

        // On affiche l'enreg dans une ligne
        TableRow tableRow = new TableRow(getActivity());
        films_table.addView(tableRow,
                new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // On crée une ligne de x films
        tableRow.setLayoutParams(new TableRow.LayoutParams(films.size()));

        // On va commencer par renseigner une ligne de titre par film
        int i;
        for (i=0; i < 8; i++) {
            TextView text = new TextView(getActivity());
            switch (i){
                case 0:
                    text.setText("Id");
                    break;
                case 1:
                    text.setText("Titre");
                    break;
                case 2:
                    text.setText("Durée");
                    break;
                case 3:
                    text.setText("DateSortie");
                    break;
                case 4:
                    text.setText("Budget");
                    break;
                case 5:
                    text.setText("MontantRecette");
                    break;
                case 6:
                    text.setText("Realisateur");
                    break;
                case 7:
                    text.setText("Catégorie");
                    break;
            }
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text);
        }

        for (int j = 0; j < films.size(); j++) { //lignes
            tableRow = new TableRow(getActivity());
            films_table.addView(tableRow,
                    new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for(i = 0; i < 6; i++) {
                TextView text = new TextView(getActivity());
                switch(i){
                    case 0:
                        text.setText(String.valueOf(films.get(j).getId()));
                        break;
                    case 1:
                        text.setText(String.valueOf(films.get(j).getTitre()));
                        break;
                    case 2:
                        text.setText(String.valueOf(films.get(j).getDuree()));
                        break;
                    case 3:
                        text.setText(String.valueOf(films.get(j).getDateSortie()));
                        break;
                    case 4:
                        text.setText(String.valueOf(films.get(j).getBudget()));
                        break;
                    case 5:
                        text.setText(String.valueOf(films.get(j).getMontantRecette()));
                        break;

                }
                tableRow.addView(text);
                text.setGravity(Gravity.CENTER);
            }
        }
    }

}
