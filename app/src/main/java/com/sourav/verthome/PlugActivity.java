package com.sourav.verthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlugActivity extends AppCompatActivity {
    private TextView PlugOn,plugidview;
    private  SwitchCompat switch1,switch2,switch3,switch4;
    private  int plugOn=0;
    private DatabaseReference database;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plug);
        ImageView aboutback5 = findViewById(R.id.aboutback5);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        PlugOn =findViewById(R.id.PlugOn);
        switch1 = findViewById(R.id.Switch1);
        switch2 = findViewById(R.id.Switch2);
        switch3 = findViewById(R.id.Switch3);
        switch4 = findViewById(R.id.Switch4);
        plugidview=findViewById(R.id.plugidview);
        Intent intent2 = getIntent();
        String host_id=intent2.getStringExtra("host_id1");
        plugidview.setText("Host_ID:"+host_id);
        if (host_id == null) {
            Toast.makeText(this, "Error: Host ID is missing!", Toast.LENGTH_LONG).show();
            Log.e("LightActivity", "host_id is NULL! Returning to previous activity.");
            finish(); // Close activity to prevent crash
            return;
        }
        database = FirebaseDatabase.getInstance("https://verthome-f7b0d-default-rtdb.firebaseio.com")
                .getReference("devices").child(host_id).child("relays");

        aboutback5.setOnClickListener(view -> {
           finish();
        });
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        switch4.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        setupSwitch(switch1, "relay4");
        fetchRelayState();
    }
    private void setupSwitch(SwitchCompat lightSwitch, String relayKey) {
        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            database.child(relayKey).setValue(isChecked);
            updateLightCount(isChecked);
        });
    }
    private void fetchRelayState() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean relay1State = snapshot.child("relay4").getValue(Boolean.class);
                    if (relay1State != null) {
                        switch1.setChecked(relay1State);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LightActivity", "Failed to read relay state", error.toException());
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void updateLightCount(boolean isChecked) {
        if (isChecked) {
            plugOn++;
        } else {
            plugOn--;
        }
        PlugOn.setText("Plug On:" + plugOn);
    }
}