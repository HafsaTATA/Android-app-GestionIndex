package com.firstapp.mtix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.firstapp.mtix.R;

public class Releve extends AppCompatActivity {
    private Spinner spinnerOptions1,spinnerOptions2;
    private EditText dateEau,dateElec;
    private Button picEau,picElec,precedent;

    private void showDatePickerDialog(EditText date) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                        date.setText(selectedDate);
                    }
                },2023,1,1);

        datePickerDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releve);
        spinnerOptions1 = findViewById(R.id.Anomalie);
        spinnerOptions2=findViewById(R.id.AnomalieElecElec);
        dateEau = findViewById(R.id.Dateeau);
        dateElec=findViewById(R.id.DateElec);
        picEau=findViewById(R.id.pictureEau);
        picElec=findViewById(R.id.pictureElec);
        precedent=findViewById(R.id.precedent);

        // SPINNER1 CODE:
        String[] options = {"null", "Compteur inaccessible", "Vitre cassée", "Compteur douteux","Compteur bloqué"};
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
                onBackPressed();
            }
        });
        //meter pictures manager:

    }
}