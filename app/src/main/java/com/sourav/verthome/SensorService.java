package com.sourav.verthome;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorService extends Service {

    private DatabaseReference firebaseDatabase;
    private ValueEventListener sensorListener;
    private boolean rainPreviouslyDetected = false;
    private boolean lowVoltageNotified = false;
    private boolean highCurrentNotified = false;

    private static final int VOLTAGE_NOTIFICATION_ID = 101;
    private static final int CURRENT_NOTIFICATION_ID = 102;
    private static final int RAIN_NOTIFICATION_ID = 103;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        startForegroundService();
        listenToFirebase();
    }

    private void startForegroundService() {
        String channelId = "SensorServiceChannel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Sensor Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Monitoring Sensor Data")
                .setContentText("Service is running in the background")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(1, notification);
    }

    private void listenToFirebase() {
        sensorListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("Firebase", "Data found!");

                    double voltage = 0, current = 0;
                    boolean isRaining = false;

                    try {
                        Object voltObj = snapshot.child("voltage").getValue();
                        Object currObj = snapshot.child("current").getValue();
                        Object rainObj = snapshot.child("rain").getValue();

                        if (voltObj != null) voltage = Double.parseDouble(voltObj.toString());
                        if (currObj != null) current = Double.parseDouble(currObj.toString());
                        if (rainObj != null) isRaining = Boolean.parseBoolean(rainObj.toString());
                    } catch (NumberFormatException e) {
                        Log.e("Firebase", "Data parsing error: " + e.getMessage());
                    }

                    // Send notifications based on conditions
                    if (voltage < 200 && !lowVoltageNotified) {
                        sendVoltageNotification();
                        lowVoltageNotified = true;
                    } else if (voltage >= 200) {
                        lowVoltageNotified = false;
                    }

                    if (current > 35 && !highCurrentNotified) {
                        sendCurrentNotification();
                        highCurrentNotified = true;
                    } else if (current <= 35) {
                        highCurrentNotified = false;
                    }

                    if (isRaining && !rainPreviouslyDetected) {
                        sendRainNotification();
                        rainPreviouslyDetected = true;
                    } else if (!isRaining) {
                        rainPreviouslyDetected = false;
                    }
                } else {
                    Log.d("Firebase", "No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        };

        firebaseDatabase.addValueEventListener(sensorListener);
    }

    private void sendNotification(int notificationId, String channelId, String channelName, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Sensor Alerts");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_rain) // Ensure you have this drawable
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, builder.build());
    }

    private void sendCurrentNotification() {
        sendNotification(CURRENT_NOTIFICATION_ID, "CURRENT_CHANNEL", "Current Alerts", "Current Alert", "🚨 High Current Detected! Turn Off Appliances");
    }

    private void sendVoltageNotification() {
        sendNotification(VOLTAGE_NOTIFICATION_ID, "VOLTAGE_CHANNEL", "Voltage Alerts", "Voltage Alert", "🚨 Low Voltage Detected! Turn Off Appliances");
    }

    private void sendRainNotification() {
        sendNotification(RAIN_NOTIFICATION_ID, "RAIN_CHANNEL", "Rain Alerts", "Weather Alert", "🚨 Rain Detected! Take necessary precautions.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (firebaseDatabase != null && sensorListener != null) {
            firebaseDatabase.removeEventListener(sensorListener);
        }
    }
}
