package com.example.election;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso; // Library to load images from URLs
import androidx.annotation.NonNull;

public class CandidateViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView sectionTextView;
    private ImageView profileImageView;

    public CandidateViewHolder(@NonNull View itemView) {
        super(itemView);

        // Find views in the item layout (assuming you have these in 'item_user.xml')
        nameTextView = itemView.findViewById(R.id.userName);
        sectionTextView = itemView.findViewById(R.id.userSection);
        profileImageView = itemView.findViewById(R.id.userImage);
    }

    // Bind user data to the views
    public void bind(User user) {
        nameTextView.setText(user.getFirstName());
        sectionTextView.setText(user.getSection());

        // Load profile image from the URL using Picasso
        Picasso.get().load(user.getImageUrl()).into(profileImageView);
    }
}
