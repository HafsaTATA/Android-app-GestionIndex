package com.firstapp.mtix.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContentValuesKt;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firstapp.mtix.Gestion_Base_Donnees;
import com.firstapp.mtix.R;

public class Releve extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1234;
    private static final int CAPTURE_CODE =1001 ;
    private Uri image_uri;
    private Spinner spinnerOptions1, spinnerOptions2;
    private EditText dateEau, dateElec;
    private Button picEau, picElec, precedent;



    private void showDatePickerDialog(EditText date) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                        date.setText(selectedDate);
                    }
                }, 2023, 1, 1);

        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releve);

        Gestion_Base_Donnees databaseHelper = new Gestion_Base_Donnees(this);
        spinnerOptions1 = findViewById(R.id.Anomalie);
        spinnerOptions2 = findViewById(R.id.AnomalieElecElec);
        dateEau = findViewById(R.id.Dateeau);
        dateElec = findViewById(R.id.DateElec);
        picEau = findViewById(R.id.pictureEau);
        picElec = findViewById(R.id.pictureElec);
        precedent = findViewById(R.id.precedent);

        //pass data:
        Intent intent = getIntent();
         // Retrieve the saved codeMatricule from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
        String codeMatricule = preferences.getString("codeMatricule", null);
        // Check if the intent has the "user_id" extra
        String userId = intent.getStringExtra("user_id");



            // SPINNER1 CODE:
        String[] options = {"null", "Compteur inaccessible", "Vitre cassée", "Compteur douteux", "Compteur bloqué"};
        // Create an ArrayAdapter using the options array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        // Specify the layout to use when the list of options appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerOptions1.setAdapter(adapter);
        //SPINNER2 CODE:
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions2.setAdapter(adapter2);

        //DATE CODE:
        dateEau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dateEau);
            }
        });

        dateElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(dateElec);
            }
        });

        //precdent button code:
        precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String date;
                    double valeur;
                    String anomalie;
                    int idCompteurSelectionne = 0;
                    int typeDeGerence;

                    // Vérifier quel onglet est actif (eau ou électricité)
                    if (dateEau.getVisibility() == View.VISIBLE) {
                        date = dateEau.getText().toString();
                        valeur = Double.parseDouble(((EditText) findViewById(R.id.Eau)).getText().toString());
                        anomalie = spinnerOptions1.getSelectedItem().toString();
                        // Obtenez idCompteurSelectionne pour l'eau

                        typeDeGerence = databaseHelper.getTypeDeGerencePourCompteur(idCompteurSelectionne);

                        if (typeDeGerence == 1) {
                            // Insérez les données dans la table Releve
                            databaseHelper.insererDonneesReleve(date, valeur, "33", idCompteurSelectionne, codeMatricule, anomalie);
                            Toast.makeText(Releve.this, "Données eau insérées", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Releve.this, "Ce compteur n'est pas de type de gerence eau (1)", Toast.LENGTH_SHORT).show();
                        }
                    } else if (dateElec.getVisibility() == View.VISIBLE) {
                        date = dateElec.getText().toString();
                        valeur = Double.parseDouble(((EditText) findViewById(R.id.Elec)).getText().toString());
                        anomalie = spinnerOptions2.getSelectedItem().toString();
                        // Obtenez idCompteurSelectionne pour l'électricité

                        typeDeGerence = databaseHelper.getTypeDeGerencePourCompteur(idCompteurSelectionne);

                        if (typeDeGerence == 2) {
                            // Insérez les données dans la table Releve
                            databaseHelper.insererDonneesReleve(date, valeur, "33", idCompteurSelectionne, codeMatricule, anomalie);
                            Toast.makeText(Releve.this, "Données électricité insérées", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Releve.this, "Ce compteur n'est pas de type de gerence électricité (2)", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Insertion", "Erreur lors des opérations de base de données : " + e.getMessage());
                }

                //go back:
                onBackPressed();
            }
        });
        //meter pictures manager:
        picEau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCameraPermissionDialog(picEau);
            }
        });

        picElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCameraPermissionDialog(picElec);
            }
        });
    }

    private void showCameraPermissionDialog(Button button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autorisation de caméra")
                .setMessage("Voulez-vous utiliser l'appareil photo?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionsManager();
                        button.setBackgroundColor(ContextCompat.getColor(Releve.this, R.color.BluePrimary));
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }

    private void permissionsManager() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED ){

                String [] permission= {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE} ;
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera();
            }
        }else{
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues values= new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camintent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camintent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(camintent,CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                }
        }

    }


}


