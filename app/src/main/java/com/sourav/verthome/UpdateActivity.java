package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText hostid, addressadd, Titleadd;
    Button saveadd1,detele1;
    ImageView aboutback1;

    String id, title, address, host_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_update);

        aboutback1=findViewById(R.id.aboutback1);
        hostid = findViewById(R.id.hostid1);
        addressadd = findViewById(R.id.addressadd1);
        Titleadd = findViewById(R.id.Titleadd1);
        saveadd1 = findViewById(R.id.saveadd1);
        detele1=findViewById(R.id.delete1);



        getAndSetIntentData();

        saveadd1.setOnClickListener(v -> {
            String title = Titleadd.getText().toString().trim();
            String address = addressadd.getText().toString().trim();
            String host_id = hostid.getText().toString().trim();

            if (title.isEmpty() || address.isEmpty() || host_id.isEmpty()) {
                Toast.makeText(UpdateActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHelper vert_home1 = new DatabaseHelper(UpdateActivity.this);
                boolean isUpdated = vert_home1.updateData(id, title, address, host_id);
                if (isUpdated) {
                    Toast.makeText(UpdateActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

                    Titleadd.setText(title);
                    addressadd.setText(address);
                    hostid.setText(host_id);


                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        detele1.setOnClickListener(v -> confirmDialog());
        aboutback1.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateActivity.this,RealTime.class);
            startActivity(intent);
        });
    }


    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("address") && getIntent().hasExtra("host_id")) {

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            address = getIntent().getStringExtra("address");
            host_id = getIntent().getStringExtra("host_id");



            Titleadd.setText(title);
            addressadd.setText(address);
            hostid.setText(host_id);
        } else {
            Log.e("UpdateActivity", "No data found to update");
        }
    }
void confirmDialog(){
    AlertDialog.Builder builder= new AlertDialog.Builder(this);
    builder.setTitle("Delete:"+title+"?");
    builder.setMessage("Are you Sure to delete:"+title+"?");
    builder.setPositiveButton("yes", (dialog, which) -> {
        try (DatabaseHelper vert_home1 = new DatabaseHelper(UpdateActivity.this)) {
            vert_home1.deleteOneRow(id);
        }
        finish();
    });
    builder.setNegativeButton("No", (dialog, which) -> {

    });
    builder.create().show();
}
}
