package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class ToolbarManager implements View.OnClickListener {
    private Context mContext;
    private ImageButton homeButton;
    private ImageButton achievementButton;
    private ImageButton profileButton;
    private ImageButton moreButton;

    public ToolbarManager(Context context) {
        this.mContext = context;
    }

    public void setupToolbarActions(View toolbar) {
        this.homeButton = toolbar.findViewById(R.id.home_button);
        this.achievementButton = toolbar.findViewById(R.id.achivement_button);
        this.profileButton = toolbar.findViewById(R.id.profile_button);
        this.moreButton = toolbar.findViewById(R.id.more_button);

        homeButton.setOnClickListener(this);
        achievementButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
        moreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
          String s = view.getResources().getResourceName(view.getId());
          Log.d("test",s);
            if (view == homeButton) {
                // Action à effectuer lorsque le bouton "Home" est cliqué
                // showToast("Home button clicked");
                Intent intent = new Intent(mContext, home.class);
                mContext.startActivity(intent);
            } else if (view == achievementButton) {
                // Action à effectuer lorsque le bouton "Achievement" est cliqué
                // showToast("Achievement button clicked");
            } else if (view == profileButton) {
                // Action à effectuer lorsque le bouton "Profile" est cliqué
                // showToast("Profile button clicked");
                Intent intent = new Intent(mContext, login.class);
                mContext.startActivity(intent);
            } else if (view == moreButton) {
                // Action à effectuer lorsque le bouton "More" est cliqué
                // showToast("More button clicked");
                Intent intent = new Intent(mContext, game.class);
                mContext.startActivity(intent);
            }
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}

