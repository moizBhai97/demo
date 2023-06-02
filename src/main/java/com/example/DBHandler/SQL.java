package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class SQL extends DBHandler {
    String connectionUrl;
    String moiz;
    String musa;
    String abdullah;

    public SQL() {
        moiz = "jdbc:sqlserver://MOIZ-KHAN;";
        musa = "jdbc:sqlserver://DESKTOP-NO4AAI8\\SQLEXPRESS;";
        abdullah = "";

        connectionUrl = moiz + 
                        "databaseName=SDA;" + 
                        "IntegratedSecurity=true;" + 
                        "encrypt=true;trustServerCertificate=true";
    }

  
    public void bookAppointment(String info)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            JSONObject obj = new JSONObject(info);

            String SQL = "UPDATE APPOINTMENT_SLOTS SET AVAILABLE = 0 WHERE DOCTOR_ID = ? AND DATE = ? AND TIME = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, obj.getInt("docId"));
            pstmt.setString(2, obj.getString("date"));
            pstmt.setString(3, obj.getString("time"));
            pstmt.executeUpdate();

        } 
        catch (SQLException e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();
        }
    }

    public void cancelAppointment(String info)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            JSONObject obj = new JSONObject(info);

            String SQL = "UPDATE APPOINTMENT_SLOTS SET AVAILABLE = 1 WHERE DOCTOR_ID = ? AND DATE = ? AND TIME = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, obj.getInt("docId"));
            pstmt.setString(2, obj.getString("date"));
            pstmt.setString(3, obj.getString("time"));
            pstmt.executeUpdate();

        } 
        catch (SQLException e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();
        }
    }

    public void updateAppointment(int appId, String info, int value)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            if(value == 1)
            {
                JSONObject obj = new JSONObject(info);

                String SQL = "UPDATE Appointments SET STATUS = 'Cancelled', UPDATE_REASON = ? WHERE ID = ?;";
                PreparedStatement pstmt = con.prepareStatement(SQL);

                pstmt.setString(1, obj.getString("reason"));
                pstmt.setInt(2, appId);
                pstmt.executeUpdate();

                pstmt.close();

                SQL = "UPDATE APPOINTMENT_SLOTS SET AVAILABLE = 1 WHERE DOCTOR_ID = ? AND DATE = ? AND TIME = ?;";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, obj.getInt("docId"));
                pstmt.setString(2, obj.getString("date"));
                pstmt.setString(3, obj.getString("time"));
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

                pstmt.close();

                SQL = "UPDATE APPOINTMENT_SLOTS SET AVAILABLE = 0 WHERE DOCTOR_ID = ? AND DATE = ? AND TIME = ?;";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, obj.getInt("docId"));
                pstmt.setString(2, obj.getString("date"));
                pstmt.setString(3, obj.getString("time"));
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
                newObj.put("patId", rs.getInt("PATIENT_ID"));

                reviews.put(newObj);
            }

            return reviews.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    public int saveAppointment(String info, int patId) 
    {
        try(Connection con = DriverManager.getConnection(connectionUrl))
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

            pstmt.close();

            SQL = "INSERT INTO Payments (DATE, TIME, AMOUNT, STATUS, APPOINTMENT_ID) VALUES (?, ?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, obj.getJSONObject("payment").getString("date"));
            pstmt.setString(2, obj.getJSONObject("payment").getString("time"));
            pstmt.setFloat(3, obj.getJSONObject("payment").getFloat("amount"));
            pstmt.setBoolean(4, false);
            pstmt.setInt(5, appId);
            pstmt.executeUpdate();

            return appId;
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());

            return -1;
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

            return obj.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    public String getSchedule(int docId, String date)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {

            String SQL = "SELECT * FROM APPOINTMENT_SLOTS WHERE DOCTOR_ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray schedule = new JSONArray();

            while(rs.next())
            {
                JSONObject newObj = new JSONObject();
                newObj.put("date", rs.getString("DATE"));
                newObj.put("time", rs.getString("TIME"));
                newObj.put("available", rs.getBoolean("AVAILABLE"));

                schedule.put(newObj);
            }

            return schedule.toString();
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

     public String getDoctors(String name) 
     {
        System.out.println("SQL getDoctors");

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {

            String SQL = "SELECT (SELECT COUNT(ID) FROM Appointments WHERE DOCTOR_ID = d.id AND STATUS = 'Completed') as Patients, id, NAME, EMAIL, DOB, COUNTRY, PHONE_NUMBER, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, STATS, PATIENTS_TREATED, EXPERIENCE, RATING, WORKING_HOURS, FEE, AVAILABILITY FROM DOCTORS d where d.name LIKE '%" + name + "%';";

            ResultSet rs = stmt.executeQuery(SQL);

            JSONArray doctors = new JSONArray();

            while (rs.next()) 
            {
                JSONObject obj = new JSONObject();

                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("NAME"));
                obj.put("email", rs.getString("EMAIL"));
                obj.put("DOB", rs.getString("DOB"));
                obj.put("country", rs.getString("COUNTRY"));
                obj.put("phoneNumber", rs.getString("PHONE_NUMBER"));
                obj.put("gender", rs.getString("GENDER"));

                JSONObject innerObj = new JSONObject();
                innerObj.put("specialization", rs.getString("specialization"));
                innerObj.put("description", rs.getString("description"));
                innerObj.put("location", rs.getString("location"));
                innerObj.put("stats", rs.getFloat("stats"));
                innerObj.put("patients", rs.getInt("patients_treated"));
                innerObj.put("experience", rs.getInt("experience"));
                innerObj.put("rating", rs.getFloat("rating"));
                innerObj.put("workingHours", rs.getString("working_hours"));
                innerObj.put("fee", rs.getInt("fee"));
                innerObj.put("availability", rs.getString("availability"));
                
                SQL ="SELECT DESCRIPTION FROM SERVICES WHERE DOCTOR_ID = ?;";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, obj.getInt("id"));
                ResultSet rs2 = pstmt.executeQuery();

                String services = "";
                while(rs2.next())
                {
                    services += rs2.getString("DESCRIPTION") + "\n";
                }
                innerObj.put("services", services);

                pstmt.close();

                obj.put("details", innerObj);
                doctors.put(obj);
            }

            stmt.close();

            con.close();
            return doctors.toString();

        } 
        catch (SQLException | JSONException e) 
        {
            e.printStackTrace();
            return "[]";
        }
    }

    public String getTopDoctors() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()){
            System.out.println("SQL getTopDoctors");

            String SQL = "SELECT TOP 4 * FROM DOCTORS ORDER BY ((RATING/5)*60 + STATS*.4) DESC";

            ResultSet rs = stmt.executeQuery(SQL);
            System.out.println(rs.toString());

            JSONArray doctors = new JSONArray();

            while(rs.next()) {
                JSONObject doctorObj = new JSONObject();
               
                doctorObj.put("id", rs.getInt("id"));
                doctorObj.put("name", rs.getString("name"));
                doctorObj.put("email", rs.getString("EMAIL"));
                doctorObj.put("DOB", rs.getString("DOB"));
                doctorObj.put("country", rs.getString("COUNTRY"));
                doctorObj.put("phoneNumber", rs.getString("PHONE_NUMBER"));
                doctorObj.put("gender", rs.getString("GENDER"));

                JSONObject detailsObj = new JSONObject();
                detailsObj.put("specialization", rs.getString("specialization"));
                detailsObj.put("description", rs.getString("description"));
                detailsObj.put("location", rs.getString("location"));
                detailsObj.put("stats", rs.getFloat("stats"));
                detailsObj.put("patients", rs.getInt("patients_treated"));
                detailsObj.put("experience", rs.getInt("experience"));
                detailsObj.put("rating", rs.getFloat("rating"));
                detailsObj.put("workingHours", rs.getString("working_hours"));
                detailsObj.put("fee", rs.getInt("fee"));
                detailsObj.put("availability", rs.getString("availability"));

                SQL ="SELECT DESCRIPTION FROM SERVICES WHERE DOCTOR_ID = ?;";
                PreparedStatement pstmt2 = con.prepareStatement(SQL);
                pstmt2.setInt(1, doctorObj.getInt("id"));
                ResultSet rs2 = pstmt2.executeQuery();

                String services = "";
                while(rs2.next())
                {
                    services += rs2.getString("DESCRIPTION") + "\n";
                }
                detailsObj.put("services", services);
                doctorObj.put("details", detailsObj);

                doctors.put(doctorObj);
            }

            //System.out.println(doctors.toString());
            return doctors.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public void updatePatientProfile(int patId, String info)
    {
        System.out.println("SQL updatePatientProfile");

        JSONObject obj = new JSONObject(info);

        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement())
        {

            String SQL = "UPDATE PATIENTS SET NAME = ?, EMAIL = ?, DOB = ?, COUNTRY = ?, PHONE_NUMBER = ?, GENDER = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, obj.getString("name"));
            pstmt.setString(2, obj.getString("email"));
            pstmt.setString(3, obj.getString("DOB"));
            pstmt.setString(4, obj.getString("country"));
            pstmt.setString(5, obj.getString("phoneNumber"));
            pstmt.setString(6, obj.getString("gender"));
            pstmt.setInt(7, patId);
            
            pstmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
        }
    }

    public String getPatient(String info) {

        
        System.out.println("SQL getPatient");

        JSONObject information = new JSONObject(info);
        
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
            String SQL = "SELECT NAME, ID, EMAIL, DOB, COUNTRY, PHONE_NUMBER, GENDER FROM Patients WHERE email = ?  AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, information.getString("email"));
            pstmt.setString(2, information.getString("password"));

            ResultSet rs = pstmt.executeQuery();

            JSONParser parser = new JSONParser();
            JSONObject patient = new JSONObject(
                parser.parse(new FileReader("src/main/resources/JSONPackage/Patient.json")).toString());                
                rs.next();
            patient.put("patId", rs.getInt("id"));
            patient.put("name", rs.getString("name"));
            patient.put("email", rs.getString("email"));
            patient.put("DOB", rs.getString("dob"));
            patient.put("country", rs.getString("country"));
            patient.put("phoneNumber", rs.getString("phone_number"));
            patient.put("gender", rs.getString("gender"));

                
            con.close();
            System.out.println(patient.toString());
            
            return patient.toString();

        } catch ( Exception e) {
            // con.close();

            e.printStackTrace();
            return "[]";
        }


    }

    public String getDoctor(String info){

        try
        {
            System.out.println("SQL getDoctor");
            System.out.println(info);
            
            JSONObject information = new JSONObject(info); 
            
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
                String SQL = "SELECT NAME, ID, EMAIL, DOB, COUNTRY, PHONE_NUMBER, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, STATS, PATIENTS_TREATED, EXPERIENCE, RATING, WORKING_HOURS, FEE, AVAILABILITY FROM Doctors WHERE email = ? AND password = ?";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, information.getString("email"));
                pstmt.setString(2, information.getString("password"));

                ResultSet rs = pstmt.executeQuery();

                rs.next();
                JSONObject doctorObj = new JSONObject();
               
                doctorObj.put("id", rs.getInt("id"));
                doctorObj.put("name", rs.getString("name"));
                doctorObj.put("email", rs.getString("EMAIL"));
                doctorObj.put("DOB", rs.getString("DOB"));
                doctorObj.put("country", rs.getString("COUNTRY"));
                doctorObj.put("phoneNumber", rs.getString("PHONE_NUMBER"));
                doctorObj.put("gender", rs.getString("GENDER"));

                JSONObject detailsObj = new JSONObject();
                detailsObj.put("specialization", rs.getString("specialization"));
                detailsObj.put("description", rs.getString("description"));
                detailsObj.put("location", rs.getString("location"));
                detailsObj.put("stats", rs.getFloat("stats"));
                detailsObj.put("patients", rs.getInt("patients_treated"));
                detailsObj.put("experience", rs.getInt("experience"));
                detailsObj.put("rating", rs.getFloat("rating"));
                detailsObj.put("workingHours", rs.getString("working_hours"));
                detailsObj.put("fee", rs.getInt("fee"));
                detailsObj.put("availability", rs.getString("availability"));

                SQL ="SELECT DESCRIPTION FROM SERVICES WHERE DOCTOR_ID = ?;";
                PreparedStatement pstmt2 = con.prepareStatement(SQL);
                pstmt2.setInt(1, doctorObj.getInt("id"));
                ResultSet rs2 = pstmt2.executeQuery();

                String services = "";
                while(rs2.next())
                {
                    services += rs2.getString("DESCRIPTION") + "\n";
                }
                detailsObj.put("services", services);
                
                doctorObj.put("details", detailsObj);
                System.out.println(doctorObj.toString());

                con.close();
                return doctorObj.toString();
    
            } catch (SQLException | JSONException e) {
                // con.close();
    
                e.printStackTrace();
                return "[]";
            }
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }

    }

    public String getPatientHistory(int patId)
    {

        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()){

            //System.out.println("SQL getPatientHistory");
            
            String SQL = "SELECT * FROM PATIENT_HISTORY WHERE ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, patId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray history = new JSONArray();

            while(rs.next())
            {
                JSONObject newObj = new JSONObject();
                newObj.put("type", rs.getString("TYPE"));
                newObj.put("description", rs.getString("DESCRIPTION"));


                history.put(newObj);
            }

            //System.out.println(history.toString());
            return history.toString();

        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getPatientAppointments(int patId) {
        
        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {

            System.out.println("SQL getPatientAppointments");

            String SQL = "SELECT * FROM Appointments WHERE PATIENT_ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, patId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray appointments = new JSONArray();

            /*
            "appId": "{{appId}}",
            "date": "{{date}}",
            "time": "{{time}}",
            "problem": "{{problem}}",
            "status": "{{status}}",
            "docId": "{{docId}}",
            "payment": {
                            "amount": "{{amount}}",
                            "status": "{{status}}",
                            "date": "{{date}}",
                            "time": "{{time}}"
                        }
             */

            while(rs.next())
            {
                JSONObject obj = new JSONObject();

                obj.put("appId", rs.getInt("ID"));
                obj.put("date", rs.getString("DATE"));
                obj.put("time", rs.getString("TIME"));
                obj.put("problem", rs.getString("PROBLEM"));
                obj.put("status", rs.getString("STATUS"));
                obj.put("docId", rs.getInt("DOCTOR_ID"));
                obj.put("patId", rs.getInt("PATIENT_ID"));

                SQL = "SELECT * FROM Payments WHERE APPOINTMENT_ID = ?;";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, obj.getInt("appId"));

                ResultSet rs2 = pstmt.executeQuery();
                while(rs2.next())
                {
                    JSONObject paymentObj = new JSONObject();
                    paymentObj.put("amount", rs2.getFloat("AMOUNT"));
                    paymentObj.put("status", rs2.getBoolean("STATUS"));
                    paymentObj.put("date", rs2.getString("DATE"));
                    paymentObj.put("time", rs2.getString("TIME"));

                    obj.put("payment", paymentObj);
                }

                appointments.put(obj);
            }

            System.out.println(appointments.toString());

            return appointments.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getDoctorAppointments(int docId) {
        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {

            System.out.println("SQL getDoctorAppointments");

            String SQL = "SELECT * FROM Appointments WHERE DOCTOR_ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray appointments = new JSONArray();

            /*
            "appId": "{{appId}}",
            "date": "{{date}}",
            "time": "{{time}}",
            "problem": "{{problem}}",
            "status": "{{status}}",
            "docId": "{{docId}}",
            "payment": {
                            "amount": "{{amount}}",
                            "status": "{{status}}",
                            "date": "{{date}}",
                            "time": "{{time}}"
                        }
             */

            while(rs.next())
            {
                JSONObject obj = new JSONObject();

                obj.put("appId", rs.getInt("ID"));
                obj.put("date", rs.getString("DATE"));
                obj.put("time", rs.getString("TIME"));
                obj.put("problem", rs.getString("PROBLEM"));
                obj.put("status", rs.getString("STATUS"));
                obj.put("docId", rs.getInt("DOCTOR_ID"));
                obj.put("patId", rs.getInt("PATIENT_ID"));

                SQL = "SELECT * FROM Payments WHERE APPOINTMENT_ID = ?;";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, obj.getInt("appId"));

                ResultSet rs2 = pstmt.executeQuery();
                while(rs2.next())
                {
                    JSONObject paymentObj = new JSONObject();
                    paymentObj.put("amount", rs2.getFloat("AMOUNT"));
                    paymentObj.put("status", rs2.getBoolean("STATUS"));
                    paymentObj.put("date", rs2.getString("DATE"));
                    paymentObj.put("time", rs2.getString("TIME"));

                    obj.put("payment", paymentObj);
                }

                appointments.put(obj);
            }

            System.out.println(appointments.toString());

            return appointments.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getAppointmentDoctors(int patId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            System.out.println("SQL getAppointmentDoctors");

            String SQL = "SELECT * FROM Doctors WHERE ID IN (SELECT DOCTOR_ID FROM Appointments WHERE PATIENT_ID = ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, patId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray doctors = new JSONArray();

            while(rs.next())
            {
                JSONObject doctorObj = new JSONObject();
               
                doctorObj.put("id", rs.getInt("id"));
                doctorObj.put("name", rs.getString("name"));
                doctorObj.put("email", rs.getString("EMAIL"));
                doctorObj.put("DOB", rs.getString("DOB"));
                doctorObj.put("country", rs.getString("COUNTRY"));
                doctorObj.put("phoneNumber", rs.getString("PHONE_NUMBER"));
                doctorObj.put("gender", rs.getString("GENDER"));

                JSONObject detailsObj = new JSONObject();
                detailsObj.put("specialization", rs.getString("specialization"));
                detailsObj.put("description", rs.getString("description"));
                detailsObj.put("location", rs.getString("location"));
                detailsObj.put("stats", rs.getFloat("stats"));
                detailsObj.put("patients", rs.getInt("patients_treated"));
                detailsObj.put("experience", rs.getInt("experience"));
                detailsObj.put("rating", rs.getFloat("rating"));
                detailsObj.put("workingHours", rs.getString("working_hours"));
                detailsObj.put("fee", rs.getInt("fee"));
                detailsObj.put("availability", rs.getString("availability"));

                SQL ="SELECT DESCRIPTION FROM SERVICES WHERE DOCTOR_ID = ?;";
                PreparedStatement pstmt2 = con.prepareStatement(SQL);
                pstmt2.setInt(1, doctorObj.getInt("id"));
                ResultSet rs2 = pstmt2.executeQuery();

                String services = "";
                while(rs2.next())
                {
                    services += rs2.getString("DESCRIPTION") + "\n";
                }
                detailsObj.put("services", services);
                
                doctorObj.put("details", detailsObj);

                doctors.put(doctorObj);
            }

            return doctors.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    public String getAppointmentPatients(int docId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl)) 
        {
            System.out.println("SQL getAppointmentPatients");

            String SQL = "SELECT * FROM Patients WHERE ID IN (SELECT PATIENT_ID FROM Appointments WHERE DOCTOR_ID = ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray patients = new JSONArray();

            while(rs.next())
            {
                JSONParser parser = new JSONParser();
                JSONObject patientObj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Patient.json")).toString());                
                patientObj.put("patId", rs.getInt("id"));
                patientObj.put("name", rs.getString("name"));
                patientObj.put("email", rs.getString("email"));
                patientObj.put("DOB", rs.getString("dob"));
                patientObj.put("country", rs.getString("country"));
                patientObj.put("phoneNumber", rs.getString("phone_number"));
                patientObj.put("gender", rs.getString("gender"));

                patients.put(patientObj);
            }

            System.out.println(patients.toString());
            return patients.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    // public static void main(String[] args) throws Exception {
    // String connectionUrl = "jdbc:sqlserver://DESKTOP-NO4AAI8\\SQLEXPRESS;" +
    // "databaseName=SDA;" +
    // "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
    // try (Connection con = DriverManager.getConnection(connectionUrl); Statement
    // stmt = con.createStatement()) {
    // String SQL = "SELECT * FROM Patients";
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

    public void addCertification(String info, int docId)
    {
        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()){
            JSONObject obj = new JSONObject(info);
            String SQL = "INSERT INTO CERTIFICATIONS (DOCTOR_ID, NAME, APPROVED_STATUS, ISSUE_DATE, EXPIRY_DATE) VALUES (?, ?, ?, ?, ?);";
            
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            pstmt.setString(2, obj.getString("name"));
            pstmt.setString(3, obj.getString("approvedStatus"));
            pstmt.setString(4, obj.getString("issueDate"));
            pstmt.setString(5, obj.getString("expiryDate"));

            pstmt.executeUpdate();
        }
        catch (SQLException | JSONException e) {
            // con.close();

            e.printStackTrace();
        }
    }

    public String getCertificates(int docId)
    {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement() ) 
        {
            System.out.println("SQL getCertificates");

            String SQL = "SELECT * FROM CERTIFICATIONS WHERE DOCTOR_ID = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, docId);
            ResultSet rs = pstmt.executeQuery();

            JSONArray certificates = new JSONArray();

            while(rs.next())
            {
                JSONObject newObj = new JSONObject();
                newObj.put("name", rs.getString("NAME"));
                newObj.put("approvedStatus", rs.getString("APPROVED_STATUS"));
                newObj.put("issueDate", rs.getString("ISSUE_DATE"));
                newObj.put("expiryDate", rs.getString("EXPIRY_DATE"));
             
                certificates.put(newObj);
            }

            return certificates.toString();
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            e.printStackTrace();            
            return null;
        } 
    }

    public void addReview(String info, int patId, int docId)
    {
        try(Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement() )
        {
            JSONObject obj = new JSONObject(info);

            String SQL = "INSERT INTO Reviews (PATIENT_ID, DOCTOR_ID, COMMENT, EXPERIENCE, RECOMMEND, CHECKUPRATING, ENVIRONMENTRATING, STAFFRATING) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, patId);
            pstmt.setInt(2, docId);
            pstmt.setString(3, obj.getString("comment"));
            pstmt.setFloat(4, obj.getFloat("experience"));
            if(obj.getString("recommend").equals("Yes"))
            {
                pstmt.setInt(5, 1);
            }
            else
            {
                pstmt.setInt(5, 0);
            }
            pstmt.setFloat(6, obj.getFloat("checkupRating"));
            pstmt.setFloat(7, obj.getFloat("environmentRating"));
            pstmt.setFloat(8, obj.getFloat("staffRating"));
            pstmt.executeUpdate();

            pstmt.close();         

        }
        catch (SQLException | JSONException e) {
            // con.close();

            e.printStackTrace();
        }
    }
}