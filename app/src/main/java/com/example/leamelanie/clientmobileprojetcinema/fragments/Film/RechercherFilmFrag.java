package com.example.leamelanie.clientmobileprojetcinema.fragments.Film;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.fragments.HomepageFrag;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

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
    public class WS extends AsyncTask<String, Integer, List<FilmDTO>> {

        //doInBackground nécessaire pour WS
        @Override
        protected List<FilmDTO> doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<FilmDAO> mesFilms = cinemaWS.mesFilms();
            return CinemaService.filmDAOtoDTO(mesFilms);
        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(List<FilmDTO> result) {

            super.onPostExecute(result);
            for (FilmDTO f : result) {
                if (f.getTitre().equals(HomepageFrag.FILM)){
                    description(f);
                    break;
                }
            }
        }

        public void description(FilmDTO film) {
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
                        text.setText(String.valueOf(film.getRealisateur().getPrenom())+" "+String.valueOf(film.getRealisateur().getNom()));
                        break;
                    case 7:
                        text.setText(String.valueOf(film.getCategorie().getLibelle()));
                        break;
                }
                description.addView(text);
                text.setGravity(Gravity.CENTER);
            }
        }
    }
}