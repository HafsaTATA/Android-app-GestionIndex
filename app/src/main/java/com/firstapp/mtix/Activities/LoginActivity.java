package com.firstapp.mtix.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                    //insert here the constructor of second activity:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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