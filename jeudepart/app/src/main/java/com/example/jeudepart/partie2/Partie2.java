package com.example.jeudepart.partie2;

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

import com.example.jeudepart.R;
import com.example.jeudepart.partie1.BD.DatabaseManager;
import com.example.jeudepart.partie1.BD.Joueur;
import com.example.jeudepart.partie1.CreateAccountPage;
import com.example.jeudepart.partie1.Jeu;
import com.example.jeudepart.partie1.LoginPage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;

public class Partie2 extends AppCompatActivity {

    private RequestQueue requestQueue;

    //doit être l'adresse de l'ordi sur le réseau
    private final String ipaddress = "192.168.2.48";
    private final String Url = "http://"+ipaddress+":5000";
    protected Button loginButton;
    protected Button createAccountButton;
    protected TextView emailTextField;
    protected TextView passwordTextField;
    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie2);
        setTitle("Partie 2");

        this.loginButton = (Button) super.findViewById(R.id.buttonConnection2);
        this.createAccountButton = (Button) super.findViewById(R.id.buttonCreerCompte2);
        this.emailTextField = (TextView) super.findViewById(R.id.courrielEditText2);
        this.passwordTextField = (TextView) super.findViewById(R.id.passwordTextEdit2);
        //commence le boutton a desactivé
        this.loginButton.setClickable(false);
        this.loginButton.setEnabled(false);
        initButtons();

        requestQueue = Volley.newRequestQueue(this);
    }
    public void initButtons(){
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJoueur(emailTextField.getText().toString().trim(),passwordTextField.getText().toString().trim());
                //test();
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

    public void createUser(){
        Intent intent = new Intent(this, CreateAccount2.class);
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

    private void getJoueur(String email, String password) {
        String url = Url + "/api/joueurs?email=" + email + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle  GET response
                        Toast.makeText(Partie2.this, "GET Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Msg api ",response.toString());
                        toJeux();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(Partie2.this, "GET Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Error api ",error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void test() {
        String url = Url + "/api";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle  GET response
                        Toast.makeText(Partie2.this, "GET Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Error api ",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(Partie2.this, "GET Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Error api ",error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void toJeux(){
        Intent intent = new Intent(this, Jeu.class);
        intent.putExtra("isLocalDataBase",false);
        super.startActivity(intent);
    }
}