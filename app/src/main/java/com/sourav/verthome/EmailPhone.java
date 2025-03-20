package com.sourav.verthome;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailPhone extends AppCompatActivity {
    private EditText etName, etEmail, etMessage;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_email_phone);
        databaseReference = FirebaseDatabase.getInstance().getReference("contact us");
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMessage = findViewById(R.id.etMessage);
        Button btnSend = findViewById(R.id.btnSend);
        ImageView notificationback = findViewById(R.id.notificationback);
        notificationback.setOnClickListener(v -> finish());
        btnSend.setOnClickListener(v -> saveDataToFirebase());
    }

    private void saveDataToFirebase() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Enter your name");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter your email");
            return;
        }

        if (TextUtils.isEmpty(message)) {
            etMessage.setError("Enter your message");
            return;
        }

        String id = databaseReference.push().getKey();

        Contact contact = new Contact(name, email, message);
        assert id != null;
        databaseReference.child(id).setValue(contact).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EmailPhone.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etEmail.setText("");
                etMessage.setText("");
            } else {
                Toast.makeText(EmailPhone.this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
