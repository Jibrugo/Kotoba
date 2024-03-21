package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class home extends AppCompatActivity
{
    public WordManager wordManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        View toolbar = findViewById(R.id.toolbar);
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setupToolbarActions(toolbar);
        this.wordManager = new WordManager(home.this);

        generateButtons(12);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void generateButtons(int buttonCount) {
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        for (int i = 0; i < buttonCount; i++) {
            milestone button = new milestone(this,i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(80), // Largeur
                    dpToPx(80) // Hauteur
            );

            int valeurAleatoire = new Random().nextInt(180 - 120 + 1) + 120;

            params.leftMargin = dpToPx(valeurAleatoire); // Marge gauche
            params.topMargin = dpToPx(20); // Marge top
            button.setLayoutParams(params);
            buttonContainer.addView(button);

            int score = this.wordManager.getWordsScore(i);

//          Log.d("Elias", String.valueOf(score));
            button.set_progress(score);

//            // Ajouter un écouteur de clic à chaque bouton
//            button.setOnClickListener(v -> {
//                // Code à exécuter lors du clic sur le bouton
//                //  Toast.makeText(this, "Bouton cliqué", Toast.LENGTH_SHORT).show();
//                readLineFromFile
//            });

            button.setCustomClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Fonction à exécuter lors du clic sur milestone1
                    //  int[] liste = readLineFromAssets(home.this, "niveaux.txt", button.getBtnId());
                     int[] liste = wordManager.getWordsSize(button.getBtnId());

                    // Création de l'intent
                    Intent intent = new Intent(home.this, game.class);

                    // Ajout de la liste à l'intent
                    intent.putExtra("INT_ARRAY", liste);

                    // Démarrage de l'activité de destination
                    startActivity(intent);
                }
            });
        }
    }


    // Méthode pour convertir dp en pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static int[] readLineFromAssets(Context context, String fileName, int lineNumber) {
        AssetManager assetManager = context.getAssets();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open(fileName)))) {
            String line;
            int currentLineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (currentLineNumber == lineNumber) {
                    String[] parts = line.split(",");
                    int[] values = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        values[i] = Integer.parseInt(parts[i].trim());
                    }
                    return values;
                }
                currentLineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur lors de la conversion en entier : " + e.getMessage());
        }
        return null; // Si la ligne spécifiée n'est pas trouvée ou s'il y a une erreur de lecture
    }
}
