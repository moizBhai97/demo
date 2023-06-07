package com.example.BackEnd;

import org.json.JSONObject;

public class PatientHistory {
    
    //private int sid;
    private String type;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PatientHistory(){}

    public PatientHistory(String type, String description)
    {
        this.type = type;
        this.description = description;
    }

    public PatientHistory(String info)
    {
        JSONObject obj = new JSONObject(info);

        if(obj.has("sid"))
        {
            //this.sid = obj.getInt("sid");
            this.type = obj.getString("type");
            this.description = obj.getString("description");
        }
        else
        {
            this.type = obj.getString("type");
            this.description = obj.getString("description");
        }
    }

    @Override
    public String toString()
    {
        JSONObject obj = new JSONObject();

        //obj.put("sid", sid);
        obj.put("type", type);
        obj.put("description", description);

        return obj.toString();
    }
}
