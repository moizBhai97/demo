package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Review {
    
    private float experience;
    private String comment;
    private Boolean recommend;
    private float checkupRating;
    private float environmentRating;
    private float staffRating;
    private int patId;

    public Review()
    {
        experience = 0;
        comment = "";
        recommend = false;
        checkupRating = 0;
        environmentRating = 0;
        staffRating = 0;
        patId = 0;
    }

    public Review(float experience, String comment, Boolean recommend, float checkupRating, float environmentRating, float clinicRating, int patId)
    {
        this.experience = experience;
        this.comment = comment;
        this.recommend = recommend;
        this.checkupRating = checkupRating;
        this.environmentRating = environmentRating;
        this.staffRating = clinicRating;
        this.patId = patId;
    }

    public Review(String info)
    {
        JSONObject obj = new JSONObject(info);
        experience = obj.getFloat("experience");
        comment = obj.getString("comment");
        recommend = obj.getBoolean("recommend");
        checkupRating = obj.getFloat("checkupRating");
        environmentRating = obj.getFloat("environmentRating");
        staffRating = obj.getFloat("staffRating");
        patId = obj.getInt("patId");
    }

    public float getExperience() {
        return experience;
    }

    public float getCheckupRating() {
        return checkupRating;
    }

    public float getEnvironmentRating() {
        return environmentRating;
    }

    public float getStaffRating() {
        return staffRating;
    }

    public String get(String value)
    {
        if(value.equals("experience"))
            return String.valueOf(experience);
        else if(value.equals("comment"))
            return comment;
        else if(value.equals("recommend"))
            return String.valueOf(recommend);
        else if(value.equals("checkupRating"))
            return String.valueOf(checkupRating);
        else if(value.equals("environmentRating"))
            return String.valueOf(environmentRating);
        else if(value.equals("staffRating"))
            return String.valueOf(staffRating);
        else if(value.equals("patId"))
            return String.valueOf(patId);
        else
            return "NULL";
    }

    @Override
    public String toString()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Review.json")).toString());

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
