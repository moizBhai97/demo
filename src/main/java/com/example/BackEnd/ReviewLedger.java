package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReviewLedger {

    private List<Review> reviews;
    private DBFactory dbFactory;

    public ReviewLedger()
    {
        dbFactory = DBFactory.getInstance();
        reviews = new ArrayList<>();
    }

    public ReviewLedger(int docId)
    {
        try
        {
            dbFactory = DBFactory.getInstance();
            populateReviews(docId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void populateReviews(int docId)
    {
        try
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
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getReviewList(int docId)
    {
        try
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
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
        
    }

    public String getAvgRating()
    {
        try
        {
            JSONObject obj = new JSONObject();

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

            obj.put("checkupRating", checkupRating*(100/5));
            obj.put("environmentRating", environmentRating*(100/5));
            obj.put("staffRating", staffRating*(100/5));
            obj.put("rating", experience);
            obj.put("stats", stats*(100/5));
            obj.put("reviews", reviews.size());

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public void addReview(String info, int patId, int docId)
    {
        JSONObject obj = new JSONObject(info);
        obj.put("patId", patId);
        Review review = new Review(obj.toString());

        try
        {
            DBFactory.getInstance().createHandler("SQL").addReview(info, patId, docId);
            reviews.add(review);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
