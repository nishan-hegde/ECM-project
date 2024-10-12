package com.example.election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button candidateProfile = findViewById(R.id.button_profile);
        Button candidateResult = findViewById(R.id.button_results);
        Button candidateCandidates = findViewById(R.id.button_candidates);
        Button candidateElectionCreate = findViewById(R.id.button_help);
        Button anotherButton = findViewById(R.id.button_campaign_management);
        Button newButton = findViewById(R.id.button_supporter_interaction);

        // Navigate to Profilec activity
        candidateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, profilec.class);
                startActivity(intent);
            }
        });

        View.OnClickListener exitAppListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Exit the app for the other buttons
            }
        };

        candidateResult.setOnClickListener(exitAppListener);
        candidateCandidates.setOnClickListener(exitAppListener);
        candidateElectionCreate.setOnClickListener(exitAppListener);
        anotherButton.setOnClickListener(exitAppListener);
        newButton.setOnClickListener(exitAppListener);
    }
}
