package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {
  ImageView verthomeback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_about_us);
        verthomeback= findViewById(R.id.about2back);
        verthomeback.setOnClickListener(v-> {
            Intent intent = new Intent(AboutUs.this, RealTime.class);
            startActivity(intent);
        });

    }
};