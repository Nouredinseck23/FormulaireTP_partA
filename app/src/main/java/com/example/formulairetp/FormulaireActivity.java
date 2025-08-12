package com.example.formulairetp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormulaireActivity extends AppCompatActivity {

    EditText etNom, etEmail, etTelephone;
    Button btnValider,btnEffacer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        etNom = findViewById(R.id.etNom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);
        btnValider = findViewById(R.id.btnValider);
        btnEffacer=  findViewById(R.id.btnEffacer);
        loadFromPrefs();

        btnEffacer.setOnClickListener(v -> {
            deleteinfo();
            Toast.makeText(this, "Données effacées", Toast.LENGTH_SHORT).show();
        });
        btnValider.setOnClickListener(v -> {
            String nom = etNom.getText().toString();
            String email = etEmail.getText().toString();
            String telephone = etTelephone.getText().toString();

            saveToPrefs(nom, email, telephone);
            Toast.makeText(this, "Données enregistrées", Toast.LENGTH_SHORT).show();
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

}

