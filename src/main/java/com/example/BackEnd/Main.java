package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
           //org.json.* is also powerfull you guyz can explore
           //both libraries are included in the project module info

            JSONParser parser = new JSONParser(); 

            JSONArray objs = (JSONArray) parser.parse(new FileReader("Moiz.json")); //parse json file and return as jsonArray
            
            for(int i=0; i<objs.size(); i++)
            {
                JSONObject obj = (JSONObject) objs.get(i); //get jsonObject from jsonArray
                
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
        
        //get json object(implmented in Appointment.java "getDetails()" )
        JSONObject obj = pc.bookAppointment(3, "2020-12-10", "12:00:00", "Heart", 1);
        
        //print a json object
        printJson(obj);
        
        //read array of values from json
        readJson();
    }
    
}
