package com.example.leamelanie.clientmobileprojetcinema.fragments.Film;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.fragments.HomepageFrag;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class RechercherFilmFrag extends Fragment {

    List<Acteur> mesActeursFilm = new ArrayList<>();
    FilmDTO monFilm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_movie, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new WS().execute();
        new WS_Acteurs().execute();
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
                    monFilm = f;
                    break;
                }
            }
        }
    }

    public class WS_Acteurs extends AsyncTask<String, Integer, List<Acteur>> {

        @Override
        protected List<Acteur> doInBackground(String... params) {
            CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<Acteur> mesActeurs = cinemaWS.mesActeursPourUnFilm(monFilm.getId());
            return mesActeurs;

        }

        @Override
        protected void onPostExecute(List<Acteur> result) {

            super.onPostExecute(result);
            mesActeursFilm.clear();
            for (Acteur a: result) {
                mesActeursFilm.add(a);
            }
            description(monFilm);

        }

    }

    public void description(FilmDTO film) {
        TextView titre = (TextView) getActivity().findViewById(R.id.titreFilm);
        titre.setText(String.valueOf(film.getId()+" - "+film.getTitre()));

        TextView duree = (TextView) getActivity().findViewById(R.id.dureeFilm);
        duree.setText(String.valueOf("Durée : "+film.getDuree()+" minutes"));

        TextView date = (TextView) getActivity().findViewById(R.id.dateSortieFilm);
        date.setText(String.valueOf("Date sortie : "+film.getDateSortie()));

        TextView budget = (TextView) getActivity().findViewById(R.id.budgetFilm);
        budget.setText(String.valueOf("Budget : "+film.getBudget()+" dollars"));

        TextView recette = (TextView) getActivity().findViewById(R.id.recetteFilm);
        recette.setText(String.valueOf("Recette : "+film.getMontantRecette()+" dollars"));

        TextView real = (TextView) getActivity().findViewById(R.id.realisateurFilm);
        real.setText(String.valueOf("Réalisé par "+film.getRealisateur().getPrenom()+" "+film.getRealisateur().getNom()));

        TextView cat = (TextView) getActivity().findViewById(R.id.categorieFilm);
        cat.setText(String.valueOf("Type : "+film.getCategorie().getLibelle()));

        TableLayout acteurs = (TableLayout) getActivity().findViewById(R.id.liste_acteurs);

        for (Acteur a: mesActeursFilm) {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));

            TextView text = new TextView(getActivity());
            text.setText(a.getPrenom()+" "+a.getNom());
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
            text.setTextSize(20);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(text);
            acteurs.addView(tableRow);
        }
    }
}