package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class starting extends AppCompatActivity {


    private MediaPlayer mediaPlayer;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);

        // Créer une instance de MediaPlayer et charger le son à partir des ressources raw
        mediaPlayer = MediaPlayer.create(this, R.raw.lama);

        // Lancer la lecture du son
        mediaPlayer.start();

        // Initialiser un Handler
        handler = new Handler();

        // Lancer une tâche différée après 3 secondes
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Créer un Intent pour démarrer une nouvelle activité
                Intent intent = new Intent(starting.this, home.class);
                startActivity(intent);

                // Terminer l'activité en cours pour empêcher l'utilisateur de revenir en arrière avec le bouton retour
                finish();
            }
        }, 3000); // Délai de 3 secondes en millisecondes (3000 ms)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter et libérer le lecteur de média lorsqu'on n'en a plus besoin
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
