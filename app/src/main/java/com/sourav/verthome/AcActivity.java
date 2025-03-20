package com.sourav.verthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcActivity extends AppCompatActivity {
    ImageView aboutback6;
    ToggleButton toggle1, toggle2;
    TextView Acon,AcIdView;
    private DatabaseReference database;
    private int acOnCount=0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_ac);
        Acon=findViewById(R.id.Acon);
        AcIdView=findViewById(R.id.AcIdView);
        aboutback6 = findViewById(R.id.aboutback6);
        toggle1 = findViewById(R.id.toggle1);
        toggle2 = findViewById(R.id.toggle2);
        Intent intent2 = getIntent();
        String host_id=intent2.getStringExtra("host_id1");
        AcIdView.setText("Host_ID:"+host_id);
        if (host_id == null) {
            Toast.makeText(this, "Error: Host ID is missing!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        database = FirebaseDatabase.getInstance("https://verthome-f7b0d-default-rtdb.firebaseio.com")
                .getReference("devices").child(host_id).child("relays");

        toggle1.setOnCheckedChangeListener((buttonView, isChecked) -> updateAcCount(isChecked));
        toggle2.setOnCheckedChangeListener((buttonView, isChecked) -> updateAcCount(isChecked));
        setupToggleButton(toggle1, "relay5");
        setupToggleButton(toggle2, "relay6");
        fetchRelayState();

        aboutback6.setOnClickListener(view -> {
           finish();
        });
    }
    private void setupToggleButton(ToggleButton toggleButton, String relayKey) {
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            database.child(relayKey).setValue(isChecked);
            updateAcCount(isChecked);
        });
    }
    private void fetchRelayState() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean relay1State = snapshot.child("relay5").getValue(Boolean.class);
                    Boolean relay2State = snapshot.child("relay6").getValue(Boolean.class);
                    if (relay1State != null) {
                        toggle1.setChecked(relay1State);
                    }
                    if (relay2State != null) {
                        toggle2.setChecked(relay2State);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateAcCount(boolean isChecked) {
        if (isChecked) {
            acOnCount++;
        } else {
            acOnCount--;
        }
        Acon.setText("Fan On:" + acOnCount);
    }
}