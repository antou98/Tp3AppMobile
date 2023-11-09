package com.example.jeudepart.partie1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jeudepart.partie1.BD.CombinaisonScoreJoueur;
import com.example.jeudepart.R;
import com.example.jeudepart.partie1.BD.SpinnerItem;

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


        switch (score.getJoueur().getPays()) {
            case "Canada":
                imageView.setImageResource(R.drawable.canada);
                break;
            case "US":
                imageView.setImageResource(R.drawable.united_states_of_america);
                break;
            case "Chine":
                imageView.setImageResource(R.drawable.china);
                break;
            case "Brézil":
                imageView.setImageResource(R.drawable.brazil);
                break;
            case "Japon":
                imageView.setImageResource(R.drawable.japan);
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
