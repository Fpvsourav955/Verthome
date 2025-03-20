package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));


        ImageView aboutback = findViewById(R.id.aboutback);


        aboutback.setOnClickListener(v -> navigateToActivity(RealTime.class));


        setupSocialLink(R.id.souravinsta, "https://www.instagram.com/sourav.pati_?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw==");
        setupSocialLink(R.id.souravlink, "https://www.linkedin.com/in/sourav-kumar-pati-aa0833297?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        setupSocialLink(R.id.souravgit, "https://github.com/Fpvsourav955");

        setupSocialLink(R.id.anshumaninsta, "https://www.instagram.com/anshuman.br?igsh=MWwzMzRjamVxbm50cw==");
        setupSocialLink(R.id.anshumanlink, "https://www.linkedin.com/in/anshuman-mahanta-149054278?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        setupSocialLink(R.id.anshumangit, "https://github.com/AnshumanMahanta");

        setupSocialLink(R.id.mukeshinsta, "https://www.instagram.com/mr_raj_mm_?igsh=Y2Q4eHVyNzBnY3Ex");
        setupSocialLink(R.id.mukeshlink, "https://www.linkedin.com/in/mukesh-bala-aa9743231?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        setupSocialLink(R.id.mukeshgit, "https://github.com/mukeshbala143");


        setupMoreButton(R.id.moresourav, AboutSourav.class);
        setupMoreButton(R.id.moreanshuman, AboutAnshuman.class);
        setupMoreButton(R.id.moremukesh, AboutMukesh.class);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Sets up a click listener for a button to navigate to a specified activity.
     *
     * @param buttonId    ID of the button
     * @param targetClass Target activity class
     */
    private void setupMoreButton(int buttonId, Class<?> targetClass) {
        findViewById(buttonId).setOnClickListener(v -> navigateToActivity(targetClass));
    }

    /**
     * Sets up a click listener for an ImageView to open a URL in a browser.
     *
     * @param viewId ID of the ImageView
     * @param url    URL to open
     */
    private void setupSocialLink(int viewId, String url) {
        findViewById(viewId).setOnClickListener(v -> openUrl(url));
    }

    /**
     * Opens a URL in the browser.
     *
     * @param url URL to open
     */
    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Navigates to the specified activity.
     *
     * @param targetClass Target activity class
     */
    private void navigateToActivity(Class<?> targetClass) {
        Intent intent = new Intent(TeamActivity.this, targetClass);
        startActivity(intent);
    }
}
