package com.sourav.verthome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "VerThomePrefs";
    private static final String FIRST_LAUNCH_KEY = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));



        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH_KEY, true);

        if (!isFirstLaunch) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        ImageView getStartButton = findViewById(R.id.imageView4);
        getStartButton.setOnClickListener(v -> {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_LAUNCH_KEY, false);
            editor.apply();


            Intent intent = new Intent(MainActivity.this, LoginActivity .class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Welcome To VerThome", Toast.LENGTH_SHORT).show();
        });
    }

}
