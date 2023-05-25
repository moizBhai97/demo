package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

public class AppointmentLedger {

    private List<Appointment> appointments;
    private DBFactory dbFactory;

    AppointmentLedger()
    {
        dbFactory = DBFactory.getInstance();

        appointments = new ArrayList<Appointment>();
    }

    public Appointment createAppointment(int docId, String date, String time, String problem, int patId)
    {
        Appointment appointment = new Appointment(date, time, problem, patId);

        appointments.add(appointment);

        dbFactory.createHandler("SQL").saveAppointment(date, time, problem, patId);

        return appointment;
    }

}
