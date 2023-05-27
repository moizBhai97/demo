package com.example.BackEnd;

public class Patient {
    
    private int patId;
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
}
