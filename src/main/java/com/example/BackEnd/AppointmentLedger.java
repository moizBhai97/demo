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

    public void bookAppointment(String info)
    {
        try
        {
            dbFactory.createHandler("SQL").bookAppointment(info);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(String info)
    {
        try
        {
            dbFactory.createHandler("SQL").cancelAppointment(info);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createAppointment(String info, int patId)
    {
        try
        {
            int appId = dbFactory.createHandler("SQL").saveAppointment(info, patId);

            Appointment appointment = new Appointment(info, patId, appId);

            appointments.add(appointment);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateAppointment(String info, int appId, int value)
    {
        try
        {
            if(value == 1)
            {
                JSONObject obj = new JSONObject(info);

                obj.put("docId", getAppointment(appId).get("docId"));
                obj.put("time", getAppointment(appId).get("time"));
                obj.put("date", getAppointment(appId).get("date"));

                dbFactory.createHandler("SQL").updateAppointment(appId, obj.toString(), value);
                
                getAppointment(appId).setStatus("Cancelled");
            }

            if(value == 2)
            {
                JSONObject obj = new JSONObject();

                obj.put("docId", getAppointment(appId).get("docId"));
                obj.put("time", getAppointment(appId).get("time"));
                obj.put("date", getAppointment(appId).get("date"));

                dbFactory.createHandler("SQL").cancelAppointment(obj.toString());

                dbFactory.createHandler("SQL").updateAppointment(appId, info, value);

                getAppointment(appId).update(info);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public Appointment getAppointment(int appId)
    {
        try
        {
            for(int i = 0; i < appointments.size(); i++)
            {
                if(appointments.get(i).getAppId() == appId)
                {
                    return appointments.get(i);
                }
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getAppointList(int value)
    {
        try
        {
            JSONArray objs = new JSONArray();
            
            for(int i = 0; i < appointments.size(); i++ )
            {
                if(appointments.get(i).getStatus().equals("Booked") && value == 1)
                {
                    JSONObject obj = new JSONObject(appointments.get(i).toString());
                    
                    objs.put(obj);
                }
                else if(value == 2 && (appointments.get(i).getStatus().equals("Completed") || appointments.get(i).getStatus().equals("Cancelled")))
                {
                    JSONObject obj = new JSONObject(appointments.get(i).toString());
                    
                    objs.put(obj);
                }
                else if(value == 3 && appointments.get(i).getStatus().equals("Completed"))
                {
                    JSONObject obj = new JSONObject(appointments.get(i).toString());
                    
                    objs.put(obj);
                } 
            }

            return objs.toString();
        }
        catch(Exception e)
        {

            e.printStackTrace();

            return "NULL";
        }
    }

    public void setPatientAppointments(int patId)
    {
        try
        {
            String appointmentsInfo = dbFactory.createHandler("SQL").getPatientAppointments(patId);

            JSONArray objs = new JSONArray(appointmentsInfo);

            for(int i = 0; i < objs.length(); i++)
            {
                JSONObject obj = objs.getJSONObject(i);

                Appointment appointment = new Appointment(obj.toString());

                appointments.add(appointment);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setDoctorAppointments(int docId)
    {
        try
        {
            String appointmentsInfo = dbFactory.createHandler("SQL").getDoctorAppointments(docId);

            JSONArray objs = new JSONArray(appointmentsInfo);

            for(int i = 0; i < objs.length(); i++)
            {
                JSONObject obj = objs.getJSONObject(i);

                Appointment appointment = new Appointment(obj.toString());

                appointments.add(appointment);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
