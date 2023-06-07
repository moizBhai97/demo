package com.example.BackEnd;

import org.json.JSONObject;

public class PatientHistory {

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


    public PatientHistory(String type, String description)
    {
        this.type = type;
        this.description = description;
    }

    public PatientHistory(String info)
    {
        try
        {
            JSONObject obj = new JSONObject(info);

            this.type = obj.getString("type");
            this.description = obj.getString("description");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        try
        {
            JSONObject obj = new JSONObject();

            obj.put("type", this.type);
            obj.put("description", this.description);

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
