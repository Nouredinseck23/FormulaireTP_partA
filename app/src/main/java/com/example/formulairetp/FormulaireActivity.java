package com.example.formulairetp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executors;

public class FormulaireActivity extends AppCompatActivity {

    EditText etNom, etEmail, etTelephone;
    Button btnValider, btnEffacer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        etNom = findViewById(R.id.etNom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);
        btnValider = findViewById(R.id.btnValider);
        btnEffacer = findViewById(R.id.btnEffacer);

        loadFromPrefs();
        chargerUtilisateurs();

        btnEffacer.setOnClickListener(v -> {
            deleteinfo();
            Toast.makeText(this, "Données effacées", Toast.LENGTH_SHORT).show();
        });

        btnValider.setOnClickListener(v -> {
            String nom = etNom.getText().toString();
            String email = etEmail.getText().toString();
            String telephone = etTelephone.getText().toString();

            saveToPrefs(nom, email, telephone);
            saveToRoom(nom, email, telephone);
        });
    }

    private void saveToPrefs(String nom, String email, String telephone) {
        SharedPreferences sp = getSharedPreferences("form", MODE_PRIVATE);
        sp.edit()
                .putString("nom", nom)
                .putString("email", email)
                .putString("telephone", telephone)
                .apply();
    }

    private void loadFromPrefs() {
        SharedPreferences sp = getSharedPreferences("form", MODE_PRIVATE);
        etNom.setText(sp.getString("nom", ""));
        etEmail.setText(sp.getString("email", ""));
        etTelephone.setText(sp.getString("telephone", ""));
    }

    private void deleteinfo() {
        SharedPreferences sp = getSharedPreferences("form", MODE_PRIVATE);
        sp.edit().clear()
                .apply();
        etNom.setText("");
        etEmail.setText("");
        etTelephone.setText("");
    }

    private void saveToRoom(String nom, String email, String telephone) {
        AppDatabase db = DbProvider.get(this);

        Executors.newSingleThreadExecutor().execute(() -> {
            Entite utilisateur = new Entite();
            utilisateur.name = nom;
            utilisateur.email = email;
            utilisateur.phone = telephone;
            utilisateur.createdAt = System.currentTimeMillis();

            long id = db.userDao().insert(utilisateur);

            runOnUiThread(() -> {
                Toast.makeText(this, "Utilisateur enregistré (id=" + id + ")", Toast.LENGTH_SHORT).show();
                chargerUtilisateurs(); // recharge la liste après insertion
            });
        });
    }

    private void chargerUtilisateurs() {
        AppDatabase db = DbProvider.get(this);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Entite> utilisateurs = db.userDao().getAll();
            runOnUiThread(() -> {
                Log.d("DB", "Nombre d'utilisateurs enregistrés : " + utilisateurs.size());
                for (Entite u : utilisateurs) {
                    Log.d("DB", "Utilisateur : " + u.name + ", Email : " + u.email + ", Tel : " + u.phone);
                }
            });
        });
    }
}

