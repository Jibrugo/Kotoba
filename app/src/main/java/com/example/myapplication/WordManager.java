package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import android.graphics.Typeface;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordManager {

    private Context context;
    private BufferedReader reader;
    public WordManager(Context context) {
        this.context = context;
        this.reader = null;
    }

    /**
     * Fonction pour obtenir 1 mot en fournissant la taille de celui-ci
     * @param taille // nombre de caractère du mot
     * @return // le mot
     */
    public String getRandom(int taille) {
        //  BufferedReader reader = null;
        List<String> words = new ArrayList<>();
        try {

            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("mots_taille_" + taille + "_lettres.txt")));
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
        return " No FOUD ";
    }


    /**
     * Fonction pour obtenir une liste de mots aléatoire
     * @param minSize // La taille minimale des mots
     * @param maxSize // La taille maximale des mots
     * @param wordCount // Le nombre de mots à générer
     * @return // Une liste de mots
     */
    public String[] getMultipleRandom(int minSize, int maxSize, int wordCount){

        Random random = new Random();
        // Obtenez un nombre aléatoire entre 2 (inclus) et 10 (exclus)

        String[] mots = new String[wordCount];
        for (int i = 0; i< wordCount; i++) {
            int taille = random.nextInt(maxSize) + minSize;
            mots[i] = getRandom(taille);
            Log.d("words", String.valueOf(taille));
        }

        // String[] mots = {selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2),selectRandom(random.nextInt(8) + 2)};
        return mots;
    }

    public String[] getMultipleRandomForLevels(int [] tailles){
        int wordCount = tailles.length;
        String[] mots = new String[wordCount];
        for (int i = 0; i< wordCount; i++) {
            int taille = tailles[i];
            mots[i] = getRandom(taille);
            Log.d("words", String.valueOf(taille));
        }
        return mots;
    }

    /**
     * Fonction pour obtenir une liste de mots de façon déterministe (en utilisant une seed par exemple)
     * @param wordsSize // Liste d'entier avec les tailles des mots a génerer
     * @return Une liste de mots
     */
    private String[] getMultipleDeterministic(int[] wordsSize){
        // TODO : à relier avec la bdd pour la suite
        String[] mots = new String[wordsSize.length];

        for (int i = 0; i < wordsSize.length; i++) {
            mots[i] = getRandom(wordsSize[i]);
        }
        return mots;
    }

    /**
     * *********************************************  OLD NAME        rechercherMotDansFichier
     * Permet de rechercher un mot dans un fichier et de verifier si il existe
     * @param motRecherche
     * @return true si le mot existe sinon false
     */
    public boolean wordExists(String motRecherche) {
        //  BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("dicoscrabble.txt")));

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
                    new InputStreamReader(context.getAssets().open("dicoscrabble2.txt")));

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

    /**
     * ********************************************* OLD NAME        checkMot
     * Permet de comparer un mot proposé avec le mot mystère
     * @param lettreMystere
     * @param lettreProposee
     * @param motMyst
     * @return
     */
    public int compareWord(char lettreMystere, char lettreProposee, String motMyst) {
        // Comparer les lettres
        if (lettreProposee == lettreMystere) {
            // Lettre bien placée (couleur verte)
            return ContextCompat.getColor(context, R.color.VertMoyen);
        } else if (motMyst.contains(String.valueOf(lettreProposee))) {
            // Lettre mal placée (couleur orange)
//            return Color.YELLOW;
            return ContextCompat.getColor(context, R.color.JauneMoyen);
        } else {
            // Lettre absente (couleur rouge)
            return ContextCompat.getColor(context, R.color.RougeMoyen);
        }
    }

    /**
     * Remplace les lettres incorrecte du mot proposé par des espaces
     * @param motPropose Le mot proposé
     * @param motMystere
     * @return
     */
    public String keepCorrectLetters(String motPropose,String motMystere){

        char[] tableauDeChar = motPropose.toCharArray();
        char[] tableauDeReponse = motMystere.toCharArray();
        for(int i = 0; i < motPropose.length(); i++) {
            if ( tableauDeChar[i] != tableauDeReponse[i]) {
                tableauDeChar[i] = ' ';
            }
        }
        return new String(tableauDeChar);
    }

    public boolean areWordsEquals(String proposition, String motMystere) {
        return proposition.equals(motMystere);
    }

    public static JSONObject readJsonFileFromResources(Context context, int resourceId) {
        JSONObject jsonObject = null;
        try {
            // Ouvrir un flux vers le fichier JSON dans les ressources
            InputStream inputStream = context.getResources().openRawResource(resourceId);

            // Lire le contenu du fichier JSON en tant que chaîne
            String jsonString = readJsonStringFromInputStream(inputStream);

            // Parser la chaîne JSON en un objet JSONObject
            jsonObject = new JSONObject(jsonString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static String readJsonStringFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }


    public int[] getWordsSize(int gameId) {

        JSONObject jsonObject = readJsonFileFromResources(this.context, R.raw.test);

        try {

        JSONObject item = jsonObject.getJSONObject(String.valueOf(gameId));

        String sizeStr = item.getString("size");

        return Arrays.stream(sizeStr.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void test() {
        Toast.makeText(this.context, "Bouton cliqué", Toast.LENGTH_SHORT).show();
    }

    public int getWordsScore(int gameId) {
        JSONObject jsonObject = readJsonFileFromResources(this.context, R.raw.test);

        try {

            JSONObject item = jsonObject.getJSONObject(String.valueOf(gameId));

            Log.d("Json", String.valueOf(item));

            int score = item.getInt("score");
            return score;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
