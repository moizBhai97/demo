package com.example.BackEnd;

import org.json.JSONObject;

public class Complaint {
    int patID;
    String description;
    String reason; 

    public Complaint(int patID, String details) {
        this.patID = patID;

        JSONObject json = new JSONObject(details);
        this.description = json.getString("description");
        this.reason = json.getString("reason");
    }
}