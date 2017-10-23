package com.example.leamelanie.clientmobileprojetcinema;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leamelanie.clientmobileprojetcinema.meserreurs.MonException;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;
import com.example.leamelanie.clientmobileprojetcinema.service.AppelWebService;
import com.example.leamelanie.clientmobileprojetcinema.service.ObjetAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String BASE_URL = "http://localhost:8080";
    private Button btAppelWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAppelWS = (Button) findViewById(R.id.btAppel);
        btAppelWS.setOnClickListener(this);
    }

    public void onClick(View v) {
        String information = "";
        if (v == btAppelWS) {
            String uneUrl = BASE_URL + "/Acteur/liste";
            // On lance le thread en passant l'URL en paramètre
            new WS().execute("get", uneUrl);
        }

    }

    //Lorsqu'on appuie sur le bouton AWS
    public class WS extends AsyncTask<String, Integer, List<Acteur>> {

        private ListView listViewData = (ListView) findViewById(R.id.listViewActors);
        private String data = "";
        private TextView uiUpdate = (TextView) findViewById(R.id.welcome);
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        private String Error = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialog.setMessage("Please wait..");
            Dialog.show();

            try {
                // Set Request parameter
                data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + " Affichage des acteurs";

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //doInBackground nécessaire pour WS
        @Override
        protected List<Acteur> doInBackground(String... params) {
            List<Acteur> mesActeurs = null;
            AppelWebService unws = new AppelWebService();
            // on récupère l'url passée en appel au Thread
            String uneurl = params[1];
            String action = params[0];
            if (action.equals("get")) {

                try {
                    mesActeurs = unws.getListeActeurs(uneurl);

                } catch (MonException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Erreur")
                            .setMessage(e.getMessage())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Bouton cliqué, on affiche
                                    Toast.makeText(MainActivity.this, "Il faut quitter l'application ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
            return mesActeurs;
        }

        //result = resultat de doInBackground, fonction lancé automatiquement à la fin du process
        @Override
        protected void onPostExecute(List<Acteur> result) {


            final List<Acteur> mesActeurs = result;
            // on efface la boîte de dialogue
            Dialog.dismiss();

            if (Error != null) {
                uiUpdate.setText("Output : " + Error);
            } else {

                // On visualise la réponse sur l'écran (activity)
                uiUpdate.setText("Voici le résultat");
                if (result!=null) {

                    listViewData.clearAnimation();
                    LayoutInflater inflater = getLayoutInflater();
                    ViewGroup header = (ViewGroup)inflater.inflate(R.layout.list_actors, listViewData, false);
                    if (listViewData.getHeaderViewsCount() ==0)
                        listViewData.addHeaderView(header, null, false);
                    final ObjetAdapter adapter = new ObjetAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mesActeurs);
                    listViewData.setAdapter(adapter);


                }
            }

        }


    }
}
