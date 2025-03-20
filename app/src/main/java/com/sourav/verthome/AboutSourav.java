package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AboutSourav extends AppCompatActivity {
   TextView soDescription1;
   ImageView back3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_about_sourav);
        soDescription1=findViewById(R.id.soDescription1);
        back3=findViewById(R.id.back3);
        back3.setOnClickListener(v -> {
            Intent intent=new Intent(AboutSourav.this,TeamActivity.class);
            startActivity(intent);
        });
        String description = "🚀 Sourav Kumar Pati – Android & IoT Developer | AIoT Engineer 🚀\n\n" +
                "I am Sourav Kumar Pati, a passionate Android & IoT Developer with expertise in AIoT (Artificial Intelligence + Internet of Things). " +
                "With experience in embedded systems, real-time monitoring, and UI/UX design, I develop innovative solutions that merge technology and usability.\n\n" +
                "📱 Android Development:\n" +
                "- Proficient in Java, Kotlin, XML, Jetpack Components\n" +
                "- Experienced in Android Studio, Firebase, and REST APIs\n" +
                "- UI/UX implementation with Material Design and Custom Views\n\n" +
                "🌍 IoT & Embedded Systems:\n" +
                "- Hands-on experience with ESP32, ESP8266, Arduino, and Raspberry Pi\n" +
                "- Sensor integration: TDS, pH, NPK, flow rate, ultrasonic, turbidity, temperature\n" +
                "- Communication Protocols: MQTT, I2C, SPI, UART, Modbus\n" +
                "- IoT Cloud: Arduino IoT Cloud, Firebase, AWS IoT, Blynk\n\n" +
                "💻 Programming & Development:\n" +
                "- Languages: C, C++, Python, Java, JavaScript\n" +
                "- Web & App Development: React.js, Node.js, Express, Firebase\n" +
                "- Database Management: MySQL, Firebase Realtime Database\n\n" +
                "🎨 UI/UX & Visualization:\n" +
                "- Displays: OLED, TFT, LCD, LED indicators\n" +
                "- Data Visualization: Matplotlib, Plotly, Recharts, Streamlit Dashboards\n\n" +
                "🤖 AI + IoT (AIoT):\n" +
                "- AI-based voltage regulation, smart automation, anomaly detection\n" +
                "- Computer Vision: ESP32-CAM for object detection\n" +
                "- Automation & Control: Smart irrigation, home automation, GPS tracking\n\n" +
                "💡 My Vision:\n" +
                "I aim to develop smart, scalable, and impactful IoT and Android solutions that enhance everyday life through technology. " +
                "With a strong passion for innovation, I continue to explore cutting-edge AIoT, embedded systems, and mobile applications.\n\n" +
                "#TechEnthusiast #AIoTDeveloper #AndroidDev #IoTInnovator";
        soDescription1.setText(description);

    }
}