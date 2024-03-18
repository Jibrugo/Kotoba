package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;


public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Lier le layout XML à cette activité

        // affiche un toast
        // Toast.makeText(login.this, "Page login", Toast.LENGTH_SHORT).show();

        View toolbar = findViewById(R.id.toolbar);
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setupToolbarActions(toolbar);
    }
}