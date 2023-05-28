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
    private String workingHours;
    private float fee;
    private Boolean availability;

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
        this.workingHours = "NULL";
        this.fee = 0;
        this.availability = false;

        reviewLedger = new ReviewLedger();
    }

    public DoctorDetails(String specialization, String description, String location, int stats, int patients, int experience, float rating, String services, String workingHours, float fee, Boolean avail, int docId)
    {
        this.specialization = specialization;
        this.description = description;
        this.location = location;
        this.stats = stats;
        this.patients = patients;
        this.experience = experience;
        this.rating = rating;
        this.services = services;
        this.workingHours = workingHours;
        this.fee = fee;
        this.availability = avail;

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
            this.workingHours = obj.getString("workingHours");
            this.fee = obj.getFloat("fee");
            this.availability = obj.getBoolean("availability");

            reviewLedger = new ReviewLedger(docId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public ReviewLedger getReviewLedger()
    {
        return reviewLedger;
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
        
        else if(value.equals("workingHours"))
            return workingHours;
        
        else if(value.equals("fee"))
            return fee + "";
        
        else if(value.equals("availability"))
            return availability + "";

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

            String s = DBFactory.getInstance().createHandler("SQL").getDoctorDetails(101);

            JSONObject obj1 = new JSONObject(s);

            obj.put("name", obj1.getString("name"));
            obj.put("specialization", obj1.getString("specialization"));
            obj.put("description", obj1.getString("description"));
            obj.put("location", obj1.getString("location"));
            obj.put("patients", obj1.getInt("patients"));
            obj.put("experience", obj1.getInt("experience"));
            obj.put("services", obj1.getString("services"));
            obj.put("workingHours", obj1.getString("workingHours"));
            obj.put("fee", obj1.getFloat("fee"));
            obj.put("availability", obj1.getString("availability"));

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
