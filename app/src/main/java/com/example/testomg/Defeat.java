package com.example.testomg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class Defeat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        // Récupérer les données passées avec l'intent
        Intent intent = getIntent();
        int nombreEssais = intent.getIntExtra("nombreEssais", 0);
        String motATrouver = intent.getStringExtra("motATrouver");
        long temps = intent.getLongExtra("temps", 0);

        // Afficher les données dans les TextView de votre mise en page
        TextView essaisTextView = findViewById(R.id.essaisTextView);
        essaisTextView.setText("Défaite Totale");

        TextView motATrouverTextView = findViewById(R.id.motATrouverTextView);
        motATrouverTextView.setText("Mot à trouver : " + motATrouver);

    }
}