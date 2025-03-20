package com.sourav.verthome;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ImageView aboutback2;

    private HomeAdapter  homeAdapter;
    DatabaseHelper vert_home1;
    private ArrayList<String> title, address, host_id, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        aboutback2=findViewById(R.id.aboutback2);
        aboutback2.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RealTime.class);
            startActivity(intent);
        });

        vert_home1 = new DatabaseHelper(HomeActivity.this);
        title = new ArrayList<>();
        address = new ArrayList<>();
        host_id = new ArrayList<>();
        id = new ArrayList<>();

        storeDataInArrays();
        homeAdapter = new HomeAdapter(HomeActivity.this, this, id, title, address, host_id);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            storeDataInArrays();
           homeAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.delete_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

