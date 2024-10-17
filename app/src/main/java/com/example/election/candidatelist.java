package com.example.election;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class candidatelist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private candidateadapter candidateAdapter;
    private List<candidaterecycler> candidateList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatelist);

        // Initialize Firebase database reference
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // Optional: for offline capabilities
        databaseReference = FirebaseDatabase.getInstance().getReference("candidates");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        candidateList = new ArrayList<>();
        candidateAdapter = new candidateadapter(this, candidateList);
        recyclerView.setAdapter(candidateAdapter);

        // Fetch only candidates with "candidate": "True"
        fetchCandidates();

        // Add dummy data for testing
        addDummyData();
    }

    private void fetchCandidates() {
        databaseReference.orderByChild("candidate").equalTo("True").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d("DataSnapshot", snapshot.toString());
                        candidaterecycler candidate = snapshot.getValue(candidaterecycler.class);
                        if (candidate != null) {
                            candidateList.add(candidate);
                        }
                    }
                    candidateAdapter.notifyDataSetChanged();
                } else {
                    Log.d("DataSnapshot", "No candidates found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Database Error: " + databaseError.getMessage());
            }
        });

        // Test simple read operation
        testReadOperation();
    }

    private void testReadOperation() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TestData", "Data: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void addDummyData() {
        candidateList.add(new candidaterecycler("John Doe", "A", "https://example.com/john_doe.jpg", "True"));
        candidateList.add(new candidaterecycler("Jane Smith", "B", "https://example.com/jane_smith.jpg", "True"));
        candidateAdapter.notifyDataSetChanged();
    }
}
