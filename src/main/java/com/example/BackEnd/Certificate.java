package com.example.BackEnd;

import org.json.JSONObject;

public class Certificate {

    private String name;
    private String approvedStatus;
    private String issueDate;
    private String expiryDate;
    
    
    public Certificate(String info)
    {
        try
        {
            JSONObject json = new JSONObject(info);

            this.name = json.getString("name");
            this.issueDate = json.getString("issueDate");
            this.expiryDate = json.getString("expiryDate");
            this.approvedStatus = json.getString("approvedStatus");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public String toString()
    {
        try
        {
            JSONObject obj = new JSONObject();

            obj.put("name", this.name);
            obj.put("issueDate", this.issueDate);
            obj.put("expiryDate", this.expiryDate);
            obj.put("approvedStatus", this.approvedStatus);

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
