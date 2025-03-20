package com.sourav.verthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FanActivity extends AppCompatActivity {
    private TextView Fanon;
    private int fanOnCount = 0;
    SeekBar seekBar1,seekBar2,seekBar3;
    private DatabaseReference database;
    TextView fan1per,fan2per,fan3per,fanidview;
    private  ToggleButton toggle1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_fan);
        ImageView aboutback6 = findViewById(R.id.aboutback6);
         toggle1 = findViewById(R.id.toggle1);
        ToggleButton toggle2 = findViewById(R.id.toggle2);
        ToggleButton toggle3 = findViewById(R.id.toggle3);
        seekBar1=findViewById(R.id.seekbar1);
        seekBar2=findViewById(R.id.seekbar2);
        seekBar3=findViewById(R.id.seekbar3);
        fan1per=findViewById(R.id.fan1per);
        fan2per=findViewById(R.id.fan2per);
        fan3per=findViewById(R.id.fan3per);
        fanidview=findViewById(R.id.fanidview);
        Fanon=findViewById(R.id.Fanon);
        Intent intent2 = getIntent();
        String host_id=intent2.getStringExtra("host_id1");
        fanidview.setText("Host_ID:"+host_id);
        if (host_id == null){
            Toast.makeText(this, "Error: Host ID is missing!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        database = FirebaseDatabase.getInstance("https://verthome-f7b0d-default-rtdb.firebaseio.com")
                .getReference("devices").child(host_id).child("relays");
        toggle1.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        toggle3.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        toggle2.setOnCheckedChangeListener((buttonView, isChecked) -> updateLightCount(isChecked));
        setupToggleButton(toggle1);
        fetchRelayState();

    aboutback6.setOnClickListener(view -> finish());

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fan1per.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fan2per.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fan3per.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
    private void setupToggleButton(ToggleButton toggleButton) {
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            database.child("relay3").setValue(isChecked);
            updateLightCount(isChecked);
        });
    }
    private void fetchRelayState() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean relay1State = snapshot.child("relay3").getValue(Boolean.class);

                    if (relay1State != null) {
                        toggle1.setChecked(relay1State);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void updateLightCount(boolean isChecked) {
        if (isChecked) {
            fanOnCount++;
        } else {
            fanOnCount--;
        }
        Fanon.setText("Fan On:" + fanOnCount);
    }
}