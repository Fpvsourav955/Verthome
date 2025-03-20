package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText hostid,addressadd,Titleadd;
    Button saveadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_add);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        hostid = findViewById(R.id.hostid);
        addressadd = findViewById(R.id.addressadd);
        Titleadd = findViewById(R.id.Titleadd);
        saveadd = findViewById(R.id.saveadd);

        saveadd.setOnClickListener(v -> {
            String title = Titleadd.getText().toString().trim();
            String address = addressadd.getText().toString().trim();
            String host = hostid.getText().toString().trim();

            if (title.isEmpty() || address.isEmpty() || host.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    int hostId = Integer.parseInt(host);
                    DatabaseHelper vert_home1 = new DatabaseHelper(this);
                    vert_home1.addDetail(title, address, hostId);
                    Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddActivity.this,HomeActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Host ID must be a number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}