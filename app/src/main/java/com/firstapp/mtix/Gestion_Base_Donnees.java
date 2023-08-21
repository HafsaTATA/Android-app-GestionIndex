package com.firstapp.mtix;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.firstapp.mtix.model.Client;

import java.util.ArrayList;
import java.util.List;

public class Gestion_Base_Donnees  extends SQLiteOpenHelper {

    public static final String nom_database = "Gestion_Index.db";
    public static final String nom_table_releveur = "Releveur";
    public static final String COL_1_releveur = "code_matricule";
    public static final String COL_2_releveur = "mot_de_passe";
    public static final String COL_3_releveur = "nom_prenom";

    public static final String nom_table_secteur = "Secteur";
    public static final String COL_1_secteur = "code_secteur";
    public static final String COL_2_secteur = "titule";
    public static final String COL_3_secteur = "code_matricule_fk";


    public static final String nom_table_batiment = "Batiment";
    public static final String COL_1_batiment = "id_batiment";
    public static final String COL_2_batiment = "adresse";
    public static final String COL_3_batiment = "code_secteur";

    public static final String nom_table_propriete = "Propriete";
    public static final String COL_1_propriete_etage = "numero_etage";
    public static final String COL_2_propriete_batiment_fk = "id_batiment";
    public static final String COL_3_propriete = "propriete";
    public static final String COL_4_propriete_compteur_fk = "id_compteur";

    public static final String nom_table_compteur = "Compteur";
    public static final String COL_1_compteur = "id_compteur";
    public static final String COL_2_compteur = "type_de_gerence";
    public static final String COL_3_compteur = "propriete";
    public static final String COL_4_compteur = "numero_etage";
    public static final String COL_5_compteur = "id_batiment";

    public static final String nom_table_client = "Client";
    public static final String COL_1_client = "id_client";
    public static final String COL_2_client = "nom_prenom";
    public static final String COL_4_client = "numero_etage";
    public static final String COL_5_client = "id_batiment";

    public static final String nom_table_releve = "Releve";
    public static final String COL_1_releve = "numero_facture";
    public static final String COL_2_releve = "Indx";
    public static final String COL_3_releve = "Date_de_releve";
    public static final String COL_4_releve = "heure_de_releve";
    public static final String COL_5_releve = "numéro_tourné";
    public static final String COL_6_releve = "id_compteur";
    public static final String COL_7_releve = "code_matricule";
    public static final String COL_8_releve = "Anomalie";

    public Gestion_Base_Donnees(@Nullable Context context) {
        super(context, nom_database, null, 1);
        //   SQLiteDatabase db = this.getWritableDatabase();


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String req1 = "CREATE TABLE " + nom_table_releveur + " (" +
                COL_1_releveur + " TEXT PRIMARY KEY, " +
                COL_2_releveur + " TEXT, " +
                COL_3_releveur + " TEXT)";
        db.execSQL(req1);

        String req2 = "CREATE TABLE " + nom_table_secteur + " (" +
                COL_1_secteur + " integer primary key, " +
                COL_2_secteur + " Text, " +
                COL_3_secteur + " integer REFERENCES " + nom_table_releveur + "(" + COL_1_releveur + "))";
        db.execSQL(req2);

        String req3 = "CREATE TABLE " + nom_table_batiment + " (" +
                COL_1_batiment + " integer primary key, " +
                COL_2_batiment + " Text, " +
                COL_3_batiment + " integer REFERENCES " + nom_table_secteur + "(" + COL_1_secteur + "))";
        db.execSQL(req3);

        String req4 = "CREATE TABLE " + nom_table_propriete + " (" +
                COL_1_propriete_etage + " integer, " +
                COL_2_propriete_batiment_fk + " integer, " +
                COL_3_propriete + " Text, " +
                COL_4_propriete_compteur_fk + " integer, " +
                "PRIMARY KEY (" + COL_1_propriete_etage + ", " + COL_2_propriete_batiment_fk + "), " +
                "FOREIGN KEY (" + COL_2_propriete_batiment_fk + ") REFERENCES " + nom_table_batiment + "(" + COL_1_batiment + "), " +
                "FOREIGN KEY (" + COL_4_propriete_compteur_fk + ") REFERENCES " + nom_table_compteur + "(" + COL_1_compteur + "))";
        db.execSQL(req4);


        String req5 = "CREATE TABLE " + nom_table_compteur + " (" +
                COL_1_compteur + " integer primary key, " +
                COL_2_compteur + " Text, " +
                COL_3_compteur + " Text, " +
                COL_4_compteur + " integer, " +
                COL_5_compteur + " integer REFERENCES " + nom_table_batiment + "(" + COL_1_batiment + "))";
        db.execSQL(req5);

        String req6 = "CREATE TABLE " + nom_table_client + " (" +
                COL_1_client + " INTEGER PRIMARY KEY, " +
                COL_2_client + " TEXT, " +
                COL_4_client + " INTEGER, " +
                COL_5_client + " INTEGER REFERENCES " + nom_table_batiment + "(" + COL_1_batiment + "))";
        db.execSQL(req6);

        String req7 = "CREATE TABLE " + nom_table_releve + " (" +
                COL_1_releve + " Integer primary key, " +
                COL_2_releve + "Integer, " +
                COL_3_releve + " Text, " +
                COL_4_releve + " Text, " +
                COL_5_releve + " Text, " +
                COL_6_releve + " integer REFERENCES " + nom_table_compteur + "(" + COL_1_compteur + "), " +
                COL_7_releve + " integer REFERENCES " + nom_table_releveur + "(" + COL_1_releveur + "),"+
                COL_8_releve + " TEXT)";
        db.execSQL(req7);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_releveur);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_secteur);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_batiment);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_propriete);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_compteur);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_client);
        db.execSQL("DROP TABLE IF EXISTS " + nom_table_releve);

        onCreate(db);
    }

    public void insertDonneesTest() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT COUNT(*) FROM " + nom_table_releveur;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        // Si les données de test existent déjà, ne pas les insérer à nouveau
        if (count > 0) {
            Log.d("Insertion", "Les données de test existent déjà dans la table Releveur.");
            return;
        }

        ContentValues valeurs = new ContentValues();
        valeurs.put(COL_1_releveur, "33334444");
        valeurs.put(COL_2_releveur, "11122");
        valeurs.put(COL_3_releveur, "fatih ahmed"); // Fusion de "nom" et "prenom"


        long result = db.insert(nom_table_releveur, null, valeurs);
        if (result == -1) {
            Log.d("Insertion", "Erreur lors de l'insertion des données de test dans la table Releveur.");
        } else {
            Log.d("Insertion", "Données de test insérées avec succès dans la table Releveur.");
        }

        Cursor testCursor = db.query(nom_table_releveur, null, null, null, null, null, null);
        if (testCursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String codeMatricule = testCursor.getString(testCursor.getColumnIndex(COL_1_releveur));
                @SuppressLint("Range") String motDePasse = testCursor.getString(testCursor.getColumnIndex(COL_2_releveur));
                @SuppressLint("Range") String nomPrenom = testCursor.getString(testCursor.getColumnIndex(COL_3_releveur)); // Nouvelle colonne
                Log.d("Insertion", "Données de test récupérées : Code Matricule = " + codeMatricule
                        + ", Mot de passe = " + motDePasse + ", Nom et Prénom = " + nomPrenom);
            } while (testCursor.moveToNext());
        }
        testCursor.close();

        db.close();
    }


    public boolean verifierUtilisateur(String codeMatricule, String motDePasse) {
        Log.d("Verification", "Code Matricule à vérifier : " + codeMatricule);
        Log.d("Verification", "Mot de passe à vérifier : " + motDePasse);

        SQLiteDatabase db = this.getReadableDatabase();
        String[] colonnes = {COL_1_releveur};
        String selection = COL_1_releveur + " = ? AND " + COL_2_releveur + " = ?";
        String[] selectionArgs = {codeMatricule, motDePasse};
        Cursor cursor = db.query(nom_table_releveur, colonnes, selection, selectionArgs, null, null, null);
        boolean utilisateurExiste = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        if (utilisateurExiste) {
            Log.d("Verification", "Utilisateur trouvé dans la table Releveur.");
        } else {
            Log.d("Verification", "Utilisateur non trouvé dans la table Releveur.");
        }

        return utilisateurExiste;
    }


    @SuppressLint("Range")
    public String getFullNameFromCodeMatricule(String codeMatricule) {
        if (codeMatricule == null) {
            return null;
        }

        String fullName = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_3_releveur};
        String selection = COL_1_releveur + " = ?";
        String[] selectionArgs = {codeMatricule};
        Cursor cursor = db.query(nom_table_releveur, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            fullName = cursor.getString(cursor.getColumnIndex(COL_3_releveur));
        }

        cursor.close();
        db.close();
        return fullName;
    }


    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();


        SQLiteDatabase db = getReadableDatabase();


        String[] columns = {"id_client", "nom_prenom"};

        Cursor cursor = db.query("Client", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id_client"));
                @SuppressLint("Range") String nomPrenom = cursor.getString(cursor.getColumnIndex("nom_prenom"));
                Client client = new Client(id, nomPrenom);
                clients.add(client);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return clients;
    }

    @SuppressLint("Range")
    public int getIdCompteurPourTypeGerence(int typeDeGerence) {
        SQLiteDatabase db = getReadableDatabase();
        int idCompteur = -1;

        String[] columns = {COL_1_compteur};
        String selection = COL_2_compteur + " = ?";
        String[] selectionArgs = {String.valueOf(typeDeGerence)};

        Cursor cursor = db.query(nom_table_compteur, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            idCompteur = cursor.getInt(cursor.getColumnIndex(COL_1_compteur));
        }

        cursor.close();
        db.close();
        return idCompteur;
    }

    @SuppressLint("Range")
    public int getTypeDeGerencePourCompteur(int idCompteur) {
        SQLiteDatabase db = getReadableDatabase();
        int typeDeGerence = -1;

        String[] columns = {COL_2_compteur};
        String selection = COL_1_compteur + " = ?";
        String[] selectionArgs = {String.valueOf(idCompteur)};

        Cursor cursor = db.query(nom_table_compteur, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            typeDeGerence = cursor.getInt(cursor.getColumnIndex(COL_2_compteur));
        }

        cursor.close();
        db.close();
        return typeDeGerence;
    }



    public void insererDonneesReleve(String date, double valeur, String s, int idCompteur, String codeMatricule, String anomalie) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_2_releve, valeur);
        values.put(COL_3_releve, date);
        values.put(COL_5_releve, s);
        values.put(COL_6_releve, idCompteur);
        values.put(COL_7_releve, codeMatricule);
        values.put(COL_8_releve, anomalie);

        long result = db.insert(nom_table_releve, null, values);

        if (result == -1) {
            Log.d("Insertion", "Erreur lors de l'insertion des données dans la table Releve.");
        } else {
            Log.d("Insertion", "Données insérées avec succès dans la table Releve.");
        }

        db.close();
    }


}