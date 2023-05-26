package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
 

public class Appointment {
    
    private String date;
    private String time;
    private String problem;
    private int patId;

    Appointment()
    {
        this.date = "";
        this.time = "";
        this.problem = "";
        this.patId = 0;
    }

    Appointment(String date, String time, String problem, int patId)
    {
        this.date = date;
        this.time = time;
        this.problem = problem;
        this.patId = patId;
    }

    public String get(String value)
    {
        if(value.equals("date"))
            return date;
        
        else if(value.equals("time"))
            return time;
        
        else if(value.equals("problem"))
            return problem;
        
        else if(value.equals("patId"))
            return patId + "";

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
            
            String Obj = obj.toString();

            return Obj;

        }
        catch(Exception e)
        {
            System.out.println(e);

            return null;
        }

        // String s="";
        // s=s+date+"\n";
        // s=s+time+"\n";
        // s=s+problem+"\n";
        // s=s+patId+"\n";

        // return s;
    } 
}