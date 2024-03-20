package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class game extends AppCompatActivity {

    private GameUIManager uiManager;
    public WordManager wordManager;

    public String[] propositions;
    public String[] secretWords;
    public String secretWord;

    private int indexGuess;
    private int indexWordl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        String gameMode = "Random";

        this.wordManager = new WordManager(this);

        this.uiManager = new GameUIManager(this,this.wordManager,gameMode);

        this.propositions = new String[6];
        this.secretWords = this.wordManager.getMultipleRandom(4,6,5);

        for (int i = 0; i < secretWords.length; i++) {
            Log.d("secret "+i+" : ",this.secretWords[i]);
        }

        Log.d("secretWords",String.join(" , ",this.secretWords));
        this.updateSecretWord();



        uiManager.displayEmptyGameGrid(this.secretWord);
        uiManager.setSecretWord(this.secretWord);
        uiManager.setAnwserBtnClick(this::process_answer);
        uiManager.setEditTextKeyListener(this::process_answer);
        uiManager.setNextBtnClick(this::start_next_word);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


//        displayToast(textBeforeCursor);

    }

    protected void resetGame() {
        this.indexGuess = 0;
        this.indexWordl = 0;
    }

    private void updateSecretWord() {
        this.secretWord = this.secretWords[this.indexWordl];
    }

    public void toast() {
        displayToast("Test");
    }
    protected void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void process_answer() {
        // this.uiManager.editText.requestFocus();
        // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        String motPropose = this.uiManager.getProposedWord();

        if(motPropose.length() == secretWord.length())
        {
            if (this.wordManager.wordExists(motPropose)) {
                uiManager.emptyTextInput();
                uiManager.afficherLettresAvecCouleur(this.indexGuess, motPropose, secretWord);

                // TODO : Verifier si victoire

                boolean isEnd = checkEnd(motPropose, secretWord);

                if (!isEnd) {
                    if (this.indexGuess < 5) {
//                        Log.d("GameUIManager", "Aff +1");
//                        String editedWord = this.wordManager.keepCorrectLetters(motPropose, secretWord);
//                        uiManager.afficherLettresAvecCouleur(this.indexGuess + 1, editedWord, secretWord);

                        this.indexGuess++;
                    }
                }

                Log.d("GameUIManager", String.valueOf(this.indexGuess));
            } else {
                Toast.makeText(this, "Ce mot ne semble pas exister", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ce mot n'est pas de la bonne taille", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkEnd(String proposition, String motMystere) {
        boolean find = this.wordManager.areWordsEquals(proposition,motMystere);

        if (find && (indexGuess <= 5)) {
            // Si le mot à été trouvé et que ça à été fait en moins de 6 essais
            // TODO : Incrémenter le compteur de victoire
            this.uiManager.setNextBtnVisibility(true);
        } else if (!find && (indexGuess == 5)) {
            // Si le mot n'a pas été trouvé et qu'il n'y a plus d'essais possible
            this.uiManager.setNextBtnVisibility(true);
        }

        return find;
    }


    protected void start_next_word() {
        displayToast("Nouvelle partie");
        this.indexGuess = 0;
        this.indexWordl++;
        updateSecretWord();
        this.uiManager.setSecretWord(this.secretWord);
        this.uiManager.setNextBtnVisibility(false);
        this.uiManager.resetGameUi();
    }

}

