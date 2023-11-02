package com.example.jeudepart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jeudepart.BD.DatabaseManager;
import com.example.jeudepart.BD.Joueur;
import com.example.jeudepart.BD.Score;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
public class Jeu extends AppCompatActivity {
    //
    private EditText txtNumber;
    private Button btnCompare;
    private TextView lblResult;
    private ProgressBar pgbScore;
    private TextView lblHistory;

    private Button buttonScores;
    private int searchedValue;
    private int score;

    private int lastScore;

    private Joueur joueur;

    private boolean isFirstTurn;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Jeu Tp3-BD-Jeu");
        txtNumber = (EditText) findViewById( R.id.txtNumber );
        btnCompare = (Button) findViewById( R.id.btnCompare );
        lblResult = (TextView) findViewById( R.id.lblResult );
        pgbScore = (ProgressBar) findViewById( R.id.pgbScore );
        lblHistory = (TextView) findViewById( R.id.lblHistory );
        buttonScores = (Button) findViewById( R.id.buttonScores );
        isFirstTurn = true;
        //bd
        databaseManager = new DatabaseManager(this);

        btnCompare.setOnClickListener( btnCompareListener );

        init();

        int id = super.getIntent().getIntExtra("idJoueur",0);

        if(id!=0){
            if (databaseManager.getJoueur(id)!=null){
                joueur =   databaseManager.getJoueur(id);
                Log.i("Joueur selectionné",joueur.toString());
            }
            else {
                Log.i("Erreur","Pas un id joueur");
            }
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        double price = 1_000_000.01;
        Log.i( "DEBUG", formatter.format( price ) );

        DateFormat dataFormatter = DateFormat.getDateTimeInstance();
        Log.i( "DEBUG", dataFormatter.format( new Date() ) );

        buttonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeScores();
            }
        });
    }

    private void init() {
        score = 0;
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i( "DEBUG", "Searched value : " + searchedValue );

        txtNumber.setText( "" );
        pgbScore.setProgress( score );
        lblResult.setText( "" );
        lblHistory.setText( "" );

        txtNumber.requestFocus();
    }

    private void congratulations() {
        if (isFirstTurn){
            lastScore = score;
        }else{
            //disable boutton
            buttonScores.setClickable(false);
            buttonScores.setEnabled(false);
        }
        isFirstTurn = false;
        lblResult.setText( R.string.strCongratulations );

        AlertDialog retryAlert = new AlertDialog.Builder( this ).create();
        retryAlert.setTitle( R.string.app_name );
        retryAlert.setMessage( getString(R.string.strMessage, score ) );

        retryAlert.setButton( AlertDialog.BUTTON_POSITIVE, getString(R.string.strYes), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lastScore = verifSmallestScore();
                init();
            }
        });

        retryAlert.setButton( AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNo), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lastScore = verifSmallestScore();
                saveScore();
                finish();
            }
        });

        retryAlert.show();
    }

    private void saveScore(){
        databaseManager.replaceScoreIfSmaller(new Score(joueur,lastScore, new Date()),joueur);
    }

    public int verifSmallestScore(){
        if (lastScore<=score){
            return lastScore;
        }else {
            return  score;
        }
    }

    private View.OnClickListener btnCompareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i( "DEBUG", "Button clicked" );

            String strNumber = txtNumber.getText().toString();
            if ( strNumber.equals( "" ) ) return;

            int enteredValue = Integer.parseInt( strNumber );
            lblHistory.append( strNumber + "\r\n" );
            pgbScore.incrementProgressBy( 1 );
            score++;

            if ( enteredValue == searchedValue ) {
                congratulations();
            } else if ( enteredValue < searchedValue ) {
                lblResult.setText( R.string.strGreater );
            } else {
                lblResult.setText( R.string.strLower );
            }

            txtNumber.setText( "" );
            txtNumber.requestFocus();

        }
    };

    public void seeScores(){
        if(isFirstTurn){
            Intent intent = new Intent(this,TableauScores.class);
            intent.putExtra("idJoueur",joueur.getIdJoueur());
            startActivity(intent);
        }
    }

}