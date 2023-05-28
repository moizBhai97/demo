package com.example.BackEnd;

import org.json.JSONObject;

public class Payment {
    
    private String date;
    private String time;
    private double amount;

    public Payment()
    {
        date = "";
        time = "";
        amount = 0;
    }

    public Payment(String date, String time, double amount)
    {
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public Payment(String info)
    {
        JSONObject obj = new JSONObject(info);
        date = obj.getString("date");
        time = obj.getString("time");
        amount = obj.getDouble("amount");
    }
}
