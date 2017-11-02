package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by LeaC on 25/10/2017.
 */

public class AjouterActeurFrag extends Fragment {

    Button requeteAjout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_actors, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requeteAjout = (Button) getActivity().findViewById(R.id.envoyerRequete);
        requeteAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acteur nouvelActeur = creerActeur();
                sendNetworkRequest(nouvelActeur);
            }
        });
    }

    private void sendNetworkRequest(Acteur acteur){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(CinemaAppelWS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CinemaAppelWS cinemaAppel = retrofit.create(CinemaAppelWS.class);

        Call<Acteur> call = cinemaAppel.createActeur(acteur);
        call.enqueue(new Callback<Acteur>() {

            @Override
            public void onResponse(Call<Acteur> call, Response<Acteur> response) {
                if (response.isSuccessful()) {
                    System.out.println("REPONSE "+response.body());
                    Toast.makeText(getActivity(),"Ajout réussi",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.d("FAILURE MSG"," : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Ajout échoué",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Acteur> call, Throwable t) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public Acteur creerActeur(){

        EditText nom = (EditText) getActivity().findViewById(R.id.editionNom);
        final String nomActeur = nom.getText().toString();

        EditText prenom = (EditText) getActivity().findViewById(R.id.editionPrenom);
        final String prenomActeur = prenom.getText().toString();

        EditText dateNaiss = (EditText) getActivity().findViewById(R.id.editionDateNaiss);
        final String dateNaissActeur = dateNaiss.getText().toString();

        EditText dateDeces = (EditText) getActivity().findViewById(R.id.editionDateDeces);
        final String dateDecesActeur = dateDeces.getText().toString();

        Acteur nouvelActeur = new Acteur(nomActeur,prenomActeur,dateNaissActeur,dateDecesActeur);

        return nouvelActeur;
    }

}
