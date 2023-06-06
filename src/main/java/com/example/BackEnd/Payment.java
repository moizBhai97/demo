package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Payment {
    
    private String date;
    private String time;
    private double amount;
    private Boolean status;

    public Payment()
    {
        date = "";
        time = "";
        amount = 0;
    }

    public Payment(String date, String time, double amount, Boolean status)
    {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.status = status;
    }

    public Payment(String info)
    {
        JSONObject obj = new JSONObject(info);
        date = obj.getString("date");
        time = obj.getString("time");
        amount = obj.getDouble("amount");
        status = obj.getBoolean("status");
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public String get(String key)
    {
        if(key.equals("date"))
            return date;
        else if(key.equals("time"))
            return time;
        else if(key.equals("amount"))
            return amount + "";
        else if(key.equals("status"))
            return status + "";
        else
            return "NULL";
    }

    public String toString()
    {
        try
        {
            JSONParser parser = new JSONParser();

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Payment.json")).toString());
            
            Set<String> keyset = obj.keySet(); //Get all keys of jsonObject

            for(String key : keyset) //Loop through all keys
            {
                if(!get(key).equals("NULL"))
                    obj.put(key, get(key)); //get value of an element in jsonObject using key
            }

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + "Payment" + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }
}
