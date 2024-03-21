package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;

public class milestone extends AppCompatImageButton {
    private int progress;
    private int btnId;

    @SuppressLint("ResourceType")
    public milestone(Context context, int id) {
        super(context);
        this.progress = 0;
        this.btnId = id;
        // Initialiser votre ImageButton ici, par exemple :
        this.setScaleType(ScaleType.CENTER_CROP);
        this.setBackgroundResource(R.drawable.score_0_5);
        this.setPaddingRelative(40,40,40,40);
        this.setImageResource(R.drawable.star_solid);
        setupClickListener();
    }

    public int getBtnId() {
        return this.btnId;
    }

    // Méthode pour définir l'image de manière dynamique
    public void setImage(int resId) {
        setImageResource(resId);
    }

    // Méthode pour définir la taille de l'image
    public void setImageSize(int width, int height) {
        setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    // Méthode pour définir le fond de l'image bouton
    public void setCustomBackground(int resId) {
        setBackgroundResource(resId);
    }

    // Méthode pour définir l'action de clic personnalisée
    public void setCustomClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

    private void setupClickListener() {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress++;

                if (progress>5) {
                    progress = 0;
                }

                set_progress(progress);

            }
        });
    }

    @SuppressLint("ResourceType")
    public void set_progress(int progress) {
        switch (progress) {
            case 0:
                this.setBackgroundResource(R.drawable.score_0_5);
            break;

            case 1:
                this.setBackgroundResource(R.drawable.score_1_5);
            break;

            case 2:
                this.setBackgroundResource(R.drawable.score_2_5);
            break;

            case 3:
                this.setBackgroundResource(R.drawable.score_3_5);
            break;

            case 4:
                this.setBackgroundResource(R.drawable.score_4_5);
            break;

            case 5:
                this.setBackgroundResource(R.drawable.score_5_5);
            break;

        }
    }
}
