package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
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

    public GameUIManager(game activity, WordManager wordManager, String gameMode) {
        this.activity = activity;
        activity.displayToast("Hello");
        this.wordManager = wordManager;

        this.propositions = initializePropositionLayouts();
        this.editText = activity.findViewById(R.id.EntreeMots);
        this.answerBtn = activity.findViewById(R.id.Propose);
//        this.editText.setVisibility(View.INVISIBLE);

        this.nextWordBtn = activity.findViewById(R.id.motSuivantButton);
        this.nextWordBtn.setVisibility(View.INVISIBLE);

        this.indicePartie = 0;
        this.indiceGuess = 0;
        this.gameMode = gameMode;
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
            lettreTextView.setTextColor(Color.BLACK);
            lettreTextView.setGravity(Gravity.CENTER);
            adjustTextSizeBasedOnScreen(lettreTextView);

            int couleurFond = activity.wordManager.compareWord(lettreMyst, lettrePropo, motMystere);

            lettreTextView.setBackgroundColor(couleurFond);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            params.setMargins(10, 3, 10, 3);
            lettreTextView.setLayoutParams(params);
            this.propositions[indexLayout].addView(lettreTextView);
        }
    }

    public void afficherCasesVides(int indexLayout, int nombreLettresAttendues) {
        this.propositions[indexLayout].removeAllViews();
        for (int i = 0; i < nombreLettresAttendues; i++) {
            TextView emptyTextView = new TextView(activity);
            emptyTextView.setText(" ");
            emptyTextView.setBackgroundColor(Color.GRAY);
            emptyTextView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    100,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.setMargins(10, 3, 10, 3);
            emptyTextView.setLayoutParams(params);
            this.propositions[indexLayout].addView(emptyTextView);
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
//            Log.d("keyDown","Key : "+unicodeChar);
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




}

