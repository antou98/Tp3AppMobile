package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jeudepart.BD.DatabaseManager;
import com.example.jeudepart.BD.Joueur;
import com.example.jeudepart.BD.SpinnerItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateAccountPage extends AppCompatActivity {

    private TextView prenom;

    private TextView nom;

    private TextView email;

    private TextView passWord;

    private Spinner spinnerPays;

    private Button newCompteButton;

    private DatabaseManager databaseManager;

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

        ArrayList<SpinnerItem> itemList = new ArrayList<>();
        itemList.add(new SpinnerItem("Canada", R.drawable.flag_of_canada));
        itemList.add(new SpinnerItem("US", R.drawable.flag_of_the_united_states));
        //SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,itemList);
        //spinnerPays.setAdapter(spinnerAdapter);

        //db
        databaseManager = new DatabaseManager(this);

        ArrayList<String> pays = new ArrayList<>(Arrays.asList("canada","us","mexique","brésil","france","angleterre","russie","chine","inde","espagne"));
        spinnerPays.setAdapter(new ArrayAdapter<String>(this,R.layout.simpletextlayout,pays));
        newCompteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

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
        if(this.email.getText().toString().trim().isEmpty() || this.email.getText().toString().trim().length()<6 ){
            this.email.setError("Mot de passe doit contenir plus de 6 ");
            retVal = false;
        }
        if(this.passWord.getText().toString().trim().isEmpty() || this.passWord.getText().toString().trim().length()<6){
            this.passWord.setError("Mot de passe doit contenir plus de 6 caratères");
            retVal = false;
        }
       if(this.spinnerPays.getSelectedItem().toString().trim().isEmpty()){
            retVal = false;
        }


        if(retVal){
            //insert new joueur
            databaseManager.insertJoueur(new Joueur(this.nom.getText().toString().trim(),this.prenom.getText().toString().trim(),this.email.getText().toString().trim(),this.passWord.getText().toString().trim(),this.spinnerPays.getSelectedItem().toString().trim()));

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