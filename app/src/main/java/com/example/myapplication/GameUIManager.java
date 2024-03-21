package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;

interface MyFunction {
    void execute();
}
public class GameUIManager {
    private final game activity;
    private Context context;
    private WordManager wordManager;
    private String gameMode;
    private LinearLayout[] propositions;
    public EditText editText;
    private Button answerBtn;
    private Button nextWordBtn;
    private int indicePartie;
    private int indiceGuess;
    private String[] secretWords;
    private String secretWord;

    private Audio audio;

    public GameUIManager(game activity, WordManager wordManager, String gameMode) {
        this.activity = activity;
        this.wordManager = wordManager;

        this.propositions = initializePropositionLayouts();
        this.editText = activity.findViewById(R.id.EntreeMots);
        this.answerBtn = activity.findViewById(R.id.Propose);


        this.nextWordBtn = activity.findViewById(R.id.motSuivantButton);
        this.nextWordBtn.setVisibility(View.INVISIBLE);

        this.indicePartie = 0;
        this.indiceGuess = 0;
        this.gameMode = gameMode;

        this.audio = new Audio(this.activity);
    }

    public void adjustTextSizeBasedOnScreen(TextView textView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        float textSize = screenWidth / activity.getResources().getDisplayMetrics().density / 10;
        textView.setTextSize(textSize);
    }

    public void afficherLettresAvecCouleur(int indexLayout, String motPropose, String motMystere) {
        this.propositions[indexLayout].removeAllViews();


        for (int i = 0; i < motPropose.length(); i++) {
            char lettrePropo = Character.toUpperCase(motPropose.charAt(i));
            char lettreMyst = Character.toUpperCase(motMystere.charAt(i));
            TextView lettreTextView = new TextView(activity);
            lettreTextView.setText(String.valueOf(lettrePropo));
            lettreTextView.setTypeface(null, Typeface.BOLD);
            lettreTextView.setTextColor(Color.WHITE);
            lettreTextView.setGravity(Gravity.CENTER);
            adjustTextSizeBasedOnScreen(lettreTextView);

            int couleurFond = activity.wordManager.compareWord(lettreMyst, lettrePropo, motMystere);
            play_sound(couleurFond);



            GradientDrawable drawable = new GradientDrawable();
            float cornerRadius = 8; // Récupérer la valeur depuis les dimensions
            drawable.setCornerRadius(cornerRadius);
            drawable.setColor(couleurFond);
            lettreTextView.setBackground(drawable);



            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            params.setMargins(10, 10, 10, 10);
            lettreTextView.setLayoutParams(params);
            this.propositions[indexLayout].addView(lettreTextView);
            this.propositions[indexLayout].setOrientation(LinearLayout.HORIZONTAL);
            this.propositions[indexLayout].setGravity(Gravity.CENTER);
        }

        boolean find = this.wordManager.areWordsEquals(motPropose,motMystere);

        if (find) {
            audio.play(R.raw.motus);
        }
    }

    public void afficherCasesVides(int indexLayout, int nombreLettresAttendues) {
        this.propositions[indexLayout].removeAllViews();
        for (int i = 0; i < nombreLettresAttendues; i++) {
            TextView emptyTextView = new TextView(activity);
            emptyTextView.setText(" ");

            GradientDrawable drawable = new GradientDrawable();

            // Définir le rayon des coins arrondis
            float cornerRadius = 8; // Récupérer la valeur depuis les dimensions
            drawable.setCornerRadius(cornerRadius);
            drawable.setColor(Color.GRAY);
            emptyTextView.setBackground(drawable);

//            emptyTextView.setBackgroundResource(R.drawable.letter_style);
//            emptyTextView.setBackgroundColor(Color.GRAY);


            emptyTextView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            params.setMargins(10, 10, 10, 10);

            emptyTextView.setLayoutParams(params);

            this.propositions[indexLayout].addView(emptyTextView);

            this.propositions[indexLayout].setOrientation(LinearLayout.HORIZONTAL);

            this.propositions[indexLayout].setGravity(Gravity.CENTER);
        }
    }
    /////////////////////////
    private LinearLayout[] initializePropositionLayouts() {
        LinearLayout[] propositions = new LinearLayout[6];
        propositions[0] = activity.findViewById(R.id.Proposition1);
        propositions[1] = activity.findViewById(R.id.Proposition2);
        propositions[2] = activity.findViewById(R.id.Proposition3);
        propositions[3] = activity.findViewById(R.id.Proposition4);
        propositions[4] = activity.findViewById(R.id.Proposition5);
        propositions[5] = activity.findViewById(R.id.Proposition6);
        return propositions;
    }

    private void setEditTextFilter(int maxLength) {
        this.editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(maxLength)});
    }

    public void displayEmptyGameGrid(String mot) {
        for (int i = 0; i < propositions.length; i++) {
            propositions[i].removeAllViews();
            afficherCasesVides(i, mot.length());
        }
    }

    public String getProposedWord() {
        return editText.getText().toString();
    }

    public void setAnwserBtnClick(MyFunction function) {
        this.answerBtn.setOnClickListener(v -> {
            function.execute();
        });
    }

    public void setNextBtnClick(MyFunction function) {
        this.nextWordBtn.setOnClickListener(v -> {
            function.execute();
        });
    }

    public void setEditTextKeyListener(MyFunction function) {

        this.editText.setOnKeyListener((view, keyCode, keyEvent) -> {
            char unicodeChar = (char) keyEvent.getUnicodeChar();

            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                function.execute();
                return true;
            } else {
                return false;
            }

        });

    }

    public void setSecretWord(String word) {
        this.secretWord = word;
        setEditTextFilter(secretWord.length());
        Log.d("secretWord",this.secretWord);
    }

    public void emptyTextInput() {
        this.editText.setText("");
    }

    public void resetGameUi() {
        this.editText.setText("");
        displayEmptyGameGrid(this.secretWord);
        setEditTextFilter(this.secretWord.length());
    }

    public void setAnwserBtnVisibility(boolean visible) {
        if (visible)
        {
            this.answerBtn.setVisibility(View.VISIBLE);
        } else {
            this.answerBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void setNextBtnVisibility(boolean visible) {
        if (visible)
        {
            this.nextWordBtn.setVisibility(View.VISIBLE);
        } else {
            this.nextWordBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void setAnwserBtnEnabled(boolean enabled) {
        this.answerBtn.setEnabled(enabled);
    }

    public void setNextBtnEnabled(boolean enabled) {
        this.answerBtn.setEnabled(enabled);
    }

    private void play_sound(int letter_color) {

        String color = String.valueOf(letter_color);
        String green = String.valueOf(ContextCompat.getColor(activity, R.color.VertMoyen));
        String yellow = String.valueOf(ContextCompat.getColor(activity, R.color.JauneMoyen));
        String red = String.valueOf(ContextCompat.getColor(activity, R.color.RougeMoyen));

        Log.d("Couleur " , color);
        Log.d("Couleur " , "Green " + green);
        Log.d("Couleur " , "Jaune " + yellow);
        Log.d("Couleur " , "Rouge " + red);

        if (color.equals(green)) {
            audio.play(R.raw.good);
            Log.d("Sound" , "GOOD");
        } else if (color.equals(yellow))  {
            audio.play(R.raw.bad);
            Log.d("Sound" , "SO SO");
        } else if (color.equals(red))  {
            audio.play(R.raw.notfound);
            Log.d("Sound" , "BAD");
        }

    }


}

