package com.example.jeudepart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jeudepart.BD.CombinaisonScoreJoueur;
import com.example.jeudepart.BD.DatabaseManager;
import com.example.jeudepart.BD.Score;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<CombinaisonScoreJoueur> {


    public ListViewAdapter(@NonNull Context context, List<CombinaisonScoreJoueur> combinaisonScoreJoueurs) {
        super(context, 0,combinaisonScoreJoueurs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        CombinaisonScoreJoueur score = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listevide, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewListView);
        TextView textViewpays = convertView.findViewById(R.id.label1);
        TextView textViewnom = convertView.findViewById(R.id.label2);
        TextView textViewscore = convertView.findViewById(R.id.label3);
        TextView textViewdate = convertView.findViewById(R.id.label4);

        int resId  = 0;
        switch (score.getJoueur().getPays()) {
            case "canada":
                resId = R.drawable.canada;
                imageView.setImageResource(R.drawable.canada);
                break;
            case "us":
                imageView.setImageResource(R.drawable.united_states_of_america);
                break;
            case "mexique":
                System.out.println("Mexico");
                break;
            case "brésil":
                System.out.println("Brazil");
                break;
            case "france":
                System.out.println("France");
                break;
            case "angleterre":
                System.out.println("England");
                break;
            case "russie":
                System.out.println("Russia");
                break;
            case "chine":
                System.out.println("China");
                break;
            case "inde":
                System.out.println("India");
                break;
            case "espagne":
                System.out.println("Spain");
                break;
            default:
                System.out.println("Unknown country");
        }
        textViewpays.setText(score.getJoueur().getPays());
        textViewnom.setText(score.getJoueur().getPrenom());
        textViewscore.setText(String.valueOf(score.getScore().getScore()));
        textViewdate.setText( score.getScore().getWhen().toString());


        return convertView;
    }


}
