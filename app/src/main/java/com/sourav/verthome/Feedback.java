package com.sourav.verthome;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    private EditText etFeedback;
    private RatingBar ratingBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));

        setContentView(R.layout.activity_feedback);


        etFeedback = findViewById(R.id.etFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        ImageView aboutback11 = findViewById(R.id.aboutback11);
        Button btnSendFeedback = findViewById(R.id.btnSendFeedback);
        databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks");


        btnSendFeedback.setOnClickListener(v -> sendFeedback());
        aboutback11.setOnClickListener(v -> finish());
    }


    private void sendFeedback() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            String feedbackText = etFeedback.getText().toString().trim();
            float ratingValue = ratingBar.getRating();


            if (feedbackText.isEmpty()) {
                Toast.makeText(this, "Please enter your feedback!", Toast.LENGTH_SHORT).show();
                return;
            }


            String location = "Latitude, Longitude";


            String feedbackId = databaseReference.push().getKey();


            FeedbackModel feedback = new FeedbackModel(userEmail, location, feedbackText, ratingValue);


            if (feedbackId != null) {
                databaseReference.child(feedbackId).setValue(feedback)
                        .addOnSuccessListener(aVoid -> Toast.makeText(Feedback.this, "Feedback sent successfully!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(Feedback.this, "Failed to send feedback!", Toast.LENGTH_SHORT).show());
            }
        } else {
            Toast.makeText(this, "Please login to send feedback", Toast.LENGTH_SHORT).show();
        }
    }


    public static class FeedbackModel {
        private String userEmail;
        private String location;
        private String feedback;
        private float rating;

        public FeedbackModel() {

        }

        public FeedbackModel(String userEmail, String location, String feedback, float rating) {
            this.userEmail = userEmail;
            this.location = location;
            this.feedback = feedback;
            this.rating = rating;
        }


        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }
    }
}
