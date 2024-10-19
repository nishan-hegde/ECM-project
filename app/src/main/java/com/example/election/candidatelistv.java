package com.example.election;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso; // For loading images from URLs

public class candidatelistv extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<User, CandidateViewHolder> adapter;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatelistv);

        // Initialize the RecyclerView and set its layout manager
        recyclerView = findViewById(R.id.recyclerViewCandidates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Reference to the Firebase Realtime Database node where users are stored
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        // FirebaseRecyclerOptions for configuring adapter with users list
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(usersRef, User.class) // Querying the 'users' node
                        .build();

        // FirebaseRecyclerAdapter to bind data to the RecyclerView
        adapter = new FirebaseRecyclerAdapter<User, CandidateViewHolder>(options) {
            @NonNull
            @Override
            public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Inflate the user item layout and return the ViewHolder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_candidate, parent, false);
                return new CandidateViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CandidateViewHolder holder, int position, @NonNull User model) {
                // Bind the user data to the ViewHolder (fetching firstName, section, and imageUrl)
                holder.bind(model);
            }
        };

        // Set the adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start listening for Firebase data changes when the activity starts
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening to prevent memory leaks when the activity stops
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
