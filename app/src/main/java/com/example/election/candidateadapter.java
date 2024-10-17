package com.example.election;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class candidateadapter extends RecyclerView.Adapter<candidateadapter.CandidateViewHolder> {

    private Context context;
    private List<candidaterecycler> candidateList;

    public candidateadapter(Context context, List<candidaterecycler> candidateList) {
        this.context = context;
        this.candidateList = candidateList;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the candidate_item layout to create a new ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.candidate_item, parent, false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        // Get the candidate for this position
        candidaterecycler candidate = candidateList.get(position);
        holder.candidateName.setText(candidate.name);
        holder.candidateSection.setText("Section " + candidate.section);
        // Load the candidate's image using Picasso
        Picasso.get().load(candidate.imageUrl).into(holder.candidateImage);
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {

        ImageView candidateImage;
        TextView candidateName, candidateSection;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views from candidate_item layout
            candidateImage = itemView.findViewById(R.id.candidateImage);
            candidateName = itemView.findViewById(R.id.candidateName);
            candidateSection = itemView.findViewById(R.id.candidateSection);
        }
    }
}
