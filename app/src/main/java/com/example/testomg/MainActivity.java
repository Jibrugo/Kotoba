package com.example.testomg;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import java.util.Random;
import android.view.MenuItem;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.text.InputFilter;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    public String selectRandom(int taille) {
        BufferedReader reader = null;
        List<String> words = new ArrayList<>();
        try {

            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("mots_taille_" + taille + "_lettres.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                words.add(mLine.trim());
            }

            // Vérifiez si la liste n'est pas vide avant de sélectionner un mot aléatoire
            if (!words.isEmpty()) {
                // Générez un index aléatoire
                Random random = new Random();
                int randomIndex = random.nextInt(words.size());

                // Récupérez le mot à l'index aléatoire
                return words.get(randomIndex);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean rechercherMotDansFichier(String motRecherche) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("dicoscrabble.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                if(mLine.equals(motRecherche)){
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("dicoscrabble2.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                if(mLine.equals(motRecherche)){
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Fonction pour comparer le mot proposé avec le mot mystère
    private int checkMot(char lettreMystere, char lettreProposee, String motMyst) {

            // Comparer les lettres
            if (lettreProposee == lettreMystere) {
                // Lettre bien placée (couleur verte)
                return Color.GREEN;
            } else if (motMyst.contains(String.valueOf(lettreProposee))) {
                // Lettre mal placée (couleur orange)
                return Color.YELLOW;
            } else {
                // Lettre absente (couleur rouge)
                return Color.RED;
            }
    }
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
    private void afficherLettresAvecCouleur(LinearLayout layoute ,String motPropose, String motMystere) {
        layoute.removeAllViews(); // Effacez les vues précédentes

        // Parcourez chaque lettre du mot proposé
        for (int i = 0; i < motPropose.length(); i++) {
            char lettrePropo = Character.toUpperCase(motPropose.charAt(i));
            char lettreMyst = Character.toUpperCase(motMystere.charAt(i));

            // Créez un TextView pour chaque lettre
            TextView lettreTextView = new TextView(this);
            lettreTextView.setText(String.valueOf(lettrePropo));
            lettreTextView.setTextColor(Color.BLACK);
            lettreTextView.setGravity(Gravity.CENTER);
            adjustTextSizeBasedOnScreen(lettreTextView); // Ajustez la taille de la police selon vos besoins
            // Appliquez la couleur de fond en fonction de la lettre et de la règle que vous avez définie
            int couleurFond = checkMot(lettreMyst,lettrePropo, motMystere);
            lettreTextView.setBackgroundColor(couleurFond);

            // Ajoutez le TextView au layout
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 10, 10, 10); // Ajustez les marges selon vos besoins
            lettreTextView.setLayoutParams(params);

            layoute.addView(lettreTextView);
        }
    }

    private void victoire(int cpt, String mot){
        Intent intent = new Intent(MainActivity.this, EndActivity.class);
        intent.putExtra("nombreEssais", cpt); // Remplacez nombreEssais par la variable réelle
        intent.putExtra("motATrouver", mot); // Remplacez motATrouver par la variable réelle
        startActivity(intent);
    }
    private void defaite(String mot){
        Intent intent = new Intent(MainActivity.this, Defeat.class);
        intent.putExtra("motATrouver", mot); // Remplacez motATrouver par la variable réelle
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout  propo1 = findViewById(R.id.Proposition1);
        LinearLayout  propo2 = findViewById(R.id.Proposition2);
        LinearLayout  propo3 = findViewById(R.id.Proposition3);
        LinearLayout  propo4 = findViewById(R.id.Proposition4);
        LinearLayout  propo5 = findViewById(R.id.Proposition5);
        LinearLayout  propo6 = findViewById(R.id.Proposition6);
        EditText editText = findViewById(R.id.EntreeMots);
        // mot mystère
        Random random = new Random();

        // Obtenez un nombre aléatoire entre 2 (inclus) et 10 (exclus)
        int nombreAleatoire = random.nextInt(8) + 2;
        String mot = selectRandom(nombreAleatoire);
        // faire fonction de check in des lettres pour afficher en conséquence
        // du toUpperCase
        // Faire Char par char et attribué une value en mode matrice du mot -> couleur selon 0 1 2
        

        // faudra limiter selon le mot de la partie
        int maxLength = mot.length();
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(maxLength)});

        // Compteur de la proposition combien on est pour switch
        final int[] cpt = {1};
        Button button = findViewById(R.id.Propose);
        button.setOnClickListener(v -> {
            if(editText.getText().toString().length()== mot.length() && rechercherMotDansFichier(editText.getText().toString())) {
                switch (cpt[0]) {

                    case 1:
                        afficherLettresAvecCouleur(propo1, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;

                    case 2:
                        afficherLettresAvecCouleur(propo2, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;

                    case 3:
                        afficherLettresAvecCouleur(propo3, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;
                    case 4:
                        afficherLettresAvecCouleur(propo4, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;
                    case 5:
                        afficherLettresAvecCouleur(propo5, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;
                    case 6:
                        afficherLettresAvecCouleur(propo6, editText.getText().toString(), mot);
                        cpt[0]++;
                        break;
                    default:
                        break;
                }
                if(editText.getText().toString().equals(mot) && cpt[0] <= 7){
                    victoire(cpt[0],mot);
                    button.setEnabled(false);
                    cpt[0] = 8;
                } else if ((!editText.getText().toString().equals(mot)) && cpt[0] == 7) {
                    defaite(mot);
                    button.setEnabled(false);
                    cpt[0] = 8;
                }
                editText.setText((""));
            }
        });


    }
}