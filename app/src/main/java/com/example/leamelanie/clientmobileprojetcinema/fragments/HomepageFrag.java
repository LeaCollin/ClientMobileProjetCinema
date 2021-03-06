package com.example.leamelanie.clientmobileprojetcinema.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.Spinner;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.fragments.Acteur.AfficherActeurFrag;
import com.example.leamelanie.clientmobileprojetcinema.fragments.Film.AfficherFilmFrag;
import com.example.leamelanie.clientmobileprojetcinema.fragments.Film.RechercherFilmFrag;
import com.example.leamelanie.clientmobileprojetcinema.fragments.Film.RechercherFilmsParCatFrag;
import com.example.leamelanie.clientmobileprojetcinema.fragments.Film.RechercherFilmsParRealFrag;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.Categorie;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.metier.Realisateur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by LeaC on 25/10/2017.
 */

public class HomepageFrag extends Fragment {

    Spinner listeFilms;
    Spinner listeRealisateurs;
    Spinner listeCategories;
    List<String> titresFilms = new ArrayList<>();
    List<String> nomsRealisateur = new ArrayList<>();
    List<String> libellesCategorie = new ArrayList<>();
    public static String FILM = "null";
    public static String REALISATEUR = "null";
    public static String CATEGORIE = "null";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_homepage, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listeFilms = getActivity().findViewById(R.id.films);
        listeRealisateurs = getActivity().findViewById(R.id.realisateurs);
        listeCategories = getActivity().findViewById(R.id.categories);

        new WS_Categories().execute();
        new WS_Realisateurs().execute();
        new WS_Films().execute();

        creationBoutons();

    }

    public void creationBoutons() {
        Button btAppelActeurs = (Button) getActivity().findViewById(R.id.btAppel);
        Button btAppelFilms = (Button) getActivity().findViewById(R.id.btAppelFilm);
        Button btRechercheFilm = (Button) getActivity().findViewById(R.id.rechercher);
        Button btRechercheFilmsParRealisateur = (Button) getActivity().findViewById(R.id.rechercherFilmsParReal);
        Button btRechercheFilmsParCategorie = (Button) getActivity().findViewById(R.id.rechercherFilmsParTheme);

        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AfficherActeurFrag ffrag = new AfficherActeurFrag();
                ftran.replace(R.id.fragment, ffrag);
                //Retour en arrière
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };

        Button.OnClickListener answerListenerFilm = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AfficherFilmFrag ffrag = new AfficherFilmFrag();
                ftran.replace(R.id.fragment, ffrag);
                //Retour en arrière
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };

        Button.OnClickListener answerListenerRechercheFilm = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILM = String.valueOf(listeFilms.getSelectedItem());
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                RechercherFilmFrag ffrag = new RechercherFilmFrag();
                ftran.replace(R.id.fragment, ffrag);
                //Retour en arrière
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };


        Button.OnClickListener answerListenerRechercheFilmsParRealisateur = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                REALISATEUR = String.valueOf(listeRealisateurs.getSelectedItem());
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                RechercherFilmsParRealFrag ffrag = new RechercherFilmsParRealFrag();
                ftran.replace(R.id.fragment, ffrag);
                //Retour en arrière
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };


        Button.OnClickListener answerListenerRechercheFilmsParCategorie= new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                CATEGORIE = String.valueOf(listeCategories.getSelectedItem());
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                RechercherFilmsParCatFrag ffrag = new RechercherFilmsParCatFrag();
                ftran.replace(R.id.fragment, ffrag);
                //Retour en arrière
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };

        btAppelActeurs.setOnClickListener(answerListener);
        btAppelFilms.setOnClickListener(answerListenerFilm);
        btRechercheFilm.setOnClickListener(answerListenerRechercheFilm);
        btRechercheFilmsParRealisateur.setOnClickListener(answerListenerRechercheFilmsParRealisateur);
        btRechercheFilmsParCategorie.setOnClickListener(answerListenerRechercheFilmsParCategorie);
    }

    public class WS_Films extends AsyncTask<String, Integer, List<FilmDAO>> {

        @Override
        protected List<FilmDAO> doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<FilmDAO> mesFilms = cinemaWS.mesFilms();
            return mesFilms;

        }

        @Override
        protected void onPostExecute(List<FilmDAO> result) {

            super.onPostExecute(result);
            CinemaService.films.clear();
            titresFilms.clear();
            for (FilmDAO f: result) {
                 titresFilms.add(f.getTitre());
                 CinemaService.films.add(f);
            }
            remplirSpinner(titresFilms,listeFilms);


        }

    }

    public class WS_Realisateurs extends AsyncTask<String, Integer, List<Realisateur>> {

        @Override
        protected List<Realisateur> doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<Realisateur> mesRealisateurs = cinemaWS.mesRealisateurs();
            return mesRealisateurs;

        }

        @Override
        protected void onPostExecute(List<Realisateur> result) {

            super.onPostExecute(result);
            CinemaService.realisateurs.clear();
            nomsRealisateur.clear();
            for (Realisateur r: result) {
                CinemaService.realisateurs.add(r);
                nomsRealisateur.add(r.getNom());
            }
            remplirSpinner(nomsRealisateur,listeRealisateurs);

        }

    }

    public class WS_Categories extends AsyncTask<String, Integer, List<Categorie>> {

        @Override
        protected List<Categorie> doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<Categorie> mesCategories = cinemaWS.mesCategories();
            return mesCategories;

        }

        @Override
        protected void onPostExecute(List<Categorie> result) {

            super.onPostExecute(result);
            CinemaService.categories.clear();
            libellesCategorie.clear();
            for (Categorie c: result) {
                CinemaService.categories.add(c);
                libellesCategorie.add(c.getLibelle());
            }
            remplirSpinner(libellesCategorie,listeCategories);

        }

    }

    public void remplirSpinner(List<String> items,Spinner spinner) {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}