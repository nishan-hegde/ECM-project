package com.example.election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Change to your main activity layout name

        // Find the button by ID
        AppCompatButton enterCandidateBttn = findViewById(R.id.enterCandidateBttn);

        // Set an OnClickListener on the button
        enterCandidateBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the Candidate activity
                Intent intent = new Intent(MainActivity.this, candidate.class);
                startActivity(intent); // Start the Candidate activity
            }
        });
    }
}

