package com.sourav.verthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

public class LightActivity extends AppCompatActivity {
    ImageView aboutback3;
    private DatabaseReference database;
    private TextView lightstatus;
    TextView hostIdview;
    private  SwitchCompat lightSwitch1,lightSwitch2,lightSwitch3,lightSwitch4;



    private TextView Lighton;
    private int lightOnCount = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_light);

        hostIdview=findViewById(R.id.hostIdview);
        aboutback3=findViewById(R.id.aboutback3);
        Intent intent2 = getIntent();
        String host_id=intent2.getStringExtra("host_id1");
        hostIdview.setText("Host_ID:"+host_id);

        if (host_id == null) {
            Toast.makeText(this, "Error: Host ID is missing!", Toast.LENGTH_LONG).show();;
            finish();
            return;
        }

        database = FirebaseDatabase.getInstance("https://verthome-f7b0d-default-rtdb.firebaseio.com")
                .getReference("devices").child(host_id).child("relays");

         lightSwitch1 = findViewById(R.id.lightSwitch1);
         lightSwitch2 = findViewById(R.id.lightSwitch2);
        lightSwitch3 = findViewById(R.id.lightSwitch3);
         lightSwitch4 = findViewById(R.id.lightSwitch4);
        Lighton= findViewById(R.id.Lighton);
        lightSwitch1.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        lightSwitch2.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        lightSwitch3.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        lightSwitch4.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        setupSwitch(lightSwitch1, "relay1");
        setupSwitch(lightSwitch2, "relay2");
        fetchRelayState();

        aboutback3.setOnClickListener(v -> {

            finish();
        });

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
                    Boolean relay1State = snapshot.child("relay1").getValue(Boolean.class);
                    Boolean relay2State = snapshot.child("relay2").getValue(Boolean.class);
                    if (relay1State != null) {
                        lightSwitch1.setChecked(relay1State);
                    }
                    if (relay2State != null) {
                        lightSwitch2.setChecked(relay2State);
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
            lightOnCount++;
        } else {
            lightOnCount--;
        }
        Lighton.setText("Light On:" + lightOnCount);
    }
}