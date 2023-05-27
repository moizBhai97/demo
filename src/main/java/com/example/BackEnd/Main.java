package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.simple.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    
    public static void printJsonArray(JSONArray objs)
    {
        if(objs != null)
        {
            for(int i=0; i<objs.length(); i++)
            {
                JSONObject obj = (JSONObject) objs.get(i);

                System.out.println();
                
                //Print json object
                printJson(obj);
            }
        }
    }
    
    public static void printJson(JSONObject obj)
    {
        if(obj != null)
        {
            Set<String> keyset = obj.keySet(); //Get all keys of jsonObject
                
            for(String key : keyset) //Loop through all keys
            {
                System.out.println(key + ": " + obj.get(key)); //get value of an element in jsonObject using key
            }
        }
    }

    public static String getInfo()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Appointment.json")).toString());

            obj.put("date", "2020-12-10");
            obj.put("time", "12:00:00");
            obj.put("problem", "Heart");
            obj.put("docId", "3");
            

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e);

            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        
        PatientController pc = new PatientController();

        pc.bookAppointment(getInfo(), 1);

        JSONArray objs = new JSONArray(pc.getAppointList(1));

        printJsonArray(objs);

        pc.cancelAppointment("Im am in love", 1, 0);

        //using json.simple.* a simple way to parse read and write json files and json objects
        //using org.json.* for manipulating simple json objects
        //to convert from one json type to another using string
        //both libraries are included in the project module info
        //There is also gson,jackson library included if you want more advanced options
    }
    
}
