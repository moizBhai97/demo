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
    private String availability;
    private float checkupRating;
    private float environmentRating;
    private float staffRating;
    private int reviews;

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
        this.availability = "NULL";
        this.checkupRating = 0;
        this.environmentRating = 0;
        this.staffRating = 0;
        this.reviews = 0;

        reviewLedger = new ReviewLedger();
    }

    public DoctorDetails(String specialization, String description, String location, int stats, int patients, int experience, float rating, String services, String workingHours, float fee, String avail, float checkupRating, float environmentRating, float staffRating, int reviews, int docId)
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
        this.checkupRating = checkupRating;
        this.environmentRating = environmentRating;
        this.staffRating = staffRating;
        this.reviews = reviews;

        reviewLedger = new ReviewLedger(docId);
    }

    public DoctorDetails(String info, int docId)
    {
        try
        {
            reviewLedger = new ReviewLedger(docId);

            info = reviewLedger.getAvgRating(info);

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
            this.availability = obj.getString("availability");
            this.checkupRating = obj.getFloat("checkupRating");
            this.environmentRating = obj.getFloat("environmentRating");
            this.staffRating = obj.getFloat("staffRating");
            this.reviews = obj.getInt("reviews");
        }
        catch(Exception e)
        {
            System.out.println(e + "\nError in DoctorDetails constructor.");
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
        
        else if(value.equals("checkupRating"))
            return checkupRating + "";
        
        else if(value.equals("environmentRating"))
            return environmentRating + "";
        
        else if(value.equals("staffRating"))
            return staffRating + "";

        else if(value.equals("reviews"))
            return reviews + "";

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
