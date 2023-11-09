package com.example.jeudepart.partie1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jeudepart.partie1.BD.DatabaseManager;
import com.example.jeudepart.partie1.BD.Joueur;
import com.example.jeudepart.partie1.BD.SpinnerItem;
import com.example.jeudepart.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountPage extends AppCompatActivity{

    private TextView prenom;

    private TextView nom;

    private TextView email;

    private TextView passWord;

    private Spinner spinnerPays;

    private Button newCompteButton;

    private DatabaseManager databaseManager;

    private  String pays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        prenom = (TextView) findViewById(R.id.prenomTextView);
        nom = (TextView) findViewById(R.id.nomTextView);
        email = (TextView) findViewById(R.id.emailTextView);
        passWord = (TextView) findViewById(R.id.passwordTextView);
        spinnerPays = (Spinner) findViewById(R.id.spinner);
        newCompteButton = (Button) findViewById(R.id.buttonCreateNewCompte);

        Spinner spinnerPays = findViewById(R.id.spinner);
        ArrayList<SpinnerItem> itemList = new ArrayList<>();
        itemList.add(new SpinnerItem("Canada", R.drawable.canada));
        itemList.add(new SpinnerItem("Chine", R.drawable.china));
        itemList.add(new SpinnerItem("Japon", R.drawable.japan));
        itemList.add(new SpinnerItem("US", R.drawable.united_states_of_america));
        itemList.add(new SpinnerItem("Brézil", R.drawable.brazil));
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, itemList);
        spinnerPays.setAdapter(spinnerAdapter);
        spinnerPays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerItem selectedItem = itemList.get(i);
                pays = selectedItem.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //db
        databaseManager = new DatabaseManager(this);

        ArrayList<String> pays = new ArrayList<>(Arrays.asList("canada","us","mexique","brésil","france","angleterre","russie","chine","inde","espagne"));
        //spinnerPays.setAdapter(new ArrayAdapter<String>(this,R.layout.simpletextlayout,pays));
        newCompteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

    }

    private boolean isValidate(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @SuppressLint("SetTextI18n")
    private void validation() {
        boolean retVal = true;

        if(this.prenom.getText().toString().trim().isEmpty() || this.prenom.getText().toString().trim().length()<3){
            this.prenom.setError("Adress Courriel doit contenir plus de 3 caratères");
            retVal = false;
        }
        if(this.nom.getText().toString().trim().isEmpty() || this.nom.getText().toString().trim().length()<3){
            this.nom.setError("Mot de passe doit contenir plus de 3 caratères");
            retVal = false;
        }
        if(this.email.getText().toString().trim().isEmpty() || this.email.getText().toString().trim().length()<6 || !isValidate(this.email.getText().toString().trim())){
            this.email.setError("Mot de passe doit contenir plus de 6 et contenir un @");
            retVal = false;
        }
        if(this.passWord.getText().toString().trim().isEmpty() || this.passWord.getText().toString().trim().length()<6){
            this.passWord.setError("Mot de passe doit contenir plus de 6 caratères");
            retVal = false;
        }
       if(pays.isEmpty()){
            retVal = false;
        }


        if(retVal){
            //insert new joueur
            databaseManager.insertJoueur(new Joueur(this.nom.getText().toString().trim(),this.prenom.getText().toString().trim(),this.email.getText().toString().trim(),this.passWord.getText().toString().trim(),pays));

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.popup);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    backToLogin();
                }
            });
            Window window = dialog.getWindow();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = 800;
            layoutParams.height = 600;
            window.setAttributes(layoutParams);

            dialog.show();
        }
        else {

        }
    }

    public void backToLogin(){
        Intent intent = new Intent(this, LoginPage.class);
        super.startActivity(intent);
    }




}