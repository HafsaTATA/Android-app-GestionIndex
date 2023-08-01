package com.firstapp.mtix.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firstapp.mtix.Gestion_Base_Donnees;
import com.firstapp.mtix.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Gestion_Base_Donnees GB;
    private TextInputEditText matriculeInput, mdpInput;
    private TextInputLayout matriculeLayout,mdpLayout;
    private Button continueButton ;


    private boolean isLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
        // Replace "user_token" with the key used to store the user token in SharedPreferences
        // If the user token exists in SharedPreferences, it means the user is logged in.
        return preferences.contains("user_token");
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //before anything test if he already logged in:
        if (isLoggedIn()) {
            // User is logged in, redirect to the main activity
            redirectToMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);

        GB = new Gestion_Base_Donnees(this);
        matriculeInput =  findViewById(R.id.matriculeInput);
        mdpInput =  findViewById(R.id.mdpInput);
        continueButton = (Button) findViewById(R.id.continueButton);
        matriculeLayout=findViewById(R.id.matriculeLayout);
        mdpLayout=findViewById(R.id.mdpLayout);

        matriculeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                continueButton.setEnabled(true);
                mdpLayout.setError(null);
                matriculeLayout.setError(null);
                //for when the user tries again,the button must be enabled and errors gone
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mdpInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                continueButton.setEnabled(true);
                mdpLayout.setError(null);
                matriculeLayout.setError(null);
                //for when the user tries again,the button must be enabled and errors gone
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeMatricule = matriculeInput.getText().toString();
                String motDePasse = mdpInput.getText().toString();

                if (GB.verifierUtilisateur(codeMatricule, motDePasse)) {
                    // L'utilisateur existe dans la table Releveur, passer à l'interface suivante
                    // Placez ici le code pour passer à l'interface suivante
                    Toast.makeText(LoginActivity.this, " Identification réussie !", Toast.LENGTH_SHORT).show();
                    continueButton.setEnabled(true);
                    mdpLayout.setError(null);
                    matriculeLayout.setError(null);

                    // Store the user token in SharedPreferences to indicate the user is logged in
                    SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_token", "YOUR_USER_TOKEN");
                    editor.apply();


                    redirectToMainActivity();
                } else {
                    continueButton.setEnabled(false);
                    mdpLayout.setError(" ");
                    matriculeLayout.setError(" ");
                    Toast.makeText(LoginActivity.this, " Code matricule ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}