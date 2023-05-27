package com.example.BackEnd;

import org.json.JSONObject;

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

    public void bookAppointment(String info)
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

    public String getAppointList()
    {
        return appointmentLedger.getAppointList(this.patId);
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
}
