package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.example.BackEnd.Doctor;
import com.example.UIController.DoctorTemp;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            obj.put("status", "Booked");
            System.out.println(patId + " " + obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("problem") + " " + obj.getString("docId") + " " + "Booked");
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void updateAppointment(int appId, String info, int value)
    {
        try
        {
            if(value == 1)
            {
                System.out.println(appId+ " " + info + " " + "Canceled");
            }

            else if(value == 2)
            {
                JSONObject obj = new JSONObject(info);
                System.out.println(obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("reason"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getReviewList(int docId)
    {
        try
        {
            //ResultSet rs = null;
            
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Review.json")).toString());

            Set<String> keyset = obj.keySet();

            //get array of reviews from database
            JSONArray reviews = new JSONArray();
            for(int i = 0; i < 3; i++)
            {
                JSONObject temp = new JSONObject();
                temp.put("comment", "Good");
                temp.put("experience", (int)(Math.random() * 5) + 1);
                temp.put("recommend", true);
                temp.put("checkupRating", (int)(Math.random() * 100) + 1);
                temp.put("environmentRating", (int)(Math.random() * 100) + 1);
                temp.put("staffRating", (int)(Math.random() * 100) + 1);
                reviews.put(temp);
            }

            // for(String key : keyset)
            // {
            //     if(rs.getString(key) != null)
            //         obj.put(key, rs.getString(key));
            // }

            System.out.println(reviews.toString());

            return reviews.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public void addPayment(String info, int appId)
    {
        try
        {
            JSONObject obj = new JSONObject(info);
            System.out.println(obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("amount") + " " + appId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }
    
public String getDummyDoctor(String name){
    ArrayList<Doctor> dummyDoctors = new ArrayList<Doctor>();
    String[] specializations = {"Cardiologist", "Dermatologist", "Dentist", "Psychiatrist"};
    String[] hospitals = {"Mayo Clinic", "Johns Hopkins Hospital", "Cleveland Clinic", "Massachusetts General Hospital", "UCSF Medical Center", "Brigham and Women's Hospital", "New York-Presbyterian Hospital", "Stanford Health Care-Stanford Hospital", "Hospitals of the University of Pennsylvania-Penn Presbyterian", "Cedars-Sinai Medical Center"};
    List<DoctorTemp> doctorTemps = new ArrayList<DoctorTemp>();
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        
        
        //generate random doctorTemps
        for(int i=0;i<10;i++){
            StringBuilder sb = new StringBuilder();
            sb.append("M ");
            for (int j = 0; j < 10; j++) {
                sb.append(chars[(int) (Math.random() * chars.length)]);
            }
        Doctor doctor = new Doctor();
        doctor.setName(sb.toString());
        doctor.setSpecialization(specializations[i % 4] );
        doctor.setExperience((i + 1) * 5 + " years");
        doctor.setRating( (Math.random() * 5));
        doctor.setLocation(hospitals[i]);
        doctor.setPrice((i + 1) * 100);
        dummyDoctors.add(doctor);
    }
    //create a doctors json object and save it in the json folder
    JSONArray doctors = new JSONArray();
    for(int i = 0; i < dummyDoctors.size(); i++){
        doctors.put(new JSONObject(dummyDoctors.get(i).toString() ));
    }
   // System.out.println(doctors.toString());
    return doctors.toString();

}

    public String getDoctors(String name){
        System.out.println("SQL getDoctors");
        return getDummyDoctor(name);

        //
    }

    public String getTopDoctors()
    {
        try
        {
            System.out.println("SQL getTopDoctors");
            // query the database and return the top doctors

            JSONArray doctors = new JSONArray();
            JSONParser parser = new JSONParser();

            for(int i = 0; i < 4; i++)
            {
                JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());
                
                obj.put("id", i + 1);
                obj.put("name", "Musa" + (i+1));
                obj.put("specialization", "Heart");
                obj.put("experience", i+5 + " years");
                obj.put("rating", "5");
                obj.put("location", "Lahore");
                obj.put("price", "500");

                doctors.put(obj);
            }

            return doctors.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
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

            System.out.println(obj.toString());
            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }

    }

    public String getAppointments(int patId)
    {
        try{

            System.out.println("SQL getAppointments");
            // query the database and return the appointments of the patient
            
            JSONArray appointments = new JSONArray();
            JSONParser parser = new JSONParser();

            for(int i = 0; i < 3; i++)
            {
                JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Appointment.json")).toString());
                
                obj.put("date", "1/1/2001");
                obj.put("time", "02:00");
                obj.put("problem", "Heart");
                obj.put("status", "Booked");
                obj.put("docId", i + 1);

                appointments.put(obj);
            }

            return appointments.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-NO4AAI8\\SQLEXPRESS;" +
                    "databaseName=SDA;" +
                    "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
                String SQL = "SELECT * FROM Patients";
                ResultSet rs = stmt.executeQuery(SQL);
                //print all columns and rows in resultset


            } catch (SQLException e) {
                e.printStackTrace();
            }
      }

    public void addComplaint(int patID, String details) {
        try
        {
            System.out.println("SQL addComplaint");
            // query the database and add the complaint of the patient
            System.out.println(patID + " " + details);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }
}