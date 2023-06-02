package com.example.BackEnd;

import org.json.JSONObject;

public class Certificate {

    private String name;
    private String approvedStatus;
    private String issueDate;
    private String expiryDate;
    
    
    public Certificate(String info)
    {
        JSONObject json = new JSONObject(info);

        this.name = json.getString("name");
        this.issueDate = json.getString("issueDate");
        this.expiryDate = json.getString("expiryDate");
        this.approvedStatus = "Approved";

    }

    public String toString()
    {
        JSONObject obj = new JSONObject();

        obj.put("name", this.name);
        obj.put("issueDate", this.issueDate);
        obj.put("expiryDate", this.expiryDate);
        obj.put("approvedStatus", this.approvedStatus);

        return obj.toString();
    }
}
