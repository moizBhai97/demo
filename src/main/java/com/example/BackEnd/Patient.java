package com.example.BackEnd;

public class Patient {
    
    private int patId;
    private AppointmentLedger appointmentLedger;

    Patient()
    {
        appointmentLedger = new AppointmentLedger();
    }

    Patient(int patId)
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
            appointmentLedger.createAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e);
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
            System.out.println(e);  
        }
    }
}
