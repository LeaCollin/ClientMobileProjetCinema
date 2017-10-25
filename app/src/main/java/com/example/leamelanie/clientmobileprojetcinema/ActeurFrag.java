package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by LeaC on 25/10/2017.
 */

public class ActeurFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_actors, container, false);
    }

    public void afficherActeurs(List<Acteur> acteurs) {
        TableLayout acteurs_table = (TableLayout) getActivity().findViewById(R.id.acteurs_table);
        for (Acteur act:acteurs) {
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TextView id = new TextView(getActivity());
            TextView nomActeur = new TextView(getActivity());
            TextView prenomActeur = new TextView(getActivity());
            TextView dateNaiss = new TextView(getActivity());
            TextView dateDeces = new TextView(getActivity());
            id.setText(act.getId());
            nomActeur.setText(act.getNom());
            prenomActeur.setText(act.getPrenom());
            dateDeces.setText(act.getDateDeces());
            dateNaiss.setText(act.getDateNaiss());
            row.addView(id);
            row.addView(nomActeur);
            row.addView(prenomActeur);
            row.addView(dateDeces);
            row.addView(dateNaiss);
            acteurs_table.addView(row);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new WS().execute();
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

            //String user = params[0];
            List<Acteur> mesActeurs = cinemaWS.mesActeurs();

            return mesActeurs;

        }

        public void onClick(View v) {
            String information = "";

        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(List<Acteur> result) {

            super.onPostExecute(result);
            afficherActeurs(result);

        }

    }
}
