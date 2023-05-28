package com.example.BackEnd;

import org.json.JSONObject;

public class Review {
    
    private String experience;
    private String comment;
    private Boolean recommend;
    private float checkupRating;
    private float environmentRating;
    private float clinicRating;

    public Review()
    {
        experience = "";
        comment = "";
        recommend = false;
        checkupRating = 0;
        environmentRating = 0;
        clinicRating = 0;
    }

    public Review(String experience, String comment, Boolean recommend, float checkupRating, float environmentRating, float clinicRating)
    {
        this.experience = experience;
        this.comment = comment;
        this.recommend = recommend;
        this.checkupRating = checkupRating;
        this.environmentRating = environmentRating;
        this.clinicRating = clinicRating;
    }

    public Review(String info)
    {
        JSONObject obj = new JSONObject(info);
        experience = obj.getString("experience");
        comment = obj.getString("comment");
        recommend = obj.getBoolean("recommend");
        checkupRating = obj.getFloat("checkupRating");
        environmentRating = obj.getFloat("environmentRating");
        clinicRating = obj.getFloat("clinicRating");
    }
}
