package com.sourav.verthome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnderHome extends AppCompatActivity {
    private TextView greetingTextView, dateTextView, temperatureTextView, humidityTextView,Rain;
    private ProgressBar voltage_bar,current_bar;
    private TextView tempTextView, humTextView, voltTextView, currTextView;
    private DatabaseReference firebaseDatabase;
    private boolean rainPreviouslyDetected = false;
    private final Handler handler = new Handler();
    private Runnable statusResetRunnable;
    private TextView lightstatus, fanstatus, plugstatus, tvstatus, acstatus;
    DatabaseHelper dbHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private String selectedHostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_under_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
        dbHelper = new DatabaseHelper(this);
        locationTextView = findViewById(R.id.locationTextView);
        lightstatus = findViewById(R.id.lightstatus);
        fanstatus = findViewById(R.id.fanstatus);
        plugstatus = findViewById(R.id.plugstatus);
        tvstatus = findViewById(R.id.tvstatus);
        voltTextView = findViewById(R.id.voltTextView);
        Rain = findViewById(R.id.Rain);
        currTextView = findViewById(R.id.currTextView);
        acstatus = findViewById(R.id.acstatus);
        greetingTextView = findViewById(R.id.greetingTextView);
        dateTextView = findViewById(R.id.dateTextView);
        tempTextView = findViewById(R.id.tempTextView);
        humTextView = findViewById(R.id.humTextView);
        voltage_bar = findViewById(R.id.voltage_bar);
        current_bar = findViewById(R.id.current_bar);

        ImageView aboutback3 = findViewById(R.id.aboutback3);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        TextView home_title = findViewById(R.id.home_title);
        home_title.setText(String.valueOf(title));

        String host_id=intent.getStringExtra("host_id");
        if (host_id == null || host_id.isEmpty()) {
            Toast.makeText(this, "Error: Host ID is missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
       firebaseDatabase= FirebaseDatabase.getInstance("https://verthome-f7b0d-default-rtdb.firebaseio.com")
                .getReference("devices").child(host_id).child("sensor");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            fetchLocation();
        }

       fetchSensorData();
        setGreetingAndDate();


        aboutback3.setOnClickListener(v -> {
            Intent intent1 = new Intent(UnderHome.this, RealTime.class);
            startActivity(intent1);
        });



        findViewById(R.id.view1).setOnClickListener(v -> {
            Intent intent2 = new Intent(UnderHome.this, Splash2.class);
            intent2.putExtra("host_id1", host_id);
            startActivity(intent2);
        });
        findViewById(R.id.view2).setOnClickListener(v ->{
                    Intent intent2 = new Intent(UnderHome.this, Splash3.class);
                    intent2.putExtra("host_id1", host_id);
                    startActivity(intent2);
                }
                );
        findViewById(R.id.view3).setOnClickListener(v ->
        {
            Intent intent2 = new Intent(UnderHome.this, Splash5.class);
            intent2.putExtra("host_id1", host_id);
            startActivity(intent2);
        });
        findViewById(R.id.view4).setOnClickListener(v ->{
                    Intent intent2 = new Intent(UnderHome.this, Splash6.class);
                    intent2.putExtra("host_id1", host_id);
                    startActivity(intent2);
                });
        findViewById(R.id.view5).setOnClickListener(v ->{
                    Intent intent2 = new Intent(UnderHome.this, Splash4.class);
                    intent2.putExtra("host_id1", host_id);
                    startActivity(intent2);
                }
               );
        findViewById(R.id.view7).setOnClickListener(v ->{
            Intent intent2 = new Intent(UnderHome.this, Splash7.class);
            intent2.putExtra("host_id1", host_id);
            startActivity(intent2);

                });
    }


    private void setGreetingAndDate() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) {
            greeting = "Morning,";
        } else if (hour < 18) {
            greeting = "Afternoon,";
        } else if (hour < 21) {
            greeting = "Evening,";
        } else {
            greeting = "Night,";
        }
        greetingTextView.setText(greeting);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
        dateTextView.setText(currentDate);
    }


    @SuppressLint("SetTextI18n")
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();


                        String areaName = getAreaName(latitude, longitude);
                        fetchTemperature(latitude, longitude, areaName);
                    } else {
                        locationTextView.setText("Unable to fetch location");
                    }
                });
    }

    private String getAreaName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                String subLocality = address.getSubLocality();
                if (subLocality != null && !subLocality.isEmpty()) {
                    return subLocality;
                }

                String thoroughfare = address.getThoroughfare();
                if (thoroughfare != null && !thoroughfare.isEmpty()) {
                    return thoroughfare;
                }

                String locality = address.getLocality();
                if (locality != null && !locality.isEmpty()) {
                    return locality;
                }

                String adminArea = address.getAdminArea();
                if (adminArea != null && !adminArea.isEmpty()) {
                    return adminArea;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Location";
    }


    private void fetchTemperature(double latitude, double longitude, String areaName) {
        String apiKey = "f93d02eaa7a42e949412d9272c9454d6";
        String baseUrl = "https://api.openweathermap.org/data/2.5/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getWeather(latitude, longitude, apiKey, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double temperature = response.body().getMain().getTemp();
                    double windSpeedInMps = response.body().getWind().getSpeed();

                    double windSpeedInKph = windSpeedInMps * 3.6;
                    locationTextView.setText(String.format("%s, %s°C\nWind Speed: %.2f km/h", areaName, temperature, windSpeedInKph));
                } else {
                    locationTextView.setText("Failed to fetch weather data");
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                locationTextView.setText("Error: " + t.getMessage());
            }
        });
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            } else {
                locationTextView.setText("Location permission denied");
            }
        }
    }


    private void fetchSensorData() {
        firebaseDatabase
                .addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    resetDisconnectionTimer();

                    Object tempObj = snapshot.child("temperature").getValue();
                    Object humObj = snapshot.child("humidity").getValue();
                    Object voltObj = snapshot.child("voltage").getValue();
                    Object currObj = snapshot.child("current").getValue();
                    Object rainObj = snapshot.child("rain").getValue(); // Boolean value

                    String temperature = (tempObj != null) ? df.format(Double.parseDouble(tempObj.toString())) : "N/A";
                    boolean isRaining = rainObj != null && Boolean.parseBoolean(rainObj.toString());
                    String rainStatus = isRaining ? "Rain Detected" : "No Rain";

                    String humidity = (humObj != null) ? df.format(Double.parseDouble(humObj.toString())) : "N/A";

                    double voltage = (voltObj != null) ? Double.parseDouble(voltObj.toString()) : 0;
                    double current = (currObj != null) ? Double.parseDouble(currObj.toString()) : 0;
                    voltage = Math.max(0, Math.min(240, voltage));
                    current = Math.max(0, Math.min(50, current));
                    int voltageProgress = (int) ((voltage / 240) * 100);
                    int currentProgress = (int) ((current / 50) * 100);
                    voltage_bar.setProgress(voltageProgress);
                    current_bar.setProgress(currentProgress);




                    tempTextView.setText(temperature + "°C");
                    humTextView.setText(humidity + "%");
                    voltTextView.setText(voltage + "V");
                    currTextView.setText(current + "A");
                    Rain.setText( rainStatus);



                    lightstatus.setText("Connected");
                   acstatus.setText("Connected");
                    fanstatus.setText("Connected");
                    tvstatus.setText("Connected");
                    plugstatus.setText("Connected");
                    if (voltage < 200) {
                        sendVoltageNotification();
                    }
                    if (current > 35) {
                        sendCurrentNotification();
                    }

                    if (isRaining && !rainPreviouslyDetected) {
                        sendRainNotification();
                        rainPreviouslyDetected = true;
                    } else if (!isRaining) {


                        rainPreviouslyDetected = false;
                    }



                } else {
                   setDisconnectedStatus();
                }
            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        setDisconnectedStatus();
                    }

        });
        resetDisconnectionTimer();

    }
    private void sendNotification(String channelId, String channelName, String title, String message,int iconResId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {

            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Alert Notification");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(iconResId)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(channelId.hashCode(), builder.build());
    }

    private void resetDisconnectionTimer() {
        if (statusResetRunnable != null) {
            handler.removeCallbacks(statusResetRunnable);
        }
        statusResetRunnable = this::setDisconnectedStatus;
        handler.postDelayed(statusResetRunnable, 5000);
    }


    @SuppressLint("SetTextI18n")
    private void setDisconnectedStatus() {
        lightstatus.setText("Disconnected");
        acstatus.setText("Disconnected");
        fanstatus.setText("Disconnected");
        tvstatus.setText("Disconnected");
        plugstatus.setText("Disconnected");
    }
    private void sendCurrentNotification() {
        sendNotification("CURRENT_CHANNEL", "Current Alerts", "Current Alert", "🚨 High Current Detected! Turn Off Appliances",R.drawable.ic_rain);
    }

    private void sendVoltageNotification() {
        sendNotification("VOLTAGE_CHANNEL", "Voltage Alerts", "Voltage Alert", "🚨 Low Voltage Detected! Turn Off Appliances",R.drawable.voltageimg);
    }

    private void sendRainNotification() {
        sendNotification("RAIN_CHANNEL", "Rain Alerts", "Weather Alert", "🚨 Rain Detected! Take necessary precautions.",R.drawable.currentimg);
    }

}