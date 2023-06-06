package com.example.BackEnd;

import org.json.JSONObject;

public class Complaint {
    int patID;
    int docID;
    String reason; 

    public Complaint(int patID, String details, int docID) {
        this.patID = patID;
        this.docID = docID;
        JSONObject json = new JSONObject(details);
        this.reason = json.getString("reason");
    }
}