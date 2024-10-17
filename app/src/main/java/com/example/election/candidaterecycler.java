package com.example.election;

public class candidaterecycler {
        public String name;
        public String section;
        public String imageUrl;
        public String candidate;

        public candidaterecycler() {
            // Default constructor required for calls to DataSnapshot.getValue(Candidate.class)
        }

        public candidaterecycler(String name, String section, String imageUrl, String candidate) {
            this.name = name;
            this.section = section;
            this.imageUrl = imageUrl;
            this.candidate = candidate;
        }
    }

