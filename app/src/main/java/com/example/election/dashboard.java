package com.example.election;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Change to your XML layout name

        Button candidateProfile = findViewById(R.id.candidate_profile);
        Button candidateResult = findViewById(R.id.candidate_result);
        Button candidateCandidates = findViewById(R.id.candidate_candidates);
        Button candidateElectionCreate = findViewById(R.id.candidate_election_create);
        Button anotherButton = findViewById(R.id.another_button);
        Button newButton = findViewById(R.id.new_button);

        View.OnClickListener exitAppListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Exit the app
            }
        };

        candidateProfile.setOnClickListener(exitAppListener);
        candidateResult.setOnClickListener(exitAppListener);
        candidateCandidates.setOnClickListener(exitAppListener);
        candidateElectionCreate.setOnClickListener(exitAppListener);
        anotherButton.setOnClickListener(exitAppListener);
        newButton.setOnClickListener(exitAppListener);
    }
}

