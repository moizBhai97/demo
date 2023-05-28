package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.example.BackEnd.Doctor;
import com.example.UIController.DoctorTemp;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SQL extends DBHandler {
    String connectionUrl;

    public SQL() {

         connectionUrl = "jdbc:sqlserver://BOREDAF\\SQLEXPRESS;" +
                "databaseName=SDA;" +
                "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
    }

  
    public int saveAppointment(String info, int patId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            String SQL = "INSERT INTO Appointments (DATE, TIME, PROBLEM, DOCTOR_ID, PATIENT_ID, STATUS) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            
            JSONObject obj = new JSONObject(info);

            pstmt.setString(1, obj.getString("date"));
            pstmt.setString(2, obj.getString("time"));
            pstmt.setString(3, obj.getString("problem"));
            pstmt.setInt(4, obj.getInt("docId"));
            pstmt.setInt(5, patId);
            pstmt.setString(6, "Booked");
            pstmt.executeUpdate();

            pstmt.close();

            SQL = "SELECT COUNT(ID) FROM Appointments";
            pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int appId = rs.getInt(1);

            return appId;

        } 
        catch (SQLException e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();
            return -1;
        }
    }

    public void updateAppointment(int appId, String info, int value)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            if(value == 1)
            {
                String SQL = "UPDATE Appointments SET STATUS = 'Cancelled' WHERE ID = ?;";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, appId);
                pstmt.executeUpdate();
            }

            else if(value == 2)
            {
                String SQL = "UPDATE Appointments SET DATE = ?, TIME = ?, UPDATE_REASON= ? WHERE ID = ?;";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                
                JSONObject obj = new JSONObject(info);

                pstmt.setString(1, obj.getString("date"));
                pstmt.setString(2, obj.getString("time"));
                pstmt.setString(3, obj.getString("reason"));
                pstmt.setInt(4, appId);
                pstmt.executeUpdate();
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();
        }
    }

    public String getReviewList(int docId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            System.out.println("SQL getReviewList");

            String SQL = "SELECT * FROM Reviews WHERE DOCTOR_ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray reviews = new JSONArray();

            while(rs.next())
            {
                JSONObject newObj = new JSONObject();
                newObj.put("comment", rs.getString("COMMENT"));
                newObj.put("experience", rs.getFloat("EXPERIENCE"));
                newObj.put("recommend", rs.getBoolean("RECOMMEND"));
                newObj.put("checkupRating", rs.getFloat("CHECKUPRATING"));
                newObj.put("environmentRating", rs.getFloat("ENVIRONMENTRATING"));
                newObj.put("staffRating", rs.getFloat("STAFFRATING"));

                reviews.put(newObj);
            }

            System.out.println(reviews.toString());

            return reviews.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    public void addPayment(String info, int appId) {
        try {
            JSONObject obj = new JSONObject(info);
            System.out.println(
                    obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("amount") + " " + appId);
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    public String getDoctorDetails(int docId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            String SQL = "SELECT NAME, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE, WORKING_HOURS, FEE, AVAILABILITY FROM Doctors WHERE ID = ?;";

            PreparedStatement pstmt = con.prepareStatement(SQL);

            pstmt.setInt(1, docId);

            ResultSet rs = pstmt.executeQuery();

            rs.next();

            JSONObject obj = new JSONObject();

            obj.put("name", rs.getString("NAME"));
            obj.put("specialization", rs.getString("SPECIALIZATION"));
            obj.put("description", rs.getString("DESCRIPTION"));
            obj.put("location", rs.getString("LOCATION"));
            obj.put("experience", rs.getInt("EXPERIENCE"));
            obj.put("workingHours", rs.getString("WORKING_HOURS"));
            obj.put("fee", rs.getFloat("FEE"));
            obj.put("availability", rs.getString("AVAILABILITY"));

            pstmt.close();

            SQL ="SELECT DESCRIPTION FROM SERVICES WHERE DOCTOR_ID = ?;";
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            rs = pstmt.executeQuery();
            
            String services = "";
            while(rs.next())
            {
                services += rs.getString("DESCRIPTION") + "\n";
            }
            obj.put("services", services);

            pstmt.close();

            SQL = "SELECT COUNT(ID) FROM Appointments WHERE DOCTOR_ID = ? AND STATUS = 'Completed' ;";
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            rs = pstmt.executeQuery();
            rs.next();
            obj.put("patients", rs.getInt(1));

            System.out.println(obj.toString());

            return obj.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }
    
// public String getDummyDoctor(String name){
//     ArrayList<Doctor> dummyDoctors = new ArrayList<Doctor>();
//     String[] specializations = {"Cardiologist", "Dermatologist", "Dentist", "Psychiatrist"};
//     String[] hospitals = {"Mayo Clinic", "Johns Hopkins Hospital", "Cleveland Clinic", "Massachusetts General Hospital", "UCSF Medical Center", "Brigham and Women's Hospital", "New York-Presbyterian Hospital", "Stanford Health Care-Stanford Hospital", "Hospitals of the University of Pennsylvania-Penn Presbyterian", "Cedars-Sinai Medical Center"};
//     List<DoctorTemp> doctorTemps = new ArrayList<DoctorTemp>();
//         char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        
        
//         //generate random doctorTemps
//         for(int i=0;i<10;i++){
//             StringBuilder sb = new StringBuilder();
//             sb.append("M ");
//             for (int j = 0; j < 10; j++) {
//                 sb.append(chars[(int) (Math.random() * chars.length)]);
//             }
//             con.close();
//             return doctors.toString();

//         } catch (SQLException | JSONException | IOException | ParseException e) {
//             // con.close();

//             e.printStackTrace();
//         }

//         return "[]";

//         //
//     }

     public String getDoctors(String name) {
        System.out.println("SQL getDoctors");
     

        String connectionUrl = "jdbc:sqlserver://BOREDAF\\SQLEXPRESS;" +
                "databaseName=SDA;" +
                "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
            String SQL = "SELECT id,name,location,specialization,experience,fee,rating FROM Doctors WHERE name LIKE '%"
                    + name + "%'";

            ResultSet rs = stmt.executeQuery(SQL);
            JSONArray doctors = new JSONArray();
            JSONParser parser = new JSONParser();
            while (rs.next()) {
                JSONObject obj = new JSONObject(
                        parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());

                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("name"));
                obj.put("specialization", rs.getString("specialization"));
                obj.put("experience", rs.getString("experience"));
                obj.put("rating", rs.getString("rating"));
                obj.put("location", rs.getString("location"));
                obj.put("price", rs.getString("fee"));
                doctors.put(obj);
            }
            con.close();
            return doctors.toString();

        } catch (SQLException | JSONException | IOException | ParseException e) {
            // con.close();

            e.printStackTrace();
        }

        return "[]";

        //
    }
    public String getTopDoctors() {
        try {
            System.out.println("SQL getTopDoctors");
            // query the database and return the top doctors

            JSONArray doctors = new JSONArray();
            JSONParser parser = new JSONParser();

            for (int i = 0; i < 4; i++) {
                JSONObject obj = new JSONObject(
                        parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());

                obj.put("id", i + 1);
                obj.put("name", "Musa" + (i + 1));
                obj.put("specialization", "Heart");
                obj.put("experience", i + 5 + " years");
                obj.put("rating", "5");
                obj.put("location", "Lahore");
                obj.put("price", "500");

                doctors.put(obj);
            }

            return doctors.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getPatient(String info) {

        try {
            System.out.println("SQL getPatient");
            // query the database and return the patient info if username and password
            // matches

            JSONParser parser = new JSONParser();
            JSONObject obj = new JSONObject(
                    parser.parse(new FileReader("src/main/resources/JSONPackage/Patient.json")).toString());
            Set<String> keyset = obj.keySet(); // gets all keys from .json

            // for(String key : keyset)
            // {
            // if(!get(key).equals("NULL"))
            // obj.put(key, get(key)); //put values in json object
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
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }

    }

    public String getAppointments(int patId) {
        try {

            System.out.println("SQL getAppointments");
            // query the database and return the appointments of the patient

            JSONArray appointments = new JSONArray();
            JSONParser parser = new JSONParser();

            for (int i = 0; i < 3; i++) {
                JSONObject obj = new JSONObject(
                        parser.parse(new FileReader("src/main/resources/JSONPackage/Appointment.json")).toString());

                obj.put("date", "1/1/2001");
                obj.put("time", "02:00");
                obj.put("problem", "Heart");
                obj.put("status", "Booked");
                obj.put("docId", i + 1);

                appointments.put(obj);
            }

            return appointments.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    // public static void main(String[] args) throws Exception {
    // String connectionUrl = "jdbc:sqlserver://BOREDAF\\SQLEXPRESS;" +
    // "databaseName=DB_Lab13;" +
    // "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
    // try (Connection con = DriverManager.getConnection(connectionUrl); Statement
    // stmt = con.createStatement()) {
    // String SQL = "SELECT * FROM tb1Employee";
    // ResultSet rs = stmt.executeQuery(SQL);
    // //print all columns and rows in resultset

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
 

    public void addComplaint(int patID, String details, int docID) {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {

            JSONObject obj = new JSONObject(details);
            String description = obj.getString("description");
            String reason = obj.getString("reason");

            String SQL = "INSERT INTO Complaints (patient_ID, description, reason, doctor_ID) VALUES (" + patID + ", '"
                    + description + "', '" + reason + "', " + docID + ")";
            stmt.executeUpdate(SQL);
           

            
            con.close();

        } catch (SQLException | JSONException e) {
            // con.close();

            e.printStackTrace();
        }
    }
}