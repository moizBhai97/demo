package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.example.BackEnd.Doctor;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class SQL extends DBHandler{

    public void saveAppointment(String info, int patId)
    {
        try
        {
            JSONObject obj = new JSONObject(info);
            System.out.println(patId + " " + obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("problem") + " " + obj.getString("docId") + " " + "Booked");
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void updateAppointment(int appId, String Reason, int value)
    {
        try
        {
            if(value == 1)
            {
                System.out.println(appId+ " " + Reason + " " + "Canceled");
            }
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }
    
    public String getDummyDoctor(String name){
        ArrayList<Doctor> dummyDoctors = new ArrayList<Doctor>();
        String[] specializations = {"Cardiology", "Dermatology", "Endocrinology", "Gastroenterology", "Hematology", "Neurology", "Oncology", "Pediatrics", "Psychiatry", "Urology"};
        String[] hospitals = {"Mayo Clinic", "Johns Hopkins Hospital", "Cleveland Clinic", "Massachusetts General Hospital", "UCSF Medical Center", "Brigham and Women's Hospital", "New York-Presbyterian Hospital", "Stanford Health Care-Stanford Hospital", "Hospitals of the University of Pennsylvania-Penn Presbyterian", "Cedars-Sinai Medical Center"};
        for(int i = 0; i < 10; i++){
            Doctor doctor = new Doctor();
            doctor.setName(name + " " + i);
            doctor.setSpecialization(specializations[i]);
            doctor.setExperience((i + 1) * 5 + " years");
            doctor.setRating( (Math.random() * 5));
            doctor.setLocation(hospitals[i]);
            dummyDoctors.add(doctor);
        }
        //create a doctors json object and save it in the json folder
        JSONArray doctors = new JSONArray();
        for(int i = 0; i < dummyDoctors.size(); i++){
            doctors.put(new JSONObject(dummyDoctors.get(i).toString() ));
        }
        System.out.println(doctors.toString());
        return doctors.toString();

    }

    public String getDoctors(String name){
        System.out.println("SQL getDoctors");
        return getDummyDoctor(name);

        //
    }

    public String getPatient(String info){
        
        
        try
        {
            System.out.println("SQL getPatient");
            // query the database and return the patient info if username and password matches

            
            JSONParser parser = new JSONParser(); 
            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Patient.json")).toString());
            Set<String> keyset = obj.keySet();      //gets all keys from .json
            

            // for(String key : keyset)
            // {
            //     if(!get(key).equals("NULL"))
            //         obj.put(key, get(key));            //put values in json object
            // }
            
            // dummy data
            obj.put("patId", "1");
            obj.put("name", "Musa");
            obj.put("email", "musa@gmail.com");
            obj.put("DOB", "1/1/2001");
            obj.put("phoneNumber", "03369420888");
            obj.put("gender", "Female");

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }

    }

    // public static void main(String[] args) throws Exception {
    //     String connectionUrl = "jdbc:sqlserver://BOREDAF\\SQLEXPRESS;" +
    //                 "databaseName=DB_Lab13;" +
    //                 "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
    //         try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
    //             String SQL = "SELECT * FROM tb1Employee";
    //             ResultSet rs = stmt.executeQuery(SQL);
    //             //print all columns and rows in resultset


    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //   }
}