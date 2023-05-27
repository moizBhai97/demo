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

    public void createAppointment(String info)
    {
        try
        {
            Appointment appointment = new Appointment(info);

            appointments.add(appointment);

            dbFactory.createHandler("SQL").saveAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void updateAppointment(String Reason, int appId, int value)
    {
        try
        {
            if(value == 1)
            {
                dbFactory.createHandler("SQL").updateAppointment(appId, Reason, value);
                
                getAppointment(appId).setStatus("Cancel");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

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
            JSONObject obj = new JSONObject(getAppointment(i).toString());
            
            objs.put(obj);
            
        }
        String last= objs.get(2).toString();
        //put it back into the json array
        objs.put(last);

        return objs.toString();
    }
}
