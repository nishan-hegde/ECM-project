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

        // Initialize buttons
        Button candidateProfile = findViewById(R.id.button_profile);
        Button candidateResult = findViewById(R.id.button_results);
        Button candidateCandidates = findViewById(R.id.button_candidates); // Existing button
        Button candidateElectionCreate = findViewById(R.id.button_help);
        Button anotherButton = findViewById(R.id.button_campaign_management);
        Button newButton = findViewById(R.id.button_supporter_interaction);

        // Set up click listeners
        setupNavigationButtons(candidateProfile, candidateResult, candidateCandidates, candidateElectionCreate);
        setupExitButtons(anotherButton, newButton);
    }

    private void setupNavigationButtons(Button candidateProfile, Button candidateResult, Button candidateCandidates, Button candidateElectionCreate) {
        // Navigate to Profilec activity
        candidateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, profilec.class);
            startActivity(intent);
        });

        // Navigate to Candidate List activity
        candidateCandidates.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, candidatelistv.class);
            startActivity(intent);
        });

        // Navigate to Candidate Section activity
        candidateResult.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, candidatesection.class);
            startActivity(intent);
        });

        // Navigate to Candidate Help activity
        candidateElectionCreate.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, candidatehelp.class);
            startActivity(intent);
        });
    }

    private void setupExitButtons(Button anotherButton, Button newButton) {
        View.OnClickListener exitAppListener = v -> finish(); // Exit the app

        anotherButton.setOnClickListener(exitAppListener);
        newButton.setOnClickListener(exitAppListener);
    }
}
