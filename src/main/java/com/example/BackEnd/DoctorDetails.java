package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class DoctorDetails {

    private String specialization;
    private String description;
    private String location;
    private int stats;
    private int patients;
    private int experience;
    private float rating;
    private String services;
    private String start;
    private String end;
    private float fee;

    private ReviewLedger reviewLedger;

    public DoctorDetails()
    {
        this.specialization = "NULL";
        this.description = "NULL";
        this.location = "NULL";
        this.stats = 0;
        this.patients = 0;
        this.experience = 0;
        this.rating = 0;
        this.services = "NULL";
        this.start = "NULL";
        this.end = "NULL";
        this.fee = 0;
    }

    public DoctorDetails(String specialization, String description, String location, int stats, int patients, int experience, float rating, String services, String start, String end, float fee)
    {
        this.specialization = specialization;
        this.description = description;
        this.location = location;
        this.stats = stats;
        this.patients = patients;
        this.experience = experience;
        this.rating = rating;
        this.services = services;
        this.start = start;
        this.end = end;
        this.fee = fee;
    }

    public DoctorDetails(String info)
    {
        try
        {
            JSONObject obj = new JSONObject(info);

            this.specialization = obj.getString("specialization");
            this.description = obj.getString("description");
            this.location = obj.getString("location");
            this.stats = obj.getInt("stats");
            this.patients = obj.getInt("patients");
            this.experience = obj.getInt("experience");
            this.rating = obj.getFloat("rating");
            this.services = obj.getString("services");
            this.start = obj.getString("start");
            this.end = obj.getString("end");
            this.fee = obj.getFloat("fee");
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getReviewList(int docId)
    {
        return reviewLedger.getReviewList(docId);
    }


    public String get(String value)
    {
        if(value.equals("specialization"))
            return specialization;
        
        else if(value.equals("description"))
            return description;
        
        else if(value.equals("location"))
            return location;

        else if(value.equals("stats"))
            return stats + "";
        
        else if(value.equals("patients"))
            return patients + "";
        
        else if(value.equals("experience"))
            return experience + "";
        
        else if(value.equals("rating"))
            return rating + "";
        
        else if(value.equals("services"))
            return services;
        
        else if(value.equals("start"))
            return start;
        
        else if(value.equals("end"))
            return end;
        
        else if(value.equals("fee"))
            return fee + "";

        else return "NULL";
    }

    @Override
    public String toString()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/DoctorDetails.json")).toString());

            Set<String> keyset = obj.keySet();
            
            for(String key : keyset)
            {
                if(!get(key).equals("NULL"))
                    obj.put(key, get(key));
            }

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    } 
}
