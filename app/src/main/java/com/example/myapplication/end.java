package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class end extends AppCompatActivity {


    private void adjustTextSizeBasedOnScreen(TextView textView) {
        // Obtenez les dimensions de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        // Définissez la taille du texte en fonction de la largeur de l'écran
        float textSize = screenWidth / getResources().getDisplayMetrics().density / 10; // Vous pouvez ajuster ce facteur

        // Appliquez la taille du texte au TextView
        textView.setTextSize(textSize);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.end);

        // Récupérer les données passées avec l'intent
        Intent intent = getIntent();
        Toast.makeText(this, "END BUTTON cliqué", Toast.LENGTH_LONG).show();
        String[] tableauDeString = intent.getStringArrayExtra("listMots");
        int[] tableauDeInt = intent.getIntArrayExtra("listValidOrNot");
        int nombreEssais = intent.getIntExtra("nombreEssaisTotaux", 7);

        // Afficher les données dans les TextView de votre mise en page
        TextView essaisTextView = findViewById(R.id.essaisTextView);
        essaisTextView.setTypeface(null, Typeface.BOLD);
        essaisTextView.setTextColor(Color.WHITE);
        essaisTextView.setText("Nombre d'essais : " + nombreEssais);
        adjustTextSizeBasedOnScreen(essaisTextView);
        // Récupérer le conteneur pour les mots à afficher
        LinearLayout motATrouverContainer = findViewById(R.id.motATrouverContainer);

        // Pour chaque élément du tableau, créer un nouveau TextView et l'ajouter au conteneur
        for (int i = 0; i < tableauDeInt.length; i++) {
            TextView nouveauTextView = new TextView(this);
            nouveauTextView.setTextColor(Color.WHITE);
            nouveauTextView.setText(tableauDeString[i]);
            nouveauTextView.setGravity(Gravity.CENTER);
            nouveauTextView.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if (i > 0) {
                layoutParams.topMargin = 16;
            }
            nouveauTextView.setLayoutParams(layoutParams);

            adjustTextSizeBasedOnScreen(nouveauTextView);
            // Définissez la couleur de fond du TextView en fonction de la valeur dans le tableau d'entiers
            if (tableauDeInt[i] == 1) {
                nouveauTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.VertMoyen));
            } else {
                nouveauTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.RougeMoyen));
            }

            // Ajouter le TextView au conteneur
            motATrouverContainer.addView(nouveauTextView);
        }
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();

        // Ajoutez un rappel d'événement pour le bouton de retour en arrière
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(end.this, home.class);
                startActivity(intent);
            }
        });
    }
}
