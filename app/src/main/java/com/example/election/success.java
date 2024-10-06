package com.example.election;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class success extends AppCompatActivity {

    private AppCompatButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // Find the start button by its ID
        loginButton = findViewById(R.id.start);

        // Set a click listener for the start button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When button is clicked, navigate to DashboardActivity
                Intent intent = new Intent(success.this, dashboard.class);
                startActivity(intent);
                finish(); // Optionally close the current activity
            }
        });
    }
}