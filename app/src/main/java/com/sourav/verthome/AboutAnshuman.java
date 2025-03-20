package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AboutAnshuman extends AppCompatActivity {
    TextView anDescription;
    ImageView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_about_anshuman);
        anDescription=findViewById(R.id.anDescription);
        back1=findViewById(R.id.back1);
        back1.setOnClickListener(v -> {
            Intent intent=new Intent(AboutAnshuman.this,TeamActivity.class);
            startActivity(intent);
        });
        String description = "✨ Anshuman Mahanta – UI/UX Designer & Developer ✨\n\n" +
                "Anshuman Mahanta is a 20-year-old UI/UX designer with a deep passion for crafting intuitive, visually appealing, and user-friendly digital experiences. " +
                "His expertise lies in designing modern interfaces that not only look stunning but also enhance usability and accessibility.\n\n" +
                "🎨 UI/UX Design:\n" +
                "- Proficient in Figma, Adobe XD, Sketch, and Photoshop\n" +
                "- Strong grasp of color theory, typography, and layout principles\n" +
                "- Skilled in prototyping and wireframing\n\n" +
                "🖥️ Front-End Development:\n" +
                "- Experience in HTML, CSS, JavaScript, and React\n" +
                "- Knowledge of CSS frameworks like Tailwind, Bootstrap\n" +
                "- Passionate about micro-interactions and animations\n\n" +
                "🔎 User Research & Testing:\n" +
                "- Conducts user research, surveys, and usability testing\n" +
                "- Data-driven approach to analyzing user behavior\n\n" +
                "✅ Work Philosophy:\n" +
                "- Simplicity & Clarity – Keeping designs minimal yet powerful\n" +
                "- Empathy – Understanding user pain points\n" +
                "- Innovation – Learning and experimenting with new trends\n\n" +
                "🚀 Future Aspirations:\n" +
                "Anshuman dreams of working with top tech companies or startups, building products that impact millions of lives. " +
                "His ultimate goal is to create digital experiences that feel effortless and intuitive.\n\n" +
                "With a creative mindset and a passion for excellence, Anshuman Mahanta is set to leave his mark in UI/UX design. 🎯";
            anDescription.setText(description);

    }
}