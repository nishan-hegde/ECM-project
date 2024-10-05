package com.example.election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Link to your main layout

        // Find the button by its ID
        Button candidateButton = findViewById(R.id.btn_candidate);

        // Set an onClick listener for the button
        candidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the Candidate Activity
                Intent intent = new Intent(MainActivity.this, candidate.class);
                startActivity(intent);  // Start the new activity
            }
        });
    }
}
