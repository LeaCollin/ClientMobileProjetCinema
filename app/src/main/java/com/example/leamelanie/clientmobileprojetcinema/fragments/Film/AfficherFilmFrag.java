package com.example.leamelanie.clientmobileprojetcinema.fragments.Film;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDTO;
import com.example.leamelanie.clientmobileprojetcinema.metier.FilmDAO;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaService;

import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class AfficherFilmFrag extends Fragment {

    private HashMap<Button,Integer> boutonsEdition;
    private Button ajouterFilm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_movies, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boutonsEdition = new HashMap<>();

        new WS().execute();

        //Actions boutons
        ajouterFilm = (Button) getActivity().findViewById(R.id.ajoutFilm);
        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AjouterFilmFrag ffrag = new AjouterFilmFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.addToBackStack(null);
                ftran.commit();
            }
        };
        ajouterFilm.setOnClickListener(answerListener);
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
            afficherFilms(result);

        }

    }

    public void afficherFilms(List<FilmDTO> films) {
        TableLayout films_table = (TableLayout) getActivity().findViewById(R.id.tableau);

        // On affiche l'enreg dans une ligne
        TableRow tableRow = new TableRow(getActivity());
        films_table.addView(tableRow,
                new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // On crée une ligne de x films
        tableRow.setLayoutParams(new TableRow.LayoutParams(films.size()));

        // On va commencer par renseigner une ligne de titre par film
        int i;
        for (i=0; i < 9; i++) {
            TextView text = new TextView(getActivity());
            switch (i){
                case 0:
                    text.setText("Id");
                    break;
                case 1:
                    text.setText("Titre du film");
                    break;
                case 2:
                    text.setText("Durée (en min)");
                    break;
                case 3:
                    text.setText("Date de sortie");
                    break;
                case 4:
                    text.setText("Budget");
                    break;
                case 5:
                    text.setText("Montant des recettes");
                    break;
                case 6:
                    text.setText("Realisateur");
                    break;
                case 7:
                    text.setText("Catégorie");
                    break;
                case 8:
                    text.setText("");
                    break;
            }
            text.setTextColor(Color.parseColor("#260096"));
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER);
            text.setPadding(20,0,20,0);
            tableRow.addView(text);
        }

        for (int j = 0; j < films.size(); j++) { //lignes
            tableRow = new TableRow(getActivity());
            final Button b = new Button(getActivity());
            films_table.addView(tableRow,
                    new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for(i = 0; i < 9; i++) {
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
                    case 6:
                        text.setText(String.valueOf(films.get(j).getRealisateur().getPrenom())+" "+String.valueOf(films.get(j).getRealisateur().getNom()));
                        break;
                    case 7:
                        text.setText(String.valueOf(films.get(j).getCategorie().getLibelle()));
                        break;
                    case 8:
                        b.setTextSize(10);
                        b.setText("Edit");
                        boutonsEdition.put(b,films.get(j).getId());
                        b.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager frman = getFragmentManager();
                                FragmentTransaction ftran = frman.beginTransaction();
                                EditerFilmFrag ffrag = new EditerFilmFrag(boutonsEdition.get(b));
                                ftran.replace(R.id.fragment, ffrag);
                                ftran.addToBackStack(null);
                                ftran.commit();
                            }
                        });
                        break;

                }
                if (i==8){
                    tableRow.addView(b);
                } else {
                    text.setGravity(Gravity.CENTER);
                    tableRow.addView(text);
                }
            }
        }
    }

}
