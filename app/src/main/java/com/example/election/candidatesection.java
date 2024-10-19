package com.example.election;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class candidatesection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatesection); // Ensure your layout file is named activity_results.xml

        // Get references to the buttons in the layout
        Button btnClassA = findViewById(R.id.btnClassA);
        Button btnClassB = findViewById(R.id.btnClassB);
        Button btnClassC = findViewById(R.id.btnClassC);

        // Set up a common listener for all buttons to exit the app
        View.OnClickListener exitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity (exit the app if this is the last activity)
            }
        };

        // Set the listener for each button
        btnClassA.setOnClickListener(exitListener);
        btnClassB.setOnClickListener(exitListener);
        btnClassC.setOnClickListener(exitListener);
    }
}
