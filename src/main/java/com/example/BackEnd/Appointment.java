package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Appointment {
    
    private String date;
    private String time;
    private String problem;
    private int docId;
    private String status;

    Appointment()
    {
        this.date = "";
        this.time = "";
        this.problem = "";
        this.docId = 0;
        this.status = "Booked";
    }

    Appointment(String date, String time, String problem, int docId)
    {
        this.date = date;
        this.time = time;
        this.problem = problem;
        this.docId = docId;
        this.status = "Booked";
    }

    Appointment(String info)
    {
        JSONObject obj = new JSONObject(info);

        this.date = obj.getString("date");
        this.time = obj.getString("time");
        this.problem = obj.getString("problem");
        this.docId = obj.getInt("docId");
        this.status = "Booked";
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public String get(String value)
    {
        if(value.equals("date"))
            return date;
        
        else if(value.equals("time"))
            return time;
        
        else if(value.equals("problem"))
            return problem;

        else if(value.equals("status"))
            return status;
        
        else if(value.equals("docId"))
            return docId + "";

        return "NULL";
    }

    public String getDetails()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Appointment.json")).toString());

            Set<String> keyset = obj.keySet();
            
            for(String key : keyset)
            {
                if(!get(key).equals("NULL"))
                    obj.put(key, get(key));            //put values in json object
            }

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + " " + getClass().getName());

            return null;
        }
    } 
}