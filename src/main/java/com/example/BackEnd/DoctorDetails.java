package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONArray;
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
    private String schedule;

    private ReviewLedger reviewLedger;
    private CertificateLedger certificateLedger;

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
        certificateLedger = new CertificateLedger();
    }

    public DoctorDetails(String info, int docId)
    {
        try
        {
            reviewLedger = new ReviewLedger(docId);
            certificateLedger = new CertificateLedger(docId);

            JSONObject obj = new JSONObject(info);

            JSONObject Rating = new JSONObject(reviewLedger.getAvgRating());

            this.specialization = obj.getString("specialization");
            this.description = obj.getString("description");
            this.location = obj.getString("location");
            this.stats = Rating.getInt("stats");
            this.patients = obj.getInt("patients");
            this.experience = obj.getInt("experience");
            this.rating = Rating.getFloat("rating");
            this.services = obj.getString("services");
            this.workingHours = obj.getString("workingHours");
            this.fee = obj.getFloat("fee");
            this.availability = obj.getString("availability");
            this.checkupRating = Rating.getFloat("checkupRating");
            this.environmentRating = Rating.getFloat("environmentRating");
            this.staffRating = Rating.getFloat("staffRating");
            this.reviews = Rating.getInt("reviews");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public CertificateLedger getCertiLedger()
    {
        return certificateLedger;
    }

    public void addCertification(String info, int docId) throws Exception
    {
        try
        {
            certificateLedger.addCertification(info, docId);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }          
    }

    public String getCertificates()
    {
        try
        {
            return certificateLedger.getCertificates();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getSpecialization()
    {
        return specialization;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLocation()
    {
        return location;
    }

    public int getStats()
    {
        return stats;
    }

    public int getPatients()
    {
        return patients;
    }

    public int getExperience()
    {
        return experience;
    }

    public float getRating()
    {
        return rating;
    }

    public String getServices()
    {
        return services;
    }

    public String getWorkingHours()
    {
        return workingHours;
    }

    public float getFee()
    {
        return fee;
    }

    public String getAvailability()
    {
        return availability;
    }

    public float getCheckupRating()
    {
        return checkupRating;
    }

    public float getEnvironmentRating()
    {
        return environmentRating;
    }

    public float getStaffRating()
    {
        return staffRating;
    }

    public int getReviews()
    {
        return reviews;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setStats(int stats)
    {
        this.stats = stats;
    }

    public void setPatients(int patients)
    {
        this.patients = patients;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public void setServices(String services)
    {
        this.services = services;
    }

    public void setWorkingHours(String workingHours)
    {
        this.workingHours = workingHours;
    }

    public void setFee(float fee)
    {
        this.fee = fee;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public void setCheckupRating(float checkupRating)
    {
        this.checkupRating = checkupRating;
    }

    public void setEnvironmentRating(float environmentRating)
    {
        this.environmentRating = environmentRating;
    }

    public void setStaffRating(float staffRating)
    {
        this.staffRating = staffRating;
    }

    public void setReviews(int reviews)
    {
        this.reviews = reviews;
    }

    public ReviewLedger getReviewLedger()
    {
        return reviewLedger;
    }

    public String getReviewList(int docId)
    {
        return reviewLedger.getReviewList(docId);
    }

    public void addReview(String info, int patId, int docId)
    {
        try{
            reviewLedger.addReview(info, patId, docId);

            String rating = reviewLedger.getAvgRating();
            
            JSONObject obj = new JSONObject(rating);
            this.rating = obj.getFloat("rating");
            this.checkupRating = obj.getFloat("checkupRating");
            this.environmentRating = obj.getFloat("environmentRating");
            this.staffRating = obj.getFloat("staffRating");
            this.stats = obj.getInt("stats");
            this.reviews = obj.getInt("reviews");    
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDayTime(String time) 
    {
        // Assuming day time slots are from 8 AM to 5 PM
        return time.compareTo("08:00") >= 0 && time.compareTo("17:00") < 0;
    }
    
    private boolean isNightTime(String time) 
    {
        //Assuming night time slots are from 5 PM to 11 PM
        return time.compareTo("17:00") >= 0 && time.compareTo("23:00") < 0;
    }

    public String getSchedule(int docId, String date, int value)
    {
        try 
        {
            String json = DBFactory.getInstance().createHandler("SQL").getSchedule(docId, date);

            this.schedule = json;

            JSONArray arr = new JSONArray(json);

            JSONArray filtered = new JSONArray();

            for(int i = 0; i < arr.length(); i++)
            {
                JSONObject obj = arr.getJSONObject(i);

                if(isDayTime(obj.getString("time")) && date.equals(obj.getString("date")) && value == 1)
                    filtered.put(obj);

                else if(isNightTime(obj.getString("time")) && date.equals(obj.getString("date")) && value == 2)
                    filtered.put(obj);
            }

            return filtered.toString();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
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
            e.printStackTrace();
            return null;
        }
    } 
}
