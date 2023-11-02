package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.jeudepart.BD.CombinaisonScoreJoueur;
import com.example.jeudepart.BD.DatabaseManager;
import com.example.jeudepart.BD.Joueur;
import com.example.jeudepart.BD.Score;

import java.util.ArrayList;
import java.util.List;

public class TableauScores extends AppCompatActivity {

    private Button buttonRetourJeu;
    private int idJoueur;

    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableau_scores);
        idJoueur = super.getIntent().getIntExtra("idJoueur",0);
        databaseManager = new DatabaseManager(this);
        buttonRetourJeu = (Button) findViewById(R.id.buttonRetourJeu);
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
            combinaisonScoreJoueurs.add(new CombinaisonScoreJoueur(j,score));
        }
        return combinaisonScoreJoueurs;
    }

    public void afficheListe(){
        List<CombinaisonScoreJoueur> combinaisonScoreJoueurs = buildObjectsFromDataBase();

        ListViewAdapter adapter = new ListViewAdapter(this,combinaisonScoreJoueurs);
        ListView listView = findViewById(R.id.listViewActivite);
        listView.setAdapter(adapter);
    }
}