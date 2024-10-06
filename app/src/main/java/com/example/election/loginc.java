package com.example.election;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class loginc extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private FirebaseAuth mAuth;
    private TextView forgotPassword, candidateSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginc);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Bind views
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        forgotPassword = findViewById(R.id.forget_candidate_password);
        candidateSignup = findViewById(R.id.candidate_signup); // Add this line

        // Set onClickListener for Login Button
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Set onClickListener for Forgot Password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        // Set onClickListener for Signup TextView
        candidateSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the Signup activity
                Intent intent = new Intent(loginc.this, signupc.class);
                startActivity(intent); // Start the Signup activity
            }
        });
    }

    // Method to handle user login
    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validate email and password fields
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            emailInput.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }

        // Firebase authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, go to success activity
                        Intent intent = new Intent(loginc.this, success.class);
                        startActivity(intent);
                        finish(); // Close current activity
                    } else {
                        // Login failed, show error message
                        Toast.makeText(loginc.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Method to handle password reset
    private void resetPassword() {
        String email = emailInput.getText().toString().trim();

        // Validate email field
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Please enter your email to reset your password");
            emailInput.requestFocus();
            return;
        }

        // Send password reset email
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(loginc.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(loginc.this, "Failed to send reset email: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
