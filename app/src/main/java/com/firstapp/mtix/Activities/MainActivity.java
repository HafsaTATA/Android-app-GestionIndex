package com.firstapp.mtix.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstapp.mtix.ClientAdapter;
import com.firstapp.mtix.Gestion_Base_Donnees;
import com.firstapp.mtix.R;
import com.firstapp.mtix.model.Client;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String codeMatricule;
    ImageView logout;

    TextView fullName;
    private RecyclerView recyclerView;
    private ClientAdapter adapter;

    private List<Client> clientList;
    private Button clientRestant;


    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmer la déconnexion");
        builder.setMessage("Êtes-vous sûr de vouloir vous déconnecter?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }




    private void searchClient(String clientId) {
        List<Client> filteredClients = new ArrayList<>();
        for (Client client : clientList) {
            if (String.valueOf(client.getId()).equals(clientId)) {
                filteredClients.add(client);
            }
        }

        adapter.setClientList(filteredClients);
    }





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
        codeMatricule = preferences.getString("codeMatricule", null);

        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
        clientRestant=findViewById(R.id.compteur_restants);
        fullName = findViewById(R.id.fullName);


        Intent intent = getIntent();

        if (intent.hasExtra("codeMatricule")) {

            codeMatricule = intent.getStringExtra("codeMatricule");

            Log.d("wellek khdm", "codeMatricule value after starting MainActivity: " + codeMatricule);
        } else {
            Log.e("wellek khdm", "No MATRICULE found in the intent");
        }

        Gestion_Base_Donnees gestionBaseDonnees = new Gestion_Base_Donnees(this);
        String nomComplet = gestionBaseDonnees.getFullNameFromCodeMatricule(codeMatricule);
        Log.d("fullname","name: "+nomComplet);

        fullName.setText(nomComplet);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gestionBaseDonnees = new Gestion_Base_Donnees(this);
        clientList = gestionBaseDonnees.getAllClients();

        adapter = new ClientAdapter(clientList);

        recyclerView.setAdapter(adapter);

        clientRestant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();
                List<Client> filteredClients = new ArrayList<>();

                for (Client client : clientList) {
                    if (String.valueOf(client.getId()).contains(searchText)) {
                        filteredClients.add(client);
                    }
                }

                adapter.setClientList(filteredClients);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLogoutConfirmationDialog();
            }
        });



    }
}