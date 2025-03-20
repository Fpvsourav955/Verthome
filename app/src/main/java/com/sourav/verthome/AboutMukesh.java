package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AboutMukesh extends AppCompatActivity {
    TextView muDescription2;
    ImageView back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_about_mukesh);
        muDescription2=findViewById(R.id.muDescription2);
        back2=findViewById(R.id.back2);
        back2.setOnClickListener(v -> {
            Intent intent=new Intent(AboutMukesh.this,TeamActivity.class);
            startActivity(intent);
        });
        String description = "🐍 Mukesh Bala – Python Developer & Software Engineer 🐍\n\n" +
                "I am Mukesh Bala, a passionate Python Developer with expertise in backend development, AI/ML, and automation. " +
                "With a strong problem-solving mindset, I specialize in building scalable applications, data-driven solutions, and automation tools.\n\n" +
                "💻 Python Development:\n" +
                "- Proficient in Python, Flask, Django, FastAPI\n" +
                "- API Development with REST & GraphQL\n" +
                "- Database Handling: MySQL, PostgreSQL, MongoDB\n\n" +
                "🤖 AI & Machine Learning:\n" +
                "- Data Science & Analysis using Pandas, NumPy, Matplotlib\n" +
                "- Machine Learning with Scikit-Learn, TensorFlow, PyTorch\n" +
                "- Natural Language Processing (NLP) & Computer Vision\n\n" +
                "🛠 Automation & Scripting:\n" +
                "- Web Scraping with BeautifulSoup & Selenium\n" +
                "- Task Automation & Schedulers (Celery, Cron Jobs)\n" +
                "- Data Processing & ETL Pipelines\n\n" +
                "🌍 Cloud & DevOps:\n" +
                "- Experience with AWS, GCP, Azure\n" +
                "- Docker & Kubernetes for Containerized Applications\n" +
                "- CI/CD Pipelines with GitHub Actions, Jenkins\n\n" +
                "🚀 Vision & Mission:\n" +
                "I aim to develop intelligent, data-driven solutions that optimize workflows and improve decision-making. " +
                "With a strong foundation in software engineering and AI, I strive to create impactful technology solutions.\n\n" +
                "#PythonDeveloper #AIEngineer #BackendDev #AutomationExpert";

       muDescription2.setText(description);

    }
}