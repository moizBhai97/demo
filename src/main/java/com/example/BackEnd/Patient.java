package com.example.BackEnd;

import java.io.FileReader;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Patient {
    
    private int patId;
    private String name; 
    private String email; 
    private String DOB; 
    private String phoneNumber; 
    private String gender; 
    private AppointmentLedger appointmentLedger;

    public Patient()
    {
        appointmentLedger = new AppointmentLedger();
    }

    public Patient(int patId)
    {
        this.patId = patId;
        appointmentLedger = new AppointmentLedger();
    }

    public Patient(String info)
    {
        JSONObject obj = new JSONObject(info);

        this.patId = obj.getInt("patId");
        this.name = obj.getString("name");
        this.email = obj.getString("email");
        this.DOB = obj.getString("DOB");
        this.phoneNumber = obj.getString("phoneNumber");
        this.gender = obj.getString("gender");
    }

    public int getpatId()
    {
        return patId;
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
            
        appointmentLedger.setAppointments(this.patId);
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
