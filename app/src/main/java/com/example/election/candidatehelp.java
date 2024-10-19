package com.example.election;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class candidatehelp extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private Button btnPhone, btnEmail;

    private String[] faqQuestions = {
            "How do I find information about a candidate?",
            "What are the policies of a candidate?",
            "How can I contact a candidate?",
            "Can I change my vote if I choose a different candidate?",
            "Where can I see a candidate's past voting record?",
            "How can I report a problem with candidate information?",
            "What do I do if I can't find a candidate?"
    };

    private String[] faqAnswers = {
            "You can find information about candidates by visiting the 'Candidates' section in the app, which includes their profile, policies, and campaign details.",
            "Each candidate's policy details are listed in their profile. You can access this information from the 'Candidates' section in the app.",
            "To contact a candidate, visit their profile page where you can find their official contact details if available.",
            "Once you submit your vote, you cannot change your choice. Make sure to review your selections before finalizing.",
            "You can view a candidate's past voting records and public service history in their profile under the 'History' section.",
            "If you notice incorrect information, please contact support using the details below.",
            "If a candidate isn't listed, make sure you have the latest app updates. If the issue persists, contact support."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatehelp);

        expandableListView = findViewById(R.id.expandableListView);
        btnPhone = findViewById(R.id.btn_phone);
        btnEmail = findViewById(R.id.btn_email);

        setupExpandableListView();
        setupButtons();

        // Back button functionality
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupExpandableListView() {
        HashMap<String, String> faqMap = new HashMap<>();
        for (int i = 0; i < faqQuestions.length; i++) {
            faqMap.put(faqQuestions[i], faqAnswers[i]);
        }

        List<String> faqList = new ArrayList<>(faqMap.keySet());
        FaqAdapter adapter = new FaqAdapter(this, faqList, faqMap);
        expandableListView.setAdapter(adapter);
    }

    private void setupButtons() {
        btnPhone.setOnClickListener(v -> {
            copyToClipboard("Phone", "7066062958");
            Toast.makeText(this, "Phone number copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        btnEmail.setOnClickListener(v -> {
            copyToClipboard("Email", "pawardevelops@gmail.com");
            Toast.makeText(this, "Email address copied to clipboard", Toast.LENGTH_SHORT).show();
        });
    }

    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }
}
