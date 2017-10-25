package com.example.leamelanie.clientmobileprojetcinema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.service.CinemaAppelWS;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by LeaC on 25/10/2017.
 */

public class HomepageFrag extends Fragment {

    private Button btAppelWS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_homepage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btAppelWS = (Button) getActivity().findViewById(R.id.btAppel);
        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                ActeurFrag ffrag = new ActeurFrag();
                ftran.replace(R.id.fragment, ffrag);
                ftran.commit();
            }
        };
        btAppelWS.setOnClickListener(answerListener);
    }

}