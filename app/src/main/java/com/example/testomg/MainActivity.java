package com.example.testomg;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import java.util.Random;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.text.InputFilter;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    // permet de vérifier l'existance du mot
    public boolean rechercherMotDansFichier(String motRecherche) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    // Ouverture du fichier
                    new InputStreamReader(getAssets().open("dicoscrabble.txt")));

            String mLine;
            // lecture toutes les lignes
            while ((mLine = reader.readLine()) != null) {
                if(mLine.equals(motRecherche)){
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // idem 2ème fichier
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
                return ContextCompat.getColor(this, R.color.VertMoyen);
            } else if (motMyst.contains(String.valueOf(lettreProposee))) {
                // Lettre mal placée (couleur orange)
                return ContextCompat.getColor(this, R.color.JauneMoyen);
            } else {
                // Lettre absente (couleur rouge)
                return ContextCompat.getColor(this, R.color.RougeMoyen);
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
            lettreTextView.setTypeface(null, Typeface.BOLD);
            lettreTextView.setTextColor(Color.WHITE);
            lettreTextView.setGravity(Gravity.CENTER);
            adjustTextSizeBasedOnScreen(lettreTextView); // Ajustez la taille de la police selon vos besoins
            // Appliquez la couleur de fond en fonction de la lettre et de la règle que vous avez définie
            int couleurFond = checkMot(lettreMyst,lettrePropo, motMystere);
            lettreTextView.setBackgroundColor(couleurFond);

            // Ajoutez le TextView au layout
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100, // Largeur de la vue vide (vous pouvez ajuster selon vos besoins)
                    ViewGroup.LayoutParams.MATCH_PARENT // Hauteur de la vue vide (correspond à celle du conteneur)
            );
            params.setMargins(10, 10, 10, 10); // Ajustez les marges selon vos besoins
            lettreTextView.setLayoutParams(params);

            layoute.addView(lettreTextView);
        }
        layoute.setOrientation(LinearLayout.HORIZONTAL);
        layoute.setGravity(Gravity.CENTER);
    }

    // Récupère le mot propose pour conserver un affichage des lettres qui sont bonnes dans l'affichage de proposition suivante
    private String motmodif(String motPropose,String motMystere){

        char[] tableauDeChar = motPropose.toCharArray();
        char[] tableauDeReponse = motMystere.toCharArray();
        for(int i = 0; i < motPropose.length(); i++) {
            if ( tableauDeChar[i] != tableauDeReponse[i]) {
                tableauDeChar[i] = ' ';
            }
        }
        String motmodifier = new String(tableauDeChar);
        return motmodifier;
    }

    // Fonction qui lance l'affichage de l'écran de fin
    // paramètres : cptotaux le nombre d'essais au total de toute la partie, mots la liste des mots à deviner, yesOrNo un tableau qui représente si les mots ont été trouvés ou non

    private void victoire(int cptotaux, String[] mots, int[] yesOrNo){
        Intent intent = new Intent(MainActivity.this, EndActivity.class);
        intent.putExtra("nombreEssaisTotaux", cptotaux);
        intent.putExtra("listMots", mots); //
        intent.putExtra("listValidOrNot", yesOrNo);
        startActivity(intent);

    }

    // Préaffichage de proposition à vide
    private void afficherCasesVides(LinearLayout layout, int nombreLettresAttendues) {
        layout.removeAllViews(); // Effacer les vues précédentes

        // Ajouter des vues vides pour représenter les cases vides
        for (int i = 0; i < nombreLettresAttendues; i++) {
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText(" "); // Caractère spécial vide
            emptyTextView.setBackgroundColor(Color.GRAY); // Fond gris
            emptyTextView.setGravity(Gravity.CENTER); // Centrer le texte
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100,// Largeur de la vue vide (vous pouvez ajuster selon vos besoins)
                    ViewGroup.LayoutParams.MATCH_PARENT // Hauteur de la vue vide (correspond à celle du conteneur)
            );
            params.setMargins(10, 10, 10, 10); // Ajuster les marges selon vos besoins
            emptyTextView.setLayoutParams(params);
            layout.addView(emptyTextView);
        }
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
    }

    // mode déterministe de génération des mots avec des tailles en entrée
    private String[] deterministeMots(int[] longueurs){

        // à relier avec la bdd pour la suite
        String[] mots = new String[longueurs.length];

        for (int i = 0; i < longueurs.length; i++) {
            mots[i] = selectRandom(longueurs[i]);

        }
        return mots;
    }

    // mode random : génération de tailles aléatoire et
    private String[] randomMots(){

        Random random = new Random();
        // Obtenez un nombre aléatoire entre 2 (inclus) et 10 (exclus)
        String[] mots = {selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2)};
        return mots;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // prépare l'affichage des 6 propositions
        setContentView(R.layout.activity_main);

        LinearLayout propo1 = findViewById(R.id.Proposition1);
        LinearLayout propo2 = findViewById(R.id.Proposition2);
        LinearLayout propo3 = findViewById(R.id.Proposition3);
        LinearLayout propo4 = findViewById(R.id.Proposition4);
        LinearLayout propo5 = findViewById(R.id.Proposition5);
        LinearLayout propo6 = findViewById(R.id.Proposition6);
        EditText editText = findViewById(R.id.EntreeMots);

        // mode qui sera modifier selon l'endroit où on lance la partie
        String mode = "random";

        final String[] mot;
        if (mode.equals("random")) {
            mot = randomMots();
        } else{
            // pour l'instant ça en attendant
            mot = randomMots();
        }

        final int[] indicePartie = {0};


        // Mets des cases à vides grises pour indiquer la taille du mot

        afficherCasesVides(propo1, mot[indicePartie[0]].length());
        afficherCasesVides(propo2, mot[indicePartie[0]].length());
        afficherCasesVides(propo3, mot[indicePartie[0]].length());
        afficherCasesVides(propo4, mot[indicePartie[0]].length());
        afficherCasesVides(propo5, mot[indicePartie[0]].length());
        afficherCasesVides(propo6, mot[indicePartie[0]].length());

        // limiter selon le mot de la manche
        final int[] maxLength = {mot[indicePartie[0]].length()};
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(maxLength[0])});


        // Nombre mots partie et les trouvés.
        final int[] motsPartie = {0};
        final int[] motsTotal = {2};
        final int[] motsDecouverts = {0};
        // mots trouvés ou non
        final int[] tableauDeInt = {0, 0, 0, 0, 0};
        // les mots de la partie
        final String[] tableauDeMots = {"","","","",""};
        tableauDeMots[indicePartie[0]] = mot[indicePartie[0]];
        // Compteur de la proposition combien on est pour switch
        final int[] cpt = {1};

        final int[] cptotaux = {0};

        // Bouton pour passer au mot suivant
        Button motSuivantButton = findViewById(R.id.motSuivantButton);
        motSuivantButton.setVisibility(View.INVISIBLE); // Masquer le bouton au début

        // Bouton proposer
        Button button = findViewById(R.id.Propose);
        button.setOnClickListener(v -> {
            if(editText.getText().toString().length()== mot[indicePartie[0]].length() && rechercherMotDansFichier(editText.getText().toString())) {
                switch (cpt[0]) {

                    case 1:
                        // affichage résultat de la proposition
                        afficherLettresAvecCouleur(propo1, editText.getText().toString(), mot[indicePartie[0]]);
                        // affichage des bonnes lettres de la proposition
                        afficherLettresAvecCouleur(propo2, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;

                    case 2:
                        afficherLettresAvecCouleur(propo2, editText.getText().toString(), mot[indicePartie[0]]);
                        afficherLettresAvecCouleur(propo3, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;

                    case 3:
                        afficherLettresAvecCouleur(propo3, editText.getText().toString(), mot[indicePartie[0]]);
                        afficherLettresAvecCouleur(propo4, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;
                    case 4:
                        afficherLettresAvecCouleur(propo4, editText.getText().toString(), mot[indicePartie[0]]);
                        afficherLettresAvecCouleur(propo5, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;
                    case 5:
                        afficherLettresAvecCouleur(propo5, editText.getText().toString(), mot[indicePartie[0]]);
                        afficherLettresAvecCouleur(propo6, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;
                    case 6:
                        afficherLettresAvecCouleur(propo6, editText.getText().toString(), mot[indicePartie[0]]);
                        cpt[0]++;
                        break;
                    default:
                        break;
                }

                // Fin de partie
                if(editText.getText().toString().equals(mot[indicePartie[0]]) && cpt[indicePartie[0]] <= 7){

                    motsDecouverts[0]++;
                    if (motsDecouverts[0] < motsTotal[0]) {
                        motSuivantButton.setVisibility(View.VISIBLE);
                    }
                    tableauDeInt[motsPartie[0]] = 1;
                    tableauDeMots[motsPartie[0]] = mot[indicePartie[0]];

                    motsPartie[0]++;
                    button.setEnabled(false);
                    cpt[0] = 8;
                    // Fin de  partie
                } else if ((!editText.getText().toString().equals(mot[indicePartie[0]])) && cpt[0] == 7) {
                    tableauDeMots[motsPartie[0]] = mot[indicePartie[0]];
                    motsPartie[0]++;
                    button.setEnabled(false);
                    cpt[0] = 8;
                    if (motsDecouverts[0] < motsTotal[0]) {
                        motSuivantButton.setVisibility(View.VISIBLE);
                    }
                }
                if (motsPartie[0] == motsTotal[0]) {
                    victoire(cptotaux[0],tableauDeMots,tableauDeInt );
                }
                editText.setText((""));
            }
        });

        // La validation via la touche entrée du clavier

        editText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && ( keyCode == KeyEvent.KEYCODE_ENTER)) {
                if(editText.getText().toString().length()== mot[indicePartie[0]].length() && rechercherMotDansFichier(editText.getText().toString())) {
                    switch (cpt[0]) {
                        case 1:
                            afficherLettresAvecCouleur(propo1, editText.getText().toString(), mot[indicePartie[0]]);
                            afficherLettresAvecCouleur(propo2, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        case 2:
                            afficherLettresAvecCouleur(propo2, editText.getText().toString(), mot[indicePartie[0]]);
                            afficherLettresAvecCouleur(propo3, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        case 3:
                            afficherLettresAvecCouleur(propo3, editText.getText().toString(), mot[indicePartie[0]]);
                            afficherLettresAvecCouleur(propo4, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        case 4:
                            afficherLettresAvecCouleur(propo4, editText.getText().toString(), mot[indicePartie[0]]);
                            afficherLettresAvecCouleur(propo5, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        case 5:
                            afficherLettresAvecCouleur(propo5, editText.getText().toString(), mot[indicePartie[0]]);
                            afficherLettresAvecCouleur(propo6, motmodif(editText.getText().toString(),mot[indicePartie[0]]), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        case 6:
                            afficherLettresAvecCouleur(propo6, editText.getText().toString(), mot[indicePartie[0]]);
                            cpt[0]++;
                            break;
                        default:
                            break;
                    }
                    if(editText.getText().toString().equals(mot[indicePartie[0]]) && cpt[0] <= 7){
                        motsDecouverts[0]++;
                        if (motsDecouverts[0] < motsTotal[0]) {
                            motSuivantButton.setVisibility(View.VISIBLE);
                        }
                        tableauDeInt[motsPartie[0]] = 1;
                        tableauDeMots[motsPartie[0]] = mot[indicePartie[0]];
                        motsPartie[0]++;
                        button.setEnabled(false);
                        cpt[0] = 8;
                    } else if ((!editText.getText().toString().equals(mot[indicePartie[0]])) && cpt[0] == 7) {
                        tableauDeMots[motsPartie[0]] = mot[indicePartie[0]];
                        motsPartie[0]++;
                        button.setEnabled(false);
                        cpt[0] = 8;
                        if (motsDecouverts[0] < motsTotal[0]) {
                            motSuivantButton.setVisibility(View.VISIBLE);
                        }
                    }

                    if (motsPartie[0] == motsTotal[0]) {
                        victoire(cptotaux[0],tableauDeMots,tableauDeInt );
                    }
                    editText.setText((""));
                }
                return true; // Indique que l'événement a été géré
            }
            return false; // Indique que l'événement n'a pas été géré
        });

        // mot suivant
        motSuivantButton.setOnClickListener(v -> {
            // Générer un nouveau mot mystère

            indicePartie[0] += 1;
            // Réinitialiser l'interface pour le nouveau mot
            // Effacer les vues des propositions précédentes
            propo1.removeAllViews();
            propo2.removeAllViews();
            propo3.removeAllViews();
            propo4.removeAllViews();
            propo5.removeAllViews();
            propo6.removeAllViews();

            afficherCasesVides(propo1, mot[indicePartie[0]].length());
            afficherCasesVides(propo2, mot[indicePartie[0]].length());
            afficherCasesVides(propo3, mot[indicePartie[0]].length());
            afficherCasesVides(propo4, mot[indicePartie[0]].length());
            afficherCasesVides(propo5, mot[indicePartie[0]].length());
            afficherCasesVides(propo6, mot[indicePartie[0]].length());
            maxLength[0] = mot[indicePartie[0]].length();
            editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(maxLength[0])});
            cptotaux[0] += cpt[0];
            // Réinitialiser le compteur de propositions
            cpt[0] = 1;

            // Réinitialiser le champ de saisie
            editText.setText("");

            // Réinitialiser le bouton "Proposer"
            button.setEnabled(true);

            // Masquer le bouton "Mot Suivant"
            motSuivantButton.setVisibility(View.INVISIBLE);
            // Logique pour passer au mot suivant
        });
    }
}