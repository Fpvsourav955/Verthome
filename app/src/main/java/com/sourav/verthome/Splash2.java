package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));

        setContentView(R.layout.activity_splash2);
        Intent intent2 = getIntent();
        String host_id=intent2.getStringExtra("host_id1");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash2.this, LightActivity.class);
                intent.putExtra("host_id1",host_id);
                startActivity(intent);
                finish();
            }
        }, 1500);


    }
}