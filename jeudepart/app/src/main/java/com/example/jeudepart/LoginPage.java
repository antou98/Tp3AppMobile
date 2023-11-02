package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeudepart.BD.DatabaseManager;
import com.example.jeudepart.BD.Joueur;

public class LoginPage extends AppCompatActivity {

    private Button loginButton;
    private Button createAccountButton;
    private TextView emailTextField;
    private TextView passwordTextField;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setTitle("Login Tp3-BD-Jeu");

        //db
        databaseManager = new DatabaseManager(this);
        //user pas défault
        //databaseManager.recreateTables();
        //databaseManager.deleteScore(1);
        //databaseManager.insertJoueur(new Joueur("fortier","antoine","ant@live.ca","abc123!","canada"));
        /*Log.i("nbJoueur",  databaseManager.getAllJoueurs().size()+"");

        Log.i("nbJoueur",  databaseManager.getAllJoueurs().size()+"");*/
        Log.i("nbJoueur",  databaseManager.getAllJoueurs().size()+"");
        Log.i("scores",databaseManager.readScores().toString());

        //init buttons
        this.loginButton = (Button) super.findViewById(R.id.buttonConnection);
        this.createAccountButton = (Button) super.findViewById(R.id.buttonCreerCompte);
        this.emailTextField = (TextView) super.findViewById(R.id.courrielEditText);
        this.passwordTextField = (TextView) super.findViewById(R.id.passwordTextEdit);
        //commence le boutton a desactivé
        this.loginButton.setClickable(false);
        this.loginButton.setEnabled(false);
        initButtons();

        this.loginButton.setClickable(true);
        this.loginButton.setEnabled(true);
    }

    public void directTo(){

        Intent intent = new Intent(this, Jeu.class);
        intent.putExtra("idJoueur",1);
        super.startActivity(intent);

    }
    public void initButtons(){
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // valideBdInfo();

                //temp
                directTo();
            }
        });

        this.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        this.emailTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validationTextFieldNotEmpty();
            }
        });

        this.passwordTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validationTextFieldNotEmpty();
            }
        });
    }

    public void valideBdInfo(){
        boolean retVal = false;
        int id = 0;
        for (Joueur j: databaseManager.getAllJoueurs()) {
            if (this.emailTextField.getText().toString().trim().equals(j.getEmail()) && this.passwordTextField.getText().toString().trim().equals(j.getMotdepasse())){
                retVal = true;
                id=j.getIdJoueur();
            }
        }

        if(retVal){
            //intent jeu
            Intent intent = new Intent(this, Jeu.class);
            intent.putExtra("idJoueur",id);
            super.startActivity(intent);

        }
        else {
            Toast.makeText(this,"Erreur : Combinaison mot de passe email inexistante", Toast.LENGTH_SHORT+2).show();
        }
    }

    public void createUser(){
        Intent intent = new Intent(this,CreateAccountPage.class);
        super.startActivity(intent);
    }

    public boolean validationTextFieldNotEmpty(){
        boolean retVal = true;
        if(this.emailTextField.getText().toString().trim().isEmpty() || this.emailTextField.getText().toString().trim().length()<5){
            this.emailTextField.setError("Adress Courriel doit contenir plus de 5 caratères");
            retVal = false;
        }
        if(this.passwordTextField.getText().toString().trim().isEmpty() || this.passwordTextField.getText().toString().trim().length()<5){
            this.passwordTextField.setError("Mot de passe doit contenir plus de 5 caratères");
            retVal = false;
        }

        if(retVal){
            this.loginButton.setClickable(true);
            this.loginButton.setEnabled(true);
        }

        return retVal;
    }
}