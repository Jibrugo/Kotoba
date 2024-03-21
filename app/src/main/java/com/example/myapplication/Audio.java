package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Audio {
    private MediaPlayer mediaPlayer;
    private Context context;
    private List<Integer> queue;
    private boolean isPlaying = false;

    public Audio(Context context) {
        this.context = context;
        this.queue = new ArrayList<>();
    }

    public void play(int resourceId) {
        // Ajouter la ressource à la file d'attente
        queue.add(resourceId);

        // S'il n'y a pas de lecture en cours, commencer la lecture
        if (!isPlaying) {
            playNext();
        }
    }

    private void playNext() {
        // Vérifier s'il y a des éléments dans la file d'attente
        if (!queue.isEmpty()) {
            // Récupérer la première ressource de la file d'attente
            int resourceId = queue.get(0);
            queue.remove(0);

            // Lire la ressource
            try {
                mediaPlayer = MediaPlayer.create(context, resourceId);
                mediaPlayer.setOnCompletionListener(mp -> {
                    // Libérer les ressources du lecteur MediaPlayer
                    mediaPlayer.release();
                    mediaPlayer = null;

                    // Passer au prochain son dans la file d'attente
                    playNext();
                });

                PlaybackParams params = new PlaybackParams();
                params.setSpeed(1.5f); // 1.5 fois la vitesse normale
                mediaPlayer.setPlaybackParams(params);


                mediaPlayer.start();
                isPlaying = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Il n'y a plus de sons dans la file d'attente, marquer comme ne jouant plus
            isPlaying = false;
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        queue.clear(); // Vider la file d'attente
        isPlaying = false;
    }
}
