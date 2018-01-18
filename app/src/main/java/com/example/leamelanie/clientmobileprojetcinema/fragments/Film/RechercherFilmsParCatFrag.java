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
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class RechercherFilmsParCatFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_movies, container, false);
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
                if (f.getCategorie().getLibelle().equals(HomepageFrag.CATEGORIE)){
                    ajoute(f.getTitre());
                }
            }
        }

    }

    public void ajoute(String titre) {
        LinearLayout liste = (LinearLayout) getActivity().findViewById(R.id.liste_films);
        TextView text = new TextView(getActivity());
        text.setText(titre);
        text.setPadding(20,0,0,15);
        text.setTextSize(20);
        liste.addView(text);
        text.setGravity(Gravity.CENTER);
    }
}
