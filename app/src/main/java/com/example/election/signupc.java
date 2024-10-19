package com.example.election;

import android.content.Intent;  // Make sure to import Intent
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class signupc extends AppCompatActivity {

    private EditText firstName, lastName, email, registrationNo, password, confirmPassword, slogan;
    private Button signUpButton;
    private TextView loginLink;
    private ImageButton backButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupc);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        registrationNo = findViewById(R.id.registrationNo);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        slogan = findViewById(R.id.slogan);
        signUpButton = findViewById(R.id.signUpButton);
        loginLink = findViewById(R.id.loginLink);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> finish());

        signUpButton.setOnClickListener(view -> createAccount());

        // Add an OnClickListener for the loginLink TextView
        loginLink.setOnClickListener(view -> {
            // Start the loginc activity
            Intent intent = new Intent(signupc.this, loginc.class);
            startActivity(intent);
            finish(); // Optional: Close the signup activity
        });
    }

    private void createAccount() {
        String first = firstName.getText().toString().trim();
        String last = lastName.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String regNo = registrationNo.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String userSlogan = slogan.getText().toString().trim();

        // Validate fields
        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(userEmail) ||
                TextUtils.isEmpty(regNo) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password before storing
        String hashedPassword = hashPassword(userPassword);

        if (hashedPassword != null) {
            // Create a user in Firebase Auth and store user details in the database
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // User registered, store user data in the database
                            String userId = mAuth.getCurrentUser().getUid();
                            User user = new User(first, last, userEmail, regNo, userSlogan, hashedPassword);
                            mDatabase.child("users").child(userId).setValue(user)
                                    .addOnCompleteListener(this, userTask -> {
                                        if (userTask.isSuccessful()) {
                                            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Database Error: " + userTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Error hashing password", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to hash the password using SHA-1
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(password.getBytes());
            byte[] hashedBytes = digest.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // User class to store user details
    public static class User {
        public String firstName, lastName, email, registrationNo, slogan, password;

        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String firstName, String lastName, String email, String registrationNo, String slogan, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.registrationNo = registrationNo;
            this.slogan = slogan;
            this.password = password;
        }
    }
}
