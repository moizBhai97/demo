package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppointmentLedger {

    private List<Appointment> appointments;
    private DBFactory dbFactory;

    public AppointmentLedger()
    {
        dbFactory = DBFactory.getInstance();

        appointments = new ArrayList<Appointment>();
    }

    public void createAppointment(String info, int patId)
    {
        try
        {
            Appointment appointment = new Appointment(info);

            appointments.add(appointment);

            dbFactory.createHandler("SQL").saveAppointment(info, patId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void updateAppointment(String info, int appId, int value)
    {
        try
        {
            if(value == 1)
            {
                dbFactory.createHandler("SQL").updateAppointment(appId, info, value);
                
                getAppointment(appId).setStatus("Cancel");
            }

            if(value == 2)
            {
                dbFactory.createHandler("SQL").updateAppointment(appId, info, value);

                getAppointment(appId).update(info);
            }
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
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

        return objs.toString();
    }

<<<<<<< HEAD
    public void addPayment(String info, int appId)
    {
        try
        {
            dbFactory.createHandler("SQL").addPayment(info, appId);

            getAppointment(appId).addPayment(info);
=======
    public void setAppointments(int patId)
    {
        try
        {
            String appointmentsInfo = dbFactory.createHandler("SQL").getAppointments(patId);

            JSONArray objs = new JSONArray(appointmentsInfo);

            for(int i = 0; i < objs.length(); i++)
            {
                JSONObject obj = objs.getJSONObject(i);

                Appointment appointment = new Appointment(obj.toString());

                appointments.add(appointment);
            }
>>>>>>> 83d1be6f875ba7d84706f2fe449c752ccc7b70f5
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }
<<<<<<< HEAD
=======

>>>>>>> 83d1be6f875ba7d84706f2fe449c752ccc7b70f5
}
