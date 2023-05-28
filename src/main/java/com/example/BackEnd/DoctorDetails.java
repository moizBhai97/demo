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

    public DoctorDetails(int docId)
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

        reviewLedger = new ReviewLedger(docId);
    }

    public DoctorDetails(String specialization, String description, String location, int stats, int patients, int experience, float rating, String services, String start, String end, float fee, int docId)
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

        reviewLedger = new ReviewLedger(docId);
    }

    public DoctorDetails(String info, int docId)
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

            reviewLedger = new ReviewLedger(docId);
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
            System.out.println("hi");
            //System.out.println(obj.toString());

            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/DoctorDetails.json")).toString());

            // Set<String> keyset = obj.keySet();
            
            // for(String key : keyset)
            // {
            //     if(!get(key).equals("NULL"))
            //         obj.put(key, get(key));
            // }

            obj.put("specialization", "Dentist");
            obj.put("description", "Dr. Moiz is dentist specialist in NUCES hospital. He is available for private consultation.");
            obj.put("location", "NUCES Hospital");
            obj.put("patients", 100);
            obj.put("experience", 3);
            obj.put("services", "Dental Checkup\nDental Implant\nDental Surgery");
            obj.put("start", "09:00");
            obj.put("end", "17:00");
            obj.put("fee", "1000");

            String details = reviewLedger.getAvgRating(obj.toString());

            return details;
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    } 
}
