package com.example.testomg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end);

        // Récupérer les données passées avec l'intent
        Intent intent = getIntent();
        String[] tableauDeString = intent.getStringArrayExtra("listMots");
        int[] tableauDeInt = intent.getIntArrayExtra("listValidOrNot");
        int nombreEssais = intent.getIntExtra("nombreEssaisTotaux", 0);

        // Afficher les données dans les TextView de votre mise en page
        TextView essaisTextView = findViewById(R.id.essaisTextView);
        essaisTextView.setText("Nombre d'essais : " + nombreEssais);

        // Récupérer le conteneur pour les mots à afficher
        LinearLayout motATrouverContainer = findViewById(R.id.motATrouverContainer);

        // Pour chaque élément du tableau, créer un nouveau TextView et l'ajouter au conteneur
        for (int i = 0; i < tableauDeInt.length; i++) {
            TextView nouveauTextView = new TextView(this);
            nouveauTextView.setText(tableauDeString[i]);

            // Définissez la couleur de fond du TextView en fonction de la valeur dans le tableau d'entiers
            if (tableauDeInt[i] == 1) {
                nouveauTextView.setBackgroundColor(Color.GREEN);
            } else {
                nouveauTextView.setBackgroundColor(Color.RED);
            }

            // Ajouter le TextView au conteneur
            motATrouverContainer.addView(nouveauTextView);
        }

    }
}