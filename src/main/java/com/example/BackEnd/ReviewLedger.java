package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReviewLedger {

    private List<Review> reviews;
    private DBFactory dbFactory;

    public ReviewLedger(int docId)
    {
        dbFactory = DBFactory.getInstance();
        populateReviews(docId);
    }

    public void populateReviews(int docId)
    {
        JSONArray objs = new JSONArray(dbFactory.createHandler("SQL").getReviewList(docId));

        reviews = new ArrayList<>();

        for(int i = 0; i < objs.length(); i++)
        {
            JSONObject obj = objs.getJSONObject(i);

            Review review = new Review(obj.toString());

            reviews.add(review);
        }
    }

    public String getReviewList(int docId)
    {
        populateReviews(docId);

        JSONArray objs = new JSONArray();
            
        for(int i = 0; i < reviews.size(); i++ )
        {
            JSONObject obj = new JSONObject(reviews.get(i).toString());
            
            objs.put(obj);
            
        }

        return objs.toString();
        
    }

    public String getAvgRating(String info)
    {

        System.out.println("getAvgRating: ");
        JSONObject obj = new JSONObject(info);

        float checkupRating = 0;
        float environmentRating = 0;
        float staffRating = 0;
        float experience = 0;

        for(Review review : reviews)
        {
            checkupRating += review.getCheckupRating();
            environmentRating += review.getEnvironmentRating();
            staffRating += review.getStaffRating();
            experience += review.getExperience();
        }

        checkupRating /= reviews.size();
        environmentRating /= reviews.size();
        staffRating /= reviews.size();
        experience /= reviews.size();

        float stats = (float)((checkupRating + environmentRating + staffRating) / 3.0);

        obj.put("checkupRating", checkupRating);
        obj.put("environmentRating", environmentRating);
        obj.put("staffRating", staffRating);
        obj.put("rating", experience);
        obj.put("stats", stats);
        obj.put("reviews", reviews.size());

        return obj.toString();
    }
    
}