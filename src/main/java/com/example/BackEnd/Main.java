package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.simple.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    
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

    public static void readJson()
    {
        try
        {
           //using json.simple.* a simple way to parse read and write json files and json objects
           //using org.json.* for manipulating simple json objects
           //to convert from one json type to another using string
           //both libraries are included in the project module info
           //There is also gson,jackson library included if you want more advanced options

            JSONParser parser = new JSONParser(); 

            JSONArray jsonArray = new JSONArray(parser.parse(new FileReader("Moiz.json")).toString());

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject obj = (JSONObject) jsonArray.get(i); //get jsonObject from jsonArray
                
                System.out.println();
                
                //Print json object
                printJson(obj);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        
        PatientController pc = new PatientController();
        
        //get jsonstring (implmented in Appointment.java "getDetails()") and convert to jsonObject
        JSONObject obj = new JSONObject(pc.bookAppointment(3, "2020-12-10", "12:00:00", "Heart", 1));
        
        //print a json object
        printJson(obj);
        
        //read array of values from json
        readJson();
    }
    
}
