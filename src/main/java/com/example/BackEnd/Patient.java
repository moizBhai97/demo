package com.example.BackEnd;

public class Patient {
    
    private int patId;
    private AppointmentLedger appointmentLedger;

    Patient()
    {
        patId = 0;
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

    public Appointment bookAppointment(int docId, String date, String time, String problem)
    {
        return appointmentLedger.createAppointment(docId, date, time, problem, docId);
    }

    public String getAppointList()
    {
        return appointmentLedger.getAppointList(this.patId);
    }
}
