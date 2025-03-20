package com.sourav.verthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RealTime extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private CustomAdapter customAdapter;
    private DatabaseHelper vert_home1;
    private TextView Titleadd, addressadd, hostid;
    private ArrayList<String> title, address, host_id, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_real_time);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, 101);
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycleView);

        vert_home1 = new DatabaseHelper(RealTime.this);
        title = new ArrayList<>();
        address = new ArrayList<>();
        host_id = new ArrayList<>();
        id = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(RealTime.this, this, id, title, address, host_id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);


        fab.setOnClickListener(v -> {
            Intent intent = new Intent(RealTime.this, AddActivity.class);
            startActivity(intent);
        });

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home1:
                Intent intent1 = new Intent(RealTime.this,HomeActivity.class);
                startActivity(intent1);
                finish();


                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(RealTime.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_profile:
                Intent profileIntent = new Intent(RealTime.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.Devteam:
                Intent teamIntent = new Intent(RealTime.this, TeamActivity.class);
                startActivity(teamIntent);
                break;
            case  R.id.Feedback:
                Intent feedbackIntent = new Intent(RealTime.this, Feedback.class);
                startActivity(feedbackIntent);
                break;
            case R.id.nav_email:
                Intent email11=new Intent(RealTime.this, EmailPhone.class);
                startActivity(email11);
                break;
            case R.id.AboutVerthome:
                Intent verthomus= new Intent(RealTime.this, AboutUs.class);
                startActivity(verthomus);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            storeDataInArrays();
            customAdapter.notifyDataSetChanged();
        }
    }


    private void storeDataInArrays() {

        Cursor cursor = vert_home1.readAllData();
        if (cursor != null && cursor.moveToFirst()) {
            do {

                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                address.add(cursor.getString(2));
                host_id.add(cursor.getString(3));
            } while (cursor.moveToNext());

            cursor.close();
        }


    }
}
