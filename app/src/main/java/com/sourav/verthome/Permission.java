package com.sourav.verthome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Permission extends AppCompatActivity {
    private CheckBox agreeCheckBox1;

    private static final String PREFS_NAME = "VerThomePrefs";
    private static final String FIRST_LAUNCH_KEY = "first_launch";
    private Button acceptButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH_KEY, true);



        if (!isFirstLaunch) {

            Intent intent = new Intent(Permission.this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_permission);
        TextView termsTextView1 = findViewById(R.id.termsTextView1);
        agreeCheckBox1 = findViewById(R.id.agreeCheckBox1);
        acceptButton1 = findViewById(R.id.acceptButton1);
        termsTextView1.setText(getTermsAndConditionsText());

        acceptButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeCheckBox1.isChecked()) {
                    Intent intent = new Intent(Permission.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Permission.this, "You must agree to the terms to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTermsAndConditionsText() {
        return "Welcome to Verthome! By using this app, you agree to the following Permissions...\n\n" +
                "1. Wi-Fi Permissions\n\n" +
                "Permission Name:\n\n" +
                "ACCESS_WIFI_STATE\n" +
                "CHANGE_WIFI_STATE\n" +
                "ACCESS_NETWORK_STATE\n\n" +
                "These permissions are required to connect the app to the home network (WiFi) so that the phone can communicate with the IoT devices to control them. This includes\n" +
                "managing the network state and ensuring devices are accessible via Wi-Fi for actions like\n" +
                "turning appliances on or off.\n\n" +
                "3. Location Permissions (Wi-Fi/Bluetooth)\n\n" +
                "Permission Name:\n\n" +
                "ACCESS_FINE_LOCATION\n" +
                "ACCESS_COARSE_LOCATION\n\n" +
                "Why Needed: Location permissions are essential for detecting Bluetooth and Wi-Fi networks,\n" +
                "which is how the phone communicates with IoT devices. On Android, the location permission\n" +
                "is required for any Wi-Fi or Bluetooth scanning because these technologies can be used to\n" +
                "determine the user’s location.\n\n" +
                "4. Notification Permissions\n\n" +
                "Permission Name:\n" +
                "POST_NOTIFICATIONS\n" +
                "Why Needed: The app will use notifications to inform the user about the status of appliances\n" +
                "(e.g., when a device is turned on/off), security alerts from the object tracker, or other system\n" +
                "updates like device malfunctions or security breaches. You’ll need this permission to send\n" +
                "push notifications.\n\n" +
                "6. Storage Permissions\n\n" +
                "Permission Name:\n" +
                "READ_EXTERNAL_STORAGE\n" +
                "WRITE_EXTERNAL_STORAGE\n" +
                "Why Needed: Storage permissions are needed if your app saves recorded video footage,\n" +
                "images from the camera, or other logs from IoT device activities to the local device storage.\n" +
                "It also allows you to store settings or app configuration.\n\n" +
                "" +
                "... and more";
    }
}

