package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TvActivity extends AppCompatActivity {
    ImageView aboutback8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_tv);
         aboutback8=findViewById(R.id.aboutback8);
         aboutback8.setOnClickListener(v -> {
             Intent intent = new Intent(TvActivity.this, UnderHome.class);
             startActivity(intent);
         });

    }
}