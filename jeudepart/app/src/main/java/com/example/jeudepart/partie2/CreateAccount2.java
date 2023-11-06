package com.example.jeudepart.partie2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeudepart.R;
import com.example.jeudepart.partie1.BD.DatabaseManager;
import com.example.jeudepart.partie1.BD.Joueur;
import com.example.jeudepart.partie1.BD.SpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount2 extends AppCompatActivity {

    private TextView prenom;

    private TextView nom;

    private TextView email;

    private TextView passWord;

    private Spinner spinnerPays;

    private Button newCompteButton;
    private RequestQueue requestQueue;
    private final String ipaddress = "192.168.2.48";
    private final String Url = "http://"+ipaddress+":5000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

        prenom = (TextView) findViewById(R.id.prenomTextView2);
        nom = (TextView) findViewById(R.id.nomTextView2);
        email = (TextView) findViewById(R.id.emailTextView2);
        passWord = (TextView) findViewById(R.id.passwordTextView2);
        spinnerPays = (Spinner) findViewById(R.id.spinner2);
        newCompteButton = (Button) findViewById(R.id.buttonCreateNewCompte2);

        ArrayList<SpinnerItem> itemList = new ArrayList<>();
        itemList.add(new SpinnerItem("Canada", R.drawable.canada));
        itemList.add(new SpinnerItem("US", R.drawable.united_states_of_america));
        //SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,itemList);
        //spinnerPays.setAdapter(spinnerAdapter);



        ArrayList<String> pays = new ArrayList<>(Arrays.asList("canada","us","mexique","brésil","france","angleterre","russie","chine","inde","espagne"));
        spinnerPays.setAdapter(new ArrayAdapter<String>(this,R.layout.simpletextlayout,pays));
        newCompteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        requestQueue = Volley.newRequestQueue(this);
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
        if(this.spinnerPays.getSelectedItem().toString().trim().isEmpty()){
            retVal = false;
        }


        if(retVal){
            //insert new joueur
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nom",this.nom.getText().toString().trim() );
                jsonObject.put("prenom", this.prenom.getText().toString().trim() );
                jsonObject.put("email", this.email.getText().toString().trim() );
                jsonObject.put("password", this.passWord.getText().toString().trim() );
                jsonObject.put("pays", this.spinnerPays.getSelectedItem().toString().trim() );
            }catch (JSONException  e){
                Log.i("Erreur json : ","Erreur convertir json createaccount2");
            }
            createJoueur(jsonObject);

            /*Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.popup);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.cancel();
                    dialog.dismiss();
                }
            });
            Window window = dialog.getWindow();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = 800;
            layoutParams.height = 600;
            window.setAttributes(layoutParams);

            dialog.show();*/
        }
        else {

        }
    }

    private void createJoueur(JSONObject requestBody) {
        String url = Url + "/api/joueurs";

            //JSONObject requestBody = new JSONObject(json);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle  POST response
                            Toast.makeText(CreateAccount2.this, "POST Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("APi ",response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle errors

                            Toast.makeText(CreateAccount2.this, "POST Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("APi error",error.toString());
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);

        }
}
