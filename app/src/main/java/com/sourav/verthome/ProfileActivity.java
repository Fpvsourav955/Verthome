package com.sourav.verthome;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        firebaseAuth = FirebaseAuth.getInstance();

        ImageView imageView9 = findViewById(R.id.imageView9);
        TextView profile_name = findViewById(R.id.Profile_name);
        TextView profile_email = findViewById(R.id.Profile_email);
        CircleImageView profile_image = findViewById(R.id.Profile_image);
        TextView logout1 = findViewById(R.id.logout1);
        TextView calender = findViewById(R.id.calender);
        profile_image.setScaleType(ImageView.ScaleType.CENTER_CROP);


        calender.setOnClickListener(view -> {

        });
        imageView9.setOnClickListener(v -> {
          Intent intent = new Intent(ProfileActivity.this,RealTime.class);
          startActivity(intent);
        });
        findViewById(R.id.notificationview).setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, Notification.class)));
       findViewById(R.id.shareview).setOnClickListener(v -> {
           Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
       });
        findViewById(R.id.githubview).setOnClickListener(v -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();


                });
        findViewById(R.id.term).setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, TermConditions.class)));
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            profile_name.setText(user.getDisplayName() != null ? user.getDisplayName() : "User");
            profile_email.setText(user.getEmail() != null ? user.getEmail() : "No Email Available");

            Uri photoUri = user.getPhotoUrl();
            if (photoUri != null) {
                Glide.with(this).load(photoUri).into(profile_image);
                profile_image.setOnClickListener(v -> showHDProfileImage(photoUri));
            } else {
                profile_image.setImageResource(R.drawable.baseline_person_24);
            }
        } else {
            Toast.makeText(this, "No user signed in!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }

        logout1.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void showHDProfileImage(Uri photoUri) {
        Dialog hdImageDialog = new Dialog(this);
        hdImageDialog.setContentView(R.layout.activity_profile);
        Objects.requireNonNull(hdImageDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView hdImageView = hdImageDialog.findViewById(R.id.Profile_image);
        Glide.with(this).load(photoUri).into(hdImageView);

        hdImageView.setOnClickListener(v -> hdImageDialog.dismiss());
        hdImageDialog.show();
    }
}
