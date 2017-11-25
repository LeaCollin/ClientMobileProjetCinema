package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.EventLogTags;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.metier.Film;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class RechercherFilmFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_movie, container, false);
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
            for (Film f : result) {
                if (f.getTitre().equals(HomepageFrag.FILM)){
                    description(f);
                    break;
                }
            }
        }

        public void description(Film film) {
            LinearLayout description = (LinearLayout) getActivity().findViewById(R.id.descriptionFilm);

            for (int i = 0; i < 8; i++) {
                TextView text = new TextView(getActivity());
                switch (i) {
                    case 0:
                        text.setText(String.valueOf(film.getId()));
                        break;
                    case 1:
                        text.setText(String.valueOf(film.getTitre()));
                        break;
                    case 2:
                        text.setText(String.valueOf(film.getDuree()));
                        break;
                    case 3:
                        text.setText(String.valueOf(film.getDateSortie()));
                        break;
                    case 4:
                        text.setText(String.valueOf(film.getBudget()));
                        break;
                    case 5:
                        text.setText(String.valueOf(film.getMontantRecette()));
                        break;
                    case 6:
                        text.setText(String.valueOf(film.getRealisateur().getId()));
                        break;
                    case 7:
                        text.setText(String.valueOf(film.getCodeCategorie()));
                        break;
                }
                description.addView(text);
                text.setGravity(Gravity.CENTER);
            }
        }
    }
}