package com.example.election;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide; // Import Glide for image loading
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class profilec extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText phoneNumber, courseField;
    private Spinner deptSpinner, sectionSpinner, semSpinner, candidateSpinner;
    private Button logoutButton;
    private ImageButton backButton;
    private ImageView profileImageView; // Added for image selection
    private Uri imageUri; // Uri to hold selected image
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef; // Reference to Firebase Storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilec);

        // Initialize Firebase Auth, Database, and Storage
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mStorageRef = FirebaseStorage.getInstance().getReference("profile_images"); // Folder in Firebase Storage

        // Initialize UI elements
        phoneNumber = findViewById(R.id.phone_number);
        courseField = findViewById(R.id.course_field);
        deptSpinner = findViewById(R.id.dept_spinner);
        sectionSpinner = findViewById(R.id.section_spinner);
        semSpinner = findViewById(R.id.sem_spinner);
        candidateSpinner = findViewById(R.id.candidate_spinner);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);
        profileImageView = findViewById(R.id.profile_image); // ImageView for the profile image

        // Load user data
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            loadUserProfile(user.getUid());
        } else {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if not logged in
        }

        // Set up button listeners
        logoutButton.setOnClickListener(view -> {
            if (imageUri != null) {
                uploadImageAndSaveProfile(user.getUid());
            } else {
                updateProfileAndLogout(user.getUid()); // Save and logout if no image is selected
            }
        });
        backButton.setOnClickListener(view -> finish()); // Close the activity

        // Set up ImageView click to choose image
        profileImageView.setOnClickListener(view -> openFileChooser());
    }

    // Open image chooser
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle result of image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri); // Display the selected image
        }
    }

    // Load user data from Firebase
    private void loadUserProfile(String userId) {
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    // Populate the profile fields with user data
                    phoneNumber.setText(user.phone);
                    courseField.setText(user.course);
                    // Set spinner selection by matching text values
                    setSpinnerSelectionByValue(deptSpinner, user.dept);
                    setSpinnerSelectionByValue(sectionSpinner, user.section);
                    setSpinnerSelectionByValue(semSpinner, user.sem);
                    setSpinnerSelectionByValue(candidateSpinner, user.candidate);

                    // Check if imageUrl is not null or empty, then load the image
                    if (!TextUtils.isEmpty(user.imageUrl)) {
                        // Load the image into profileImageView using Glide
                        Glide.with(profilec.this)
                                .load(user.imageUrl)
                                .placeholder(R.drawable.circle_shape) // Optional: default image
                                .into(profileImageView);
                    }
                } else {
                    Toast.makeText(profilec.this, "User profile not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(profilec.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Upload image to Firebase Storage and then save profile data
    private void uploadImageAndSaveProfile(String userId) {
        StorageReference fileReference = mStorageRef.child(userId + ".jpg"); // Save image with userId as filename
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString(); // Get URL of uploaded image
                    saveProfileWithImage(userId, imageUrl); // Save profile with image URL
                }))
                .addOnFailureListener(e -> Toast.makeText(profilec.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Save profile data including image URL
    private void saveProfileWithImage(String userId, String imageUrl) {
        String userPhone = phoneNumber.getText().toString().trim();
        String userCourse = courseField.getText().toString().trim();
        // Fetch text values from Spinners instead of positions
        String userDept = deptSpinner.getSelectedItem().toString();
        String userSection = sectionSpinner.getSelectedItem().toString();
        String userSem = semSpinner.getSelectedItem().toString();
        String userCandidate = candidateSpinner.getSelectedItem().toString();

        // Validate fields
        if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(userCourse)) {
            Toast.makeText(this, "Phone and course fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map of updated values
        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("phone", userPhone);
        updatedValues.put("course", userCourse);
        updatedValues.put("dept", userDept);
        updatedValues.put("section", userSection);
        updatedValues.put("sem", userSem);
        updatedValues.put("candidate", userCandidate);
        updatedValues.put("imageUrl", imageUrl); // Store image URL in the database

        // Update only the specified fields using updateChildren()
        mDatabase.child(userId).updateChildren(updatedValues)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(profilec.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        logout(); // Logout the user after saving the profile
                    } else {
                        Toast.makeText(profilec.this, "Failed to update profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Save and logout (if no image is selected)
    private void updateProfileAndLogout(String userId) {
        String userPhone = phoneNumber.getText().toString().trim();
        String userCourse = courseField.getText().toString().trim();
        String userDept = deptSpinner.getSelectedItem().toString();
        String userSection = sectionSpinner.getSelectedItem().toString();
        String userSem = semSpinner.getSelectedItem().toString();
        String userCandidate = candidateSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(userCourse)) {
            Toast.makeText(this, "Phone and course fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("phone", userPhone);
        updatedValues.put("course", userCourse);
        updatedValues.put("dept", userDept);
        updatedValues.put("section", userSection);
        updatedValues.put("sem", userSem);
        updatedValues.put("candidate", userCandidate);

        mDatabase.child(userId).updateChildren(updatedValues)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(profilec.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        logout();
                    } else {
                        Toast.makeText(profilec.this, "Failed to update profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Logout the user
    private void logout() {
        mAuth.signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(profilec.this, loginc.class); // Redirect to login activity
        startActivity(intent);
        finish(); // Close the profile activity
    }

    // Helper function to set spinner by value
    private void setSpinnerSelectionByValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    // User class to store user details
    public static class User {
        public String phone, course;
        public String dept, section, sem, candidate;
        public String imageUrl; // Add imageUrl to the User class

        public User() {
        }

        public User(String phone, String course, String dept, String section, String sem, String candidate, String imageUrl) {
            this.phone = phone;
            this.course = course;
            this.dept = dept;
            this.section = section;
            this.sem = sem;
            this.candidate = candidate;
            this.imageUrl = imageUrl;
        }
    }
}
