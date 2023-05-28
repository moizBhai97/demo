package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.example.BackEnd.Doctor;
import com.example.UIController.DoctorTemp;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
            System.out.println(e + " " + getClass().getName());
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
            System.out.println(e + " " + getClass().getName());
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
