package com.example.BackEnd;

import org.json.JSONObject;

public class PatientHistory {
    
    private String type;
    private String description;

    public PatientHistory(){}

    public PatientHistory(String type, String description)
    {
        this.type = type;
        this.description = description;
    }

    public PatientHistory(String info)
    {
        JSONObject obj = new JSONObject(info);

        this.type = obj.getString("type");
        this.description = obj.getString("description");
    }
}
