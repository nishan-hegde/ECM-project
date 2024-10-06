package com.example.election;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }

    private void createAccount() {
        String first = firstName.getText().toString().trim();
        String last = lastName.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String regNo = registrationNo.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String userSlogan = slogan.getText().toString().trim();

        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(userEmail) ||
                TextUtils.isEmpty(regNo) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(first, last, userEmail, regNo, userSlogan);
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
    }

    public static class User {
        public String firstName, lastName, email, registrationNo, slogan;

        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String firstName, String lastName, String email, String registrationNo, String slogan) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.registrationNo = registrationNo;
            this.slogan = slogan;
        }

        // Optionally, you can add getters and setters if needed
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRegistrationNo() {
            return registrationNo;
        }

        public void setRegistrationNo(String registrationNo) {
            this.registrationNo = registrationNo;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }
    }
}
