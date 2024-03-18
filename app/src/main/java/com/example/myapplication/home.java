package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class home extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        generateButtons(10);

        View toolbar = findViewById(R.id.toolbar);
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setupToolbarActions(toolbar);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void generateButtons(int buttonCount) {
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        // Nombre de boutons à générer
        // int buttonCount = 20;

        for (int i = 0; i < buttonCount; i++) {
            milestone button = new milestone(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(80), // Largeur
                    dpToPx(80) // Hauteur
            );

            int valeurAleatoire = new Random().nextInt(180 - 120 + 1) + 120;

            params.leftMargin = dpToPx(valeurAleatoire); // Marge gauche
            params.topMargin = dpToPx(20); // Marge top
            button.setLayoutParams(params);

            buttonContainer.addView(button);
        }
    }

    // Méthode pour convertir dp en pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
