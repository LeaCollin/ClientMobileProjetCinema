package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.metier.Film;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by LeaC on 25/10/2017.
 */

public class HomepageFrag extends Fragment {

    private Button btAppelActeurs;
    private Button btAppelFilms;
    private Button btRechercheFilm;
    Spinner listeFilms;
    List<String> titresFilms = new ArrayList<>();
    public static String FILM = "null";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_homepage, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Remplir la liste des films
        listeFilms = getActivity().findViewById(R.id.films);
        //new WS().execute();

        //Gérer les actions des boutons
        btAppelActeurs = (Button) getActivity().findViewById(R.id.btAppel);
        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AfficherActeurFrag ffrag = new AfficherActeurFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };
        btAppelActeurs.setOnClickListener(answerListener);

        btAppelFilms = (Button) getActivity().findViewById(R.id.btAppelFilm);
        Button.OnClickListener answerListenerFilm = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AfficherFilmFrag ffrag = new AfficherFilmFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };
        btAppelFilms.setOnClickListener(answerListenerFilm);

        btRechercheFilm = (Button) getActivity().findViewById(R.id.rechercher);
        Button.OnClickListener answerListenerRechercheFilm = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILM = String.valueOf(listeFilms.getSelectedItem());
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                RechercherFilmFrag ffrag = new RechercherFilmFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.commit();
            }
        };
        btRechercheFilm.setOnClickListener(answerListenerRechercheFilm);
    }

    //Lorsqu'on appuie sur le bouton OK rechercher un film
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
            for (Film f: result) {
                titresFilms.add(f.getTitre());
            }
            remplirFilms(titresFilms);
        }

    }

    public void remplirFilms(List<String> titres) {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                titres
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeFilms.setAdapter(adapter);

    }
}