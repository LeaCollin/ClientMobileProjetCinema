package com.example.leamelanie.clientmobileprojetcinema.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leamelanie.clientmobileprojetcinema.R;
import com.example.leamelanie.clientmobileprojetcinema.metier.Acteur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeaC on 19/10/2017.
 */

public class ObjetAdapter extends ArrayAdapter<Acteur> {

    private List<Acteur> mesActeurs = new ArrayList<Acteur>();
    private LayoutInflater layoutInflater;
    private Context context;

    public ObjetAdapter(Context context, int textViewResourceId, List<Acteur> mesActeurs) {
        super(context, textViewResourceId, mesActeurs);
        this.mesActeurs = mesActeurs;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return this.mesActeurs.size();
    }
    @Override
    public Acteur getItem(int position) {
        return this.mesActeurs.get(position);
    }
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current list item
        final Acteur item = mesActeurs.get(position);
        // Get the layout for the list item
        final LinearLayout itemLayout = (LinearLayout)
                LayoutInflater.from(context).inflate(R.layout.list_actors, parent, false);
        // Set the text label as defined in our list item

        TextView txtno = (TextView) itemLayout.findViewById(R.id.txtNoActeur);
        txtno.setText(String.valueOf(item.getNoActeur()));

        TextView txtnom = (TextView) itemLayout.findViewById(R.id.txtNomActeur);
        txtnom.setText(item.getNomActeur());

        TextView txtprenom = (TextView) itemLayout.findViewById(R.id.txtPrenomActeur);
        txtprenom.setText(String.valueOf(item.getPrenomActeur()));

        TextView txtdatenais = (TextView) itemLayout.findViewById(R.id.txtDateNais);
        txtdatenais.setText(String.valueOf(item.getDateNaissance()));

        TextView txtdatedec = (TextView) itemLayout.findViewById(R.id.txtDateDeces);
        txtdatedec.setText(String.valueOf(item.getDateDeces()));

        return itemLayout;

    }
}
