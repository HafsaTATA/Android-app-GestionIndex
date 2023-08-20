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

                // Perform logout: Clear the session data when the user logs out
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

        // Filtrer la liste des clients en fonction de l'ID client saisi
        List<Client> filteredClients = new ArrayList<>();
        for (Client client : clientList) {
            if (String.valueOf(client.getId()).equals(clientId)) {
                filteredClients.add(client);
            }
        }

        // Mettre à jour la liste des clients de l'adaptateur avec la liste filtrée
        adapter.setClientList(filteredClients);
    }





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the saved codeMatricule from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("your_session_pref_name", MODE_PRIVATE);
        codeMatricule = preferences.getString("codeMatricule", null);

        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
        clientRestant=findViewById(R.id.compteur_restants);
        fullName = findViewById(R.id.fullName); // Lier le EditText pour afficher le nom complet

        // Obtenir le code matricule de l'utilisateur à partir de l'activité de connexion
        Intent intent = getIntent();
        // Check if the intent has the "user_id" extra
        if (intent.hasExtra("codeMatricule")) {
            // Retrieve the user id from the intent
            codeMatricule = intent.getStringExtra("codeMatricule");


            // Now you have the user id, you can use it as needed
            // For example, you can log it or display it in a TextView
            Log.d("wellek khdm", "codeMatricule value after starting MainActivity: " + codeMatricule);
        } else {
            // Handle the case where the "user_id" extra is missing
            Log.e("wellek khdm", "No MATRICULE found in the intent");
        }


        // Utiliser la classe Gestion_Base_Donnees pour obtenir le nom complet de l'utilisateur
        Gestion_Base_Donnees gestionBaseDonnees = new Gestion_Base_Donnees(this);
        String nomComplet = gestionBaseDonnees.getFullNameFromCodeMatricule(codeMatricule);
        Log.d("fullname","name: "+nomComplet);

        // Mettre à jour le TextView avec le nom complet de l'utilisateur
        fullName.setText(nomComplet);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Appel de la méthode pour récupérer la liste des clients
        gestionBaseDonnees = new Gestion_Base_Donnees(this);
        clientList = gestionBaseDonnees.getAllClients();

        // Création de l'adaptateur pour le RecyclerView
        adapter = new ClientAdapter(clientList);

        recyclerView.setAdapter(adapter);

        clientRestant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Ajoutez ici la configuration du gestionnaire de texte pour search_bar
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