package com.firstapp.mtix.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

        if (isLoggedIn()) {
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
                    Toast.makeText(LoginActivity.this, " Identification réussie !", Toast.LENGTH_SHORT).show();
                    continueButton.setEnabled(true);
                    mdpLayout.setError(null);
                    matriculeLayout.setError(null);

                    SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_token", "YOUR_USER_TOKEN");
                    editor.putString("codeMatricule", codeMatricule);
                    editor.apply();


                    Intent intent1 = new Intent(LoginActivity.this, Releve.class);
                    intent1.putExtra("codeMatricule", codeMatricule);

                    Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                    intent2.putExtra("codeMatricule", codeMatricule);
                    startActivity(intent2);
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