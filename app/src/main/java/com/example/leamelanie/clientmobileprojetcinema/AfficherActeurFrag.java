package com.example.leamelanie.clientmobileprojetcinema;

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

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;


/**
 * Created by LeaC on 25/10/2017.
 */

public class AfficherActeurFrag extends Fragment {

    private Button ajouterActeur;
    private HashMap<Button,Integer> boutonsEdition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_actors, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boutonsEdition = new HashMap<>();

        new WS().execute();

        //Actions boutons
        ajouterActeur = (Button) getActivity().findViewById(R.id.ajoutActeur);
        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                AjouterActeurFrag ffrag = new AjouterActeurFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.commit();
            }
        };
        ajouterActeur.setOnClickListener(answerListener);
    }

    //Lorsqu'on appuie sur le bouton AWS
    public class WS extends AsyncTask<String, Integer, List<Acteur>> {

        //doInBackground nécessaire pour WS
        @Override
        protected List<Acteur> doInBackground(String... params) {
                 CinemaAppelWS cinemaWS = new RestAdapter.Builder()
                    .setEndpoint(CinemaAppelWS.BASE_URL)
                    .build()
                    .create(CinemaAppelWS.class);

            List<Acteur> mesActeurs = cinemaWS.mesActeurs();
            return mesActeurs;

        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(List<Acteur> result) {

            super.onPostExecute(result);
            afficherActeurs(result);

        }

    }

    public void afficherActeurs(List<Acteur> acteurs) {

        TableLayout acteurs_table = (TableLayout) getActivity().findViewById(R.id.tableau);

        // On affiche l'enreg dans une ligne
        TableRow tableRow = new TableRow(getActivity());
        acteurs_table.addView(tableRow,
                new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // On crée une ligne de x acteurs
        tableRow.setLayoutParams(new TableRow.LayoutParams(acteurs.size()));

        int i;
        for (i=0; i < 5; i++) { //colonnes
            TextView text = new TextView(getActivity());
            switch (i){
                case 0:
                    text.setText("Id");
                    break;
                case 1:
                    text.setText("Nom");
                    break;
                case 2:
                    text.setText("Prénom");
                    break;
                case 3:
                    text.setText("Dates");
                    break;
                case 4:
                    text.setText("");
                    break;
            }
            text.setTextColor(Color.parseColor("#260096"));
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text);
        }

        for (int j = 0; j < acteurs.size(); j++) { //lignes
            tableRow = new TableRow(getActivity());
            acteurs_table.addView(tableRow,
                    new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for(i = 0; i < 5; i++) {
                final Button b = new Button(getActivity());
                TextView text = new TextView(getActivity());
                switch(i){
                    case 0:
                        text.setText(String.valueOf(acteurs.get(j).getId()));
                        break;
                    case 1:
                        text.setText(String.valueOf(acteurs.get(j).getNom()));
                        break;
                    case 2:
                        text.setText(String.valueOf(acteurs.get(j).getPrenom()));
                        break;
                    case 3:
                        String deces = String.valueOf(acteurs.get(j).getDateDeces());
                        String naiss = String.valueOf(acteurs.get(j).getDateNaiss());
                        if (!deces.equals("null")){
                            text.setText(naiss+"\n"+deces);
                        } else {
                            text.setText(naiss);
                        }
                        break;
                    case 4:
                        b.setTextSize(10);
                        b.setText("EDIT");
                        boutonsEdition.put(b,acteurs.get(j).getId());
                        b.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager frman = getFragmentManager();
                                FragmentTransaction ftran = frman.beginTransaction();
                                EditerActeurFrag ffrag = new EditerActeurFrag(boutonsEdition.get(b));
                                ftran.replace(R.id.fragment, ffrag);
                                ftran.commit();
                            }
                        });
                        break;
                }
                if (i==4){
                    tableRow.addView(b);
                } else {
                    tableRow.setWeightSum(5);
                    tableRow.addView(text);
                    text.setGravity(Gravity.CENTER);
                }
                tableRow.setPadding(0,20,0,20);
            }




        }
    }

}
