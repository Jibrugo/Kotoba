package com.example.testomg;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.text.InputFilter;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {





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
            lettreTextView.setTextSize(20); // Ajustez la taille de la police selon vos besoins

            // Appliquez la couleur de fond en fonction de la lettre et de la règle que vous avez définie
            int couleurFond = checkMot(lettreMyst,lettrePropo, motMystere);
            lettreTextView.setBackgroundColor(couleurFond);

            // Ajoutez le TextView au layout
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 5, 5, 5); // Ajustez les marges selon vos besoins
            lettreTextView.setLayoutParams(params);

            layoute.addView(lettreTextView);
        }
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
        String mot = "LAPIN";
        // faire fonction de check in des lettres pour afficher en conséquence
        // du toUpperCase
        // Faire Char par char et attribué une value en mode matrice du mot -> couleur selon 0 1 2
        


        // faudra limiter selon le mot de la partie
        int maxLength = 5;
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
                editText.setText((""));
            }
        });


    }
}