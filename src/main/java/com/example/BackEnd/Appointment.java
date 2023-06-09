package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Appointment {
    
    private int appId;
    private String date;
    private String time;
    private String problem;
    private int docId;
    private int patId;
    private String status;

    private Payment payment;

    public Appointment()
    {
        this.appId = 0;
        this.date = "";
        this.time = "";
        this.problem = "";
        this.docId = 0;
        this.patId = 0;
        this.status = "Booked";
        this.payment = null;
    }

    public Appointment(String info, int patId, int appId)
    {
        try
        {
            JSONObject obj = new JSONObject(info);
        
            this.appId = appId;
            this.date = obj.getString("date");
            this.time = obj.getString("time");
            this.problem = obj.getString("problem");
            this.docId = obj.getInt("docId");
            this.patId = patId;
            this.status = "Booked";
            this.payment = new Payment(obj.getJSONObject("payment").toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Appointment(String info)
    {
        try
        {
            JSONObject obj = new JSONObject(info);
        
            this.appId = obj.getInt("appId");
            this.date = obj.getString("date");
            this.time = obj.getString("time");
            this.problem = obj.getString("problem");
            this.docId = obj.getInt("docId");
            this.patId = obj.getInt("patId");
            this.status = obj.getString("status");
            this.payment = new Payment(obj.getJSONObject("payment").toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getAppId()
    {
        return appId;
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public String getStatus()
    {
        return status;
    }

    public void verifyPayment()
    {
        try
        {
            DBFactory.getInstance().createHandler("SQL").updatePayment(appId);
            payment.setStatus(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String get(String value)
    {
        if(value.equals("appId"))
            return appId + "";
        
        else if(value.equals("date"))
            return date;
        
        else if(value.equals("time"))
            return time;
        
        else if(value.equals("problem"))
            return problem;

        else if(value.equals("status"))
            return status;
        
        else if(value.equals("docId"))
            return docId + "";

        else if(value.equals("patId"))
            return patId + "";

        return "NULL";
    }

    public void update(String info)
    {
        try
        {
            JSONObject obj = new JSONObject(info);

            date = obj.getString("date");
            time = obj.getString("time");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Appointment.json")).toString());

            Set<String> keyset = obj.keySet();
            
            for(String key : keyset)
            {
                if(!get(key).equals("NULL"))
                    obj.put(key, get(key));
            }

            obj.put("payment", new JSONObject(payment.toString()));

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    } 
}