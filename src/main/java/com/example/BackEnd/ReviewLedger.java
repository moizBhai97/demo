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

    public String getReviewList(int docId)
    {
        JSONArray objs = new JSONArray(dbFactory.createHandler("SQL").getReviewList(docId));

        for(int i = 0; i < objs.length(); i++)
        {
            JSONObject obj = objs.getJSONObject(i);

            Review review = new Review(obj.toString());

            reviews.add(review);
        }

        return objs.toString();
        
    }
    
}
