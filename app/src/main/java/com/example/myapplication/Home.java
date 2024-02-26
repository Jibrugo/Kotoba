package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Home extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        generateButtons();
    }

    private void generateButtons() {
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        List<Integer> liste = new ArrayList<>(Arrays.asList(165, 125, 145, 125, 145));

        // Nombre de boutons à générer
        int buttonCount = 20;

        for (int i = 0; i < buttonCount; i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(80), // Largeur
                    dpToPx(80) // Hauteur
            );
            int valeurAleatoire = new Random().nextInt(180 - 120 + 1) + 120;

            params.leftMargin = dpToPx(valeurAleatoire);//liste.get(i)); // Marge gauche
            params.topMargin = dpToPx(20); // Marge top
            button.setLayoutParams(params);
            button.setBackground(getResources().getDrawable(R.drawable.rounded_button));
            button.setText(" Button "+i+" ");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Action à exécuter lors du clic sur le bouton
                    // Par exemple, afficher un toast
                    Toast.makeText(Home.this, "Bouton cliqué", Toast.LENGTH_SHORT).show();
//                    setContentView(R.layout.login);
                }
            });

            // Ajoutez le bouton au conteneur
            buttonContainer.addView(button);
        }
    }

    // Méthode pour convertir dp en pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
