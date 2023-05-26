package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public Boolean updateAppointment(String Reason, int appId, Boolean flag)
    {
        if(flag)
        {
            getAppointment(appId);
            return true;
        }

        else return true;
    }

    public Appointment getAppointment(int appId)
    {
        return appointments.get(appId);
    }

    public String getAppointList(int patId)
    {
        JSONArray objs = new JSONArray();
            
            for(int i = 0; i < appointments.size(); i++ )
            {
                 
                JSONObject obj = new JSONObject(appointments.get(i).getDetails());
                
                objs.put(obj);
            }

            return objs.toString();
    }
}
