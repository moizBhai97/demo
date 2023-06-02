package com.example.BackEnd;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Patient {
    
    private int patId;
    private String name; 
    private String email; 
    private String DOB; 
    private String country; 
    private String phoneNumber; 
    private String gender;
    
    private AppointmentLedger appointmentLedger;
    private List<PatientHistory> patientHistoryList;

    public Patient()
    {
        appointmentLedger = new AppointmentLedger();
    }

    public Patient(int patId)
    {
        this.patId = patId;
        appointmentLedger = new AppointmentLedger();
    }

    public String getName()
    {
        System.out.println("Patient name: " + name);
        return name;
    }

    public Patient(String info)
    {
        JSONObject obj = new JSONObject(info);
        //System.out.println(obj.getString("patId"));

        this.patId = obj.getInt("patId");
        this.name = obj.getString("name");
        this.email = obj.getString("email");
        this.DOB = obj.getString("DOB");
        this.DOB = obj.getString("country");
        this.phoneNumber = obj.getString("phoneNumber");
        this.gender = obj.getString("gender");

        appointmentLedger = new AppointmentLedger();
    }

    public int getpatId()
    {
        return patId;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dOB) {
        DOB = dOB;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void updateProfile(String info)
    {
        JSONObject obj = new JSONObject(info);
        
        this.patId = obj.getInt("patId");
        this.name = obj.getString("name");
        this.email = obj.getString("email");
        this.DOB = obj.getString("DOB");
        this.phoneNumber = obj.getString("phoneNumber");
        this.gender = obj.getString("gender");
    }

    public void setHistory()
    {
        try{
            if(patientHistoryList == null)
            {
                JSONArray jsonArray = new JSONArray(DBFactory.getInstance().createHandler("SQL").getPatientHistory(patId));
                
                patientHistoryList = new ArrayList<>();

                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    PatientHistory history = new PatientHistory(obj.toString());

                    patientHistoryList.add(history);
                }
            }            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getHistory()
    {
        try{

            setHistory();
            
            JSONArray jsonArray = new JSONArray();
            
            for(int i = 0; i < patientHistoryList.size(); i++)
            {
                JSONObject obj = new JSONObject(patientHistoryList.get(i).toString());
                
                jsonArray.put(obj);
            }
            
            return jsonArray.toString();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return "[]";
        }
    }

    public void bookSlot(String info)
    {
        try
        {
            appointmentLedger.bookAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void cancelSlot(String info)
    {
        try
        {
            appointmentLedger.cancelAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void saveAppointment(String info)
    {
        try
        {
            appointmentLedger.createAppointment(info, patId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getAppointList(int value)
    {
        return appointmentLedger.getAppointList(value);
    }

    public void setAppointments()
    {
        if(appointmentLedger == null)
            appointmentLedger = new AppointmentLedger();
            
        appointmentLedger.setPatientAppointments(this.patId);
    }

    public void cancelAppointment(String Reason, int appId)
    {
        try
        {
            appointmentLedger.updateAppointment(Reason, appId, 1);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void reschAppointment(String info, int appId)
    {
        try
        {
            appointmentLedger.updateAppointment(info, appId, 2);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getAppointment(int appId)
    {
        return appointmentLedger.getAppointment(appId).toString();
    }

    public String getDetails()
    {
        JSONObject obj = new JSONObject();

        obj.put("name", name);
        obj.put("email", email);
        obj.put("DOB", DOB);
        obj.put("country", country);
        obj.put("phoneNumber", phoneNumber);
        obj.put("gender", gender);

        return obj.toString();
    }

    public String get(String value)
    {
        if(value.equals("patId"))
            return patId + "";

        else if(value.equals("name"))
            return name;

        else if(value.equals("email"))
            return email;
        
        else if(value.equals("DOB"))
            return DOB;

        else if(value.equals("phoneNumber"))
            return phoneNumber;
        
        else if(value.equals("gender"))
            return gender;

        return "NULL";
    }

    @Override
    public String toString()
    {
        try
        {
            JSONParser parser = new JSONParser(); 

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Patient.json")).toString());

            Set<String> keyset = obj.keySet();
            
            for(String key : keyset)
            {
                if(!get(key).equals("NULL"))
                    obj.put(key, get(key));
            }

            return obj.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    } 
}
