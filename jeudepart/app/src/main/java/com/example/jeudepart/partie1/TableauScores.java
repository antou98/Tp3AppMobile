package com.example.jeudepart.partie1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeudepart.partie1.BD.CombinaisonScoreJoueur;
import com.example.jeudepart.partie1.BD.DatabaseManager;
import com.example.jeudepart.partie1.BD.Joueur;
import com.example.jeudepart.partie1.BD.Score;
import com.example.jeudepart.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableauScores extends AppCompatActivity {

    private Button buttonRetourJeu;

    private TextView textView;
    private int idJoueur;

    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableau_scores);
        idJoueur = super.getIntent().getIntExtra("idJoueur",0);
        databaseManager = new DatabaseManager(this);
        buttonRetourJeu = (Button) findViewById(R.id.buttonRetourJeu);
        textView = (TextView) findViewById(R.id.scoresTextMessage);
        spannable();
        buttonRetourJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToGame();
            }
        });
        afficheListe();

    }

    public void backToGame(){
        Intent intent = new Intent();
        intent.putExtra("idJoueur",idJoueur);
        super.startActivity(intent);
    }

    public List<CombinaisonScoreJoueur> buildObjectsFromDataBase(){
        List<CombinaisonScoreJoueur> combinaisonScoreJoueurs = new ArrayList<>();
        List<Joueur> joueurs = databaseManager.getAllJoueurs();
        for (Joueur j: joueurs) {
            Score score = databaseManager.getScoreFromJoueur(j);
            if (score!=null){
                combinaisonScoreJoueurs.add(new CombinaisonScoreJoueur(j,score));
            }
        }
        Collections.sort(combinaisonScoreJoueurs);
        return combinaisonScoreJoueurs;
    }

    public void afficheListe(){
        List<CombinaisonScoreJoueur> combinaisonScoreJoueurs = buildObjectsFromDataBase();
        ListViewAdapter adapter = new ListViewAdapter(this,combinaisonScoreJoueurs);
        ListView listView = findViewById(R.id.listViewActivite);
        listView.setAdapter(adapter);
    }

    public void spannable(){


        //Texte d'origine
        String text = "Scores";

        //Créer une SpannableString
        SpannableString spannableString = new SpannableString(text);

        //Rendre le texte deux fois plus grand
        spannableString.setSpan(new RelativeSizeSpan(2f), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Mettre le texte en gras
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Souligner le texte
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Mettre le texte en italique
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Texte surligné (jaune)
        spannableString.setSpan(new BackgroundColorSpan(0xFFFFFF00), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Modifier la couleur du texte (par exemple, rouge)
        spannableString.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Appliquer la SpannableString au TextView
        textView.setText(spannableString);
    }
}