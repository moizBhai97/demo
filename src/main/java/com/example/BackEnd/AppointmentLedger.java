package com.example.BackEnd;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

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

    public String getAppointList(int patId)
    {
        try
        {
            JSONParser parser = new JSONParser();

            JSONObject obj = new JSONObject(parser.parse(new FileReader("Appointment.json")).toString());

            Set<String> keyset = obj.keySet();

            JSONArray objs = new JSONArray();
            
            for(int i = 0; i < appointments.size(); i++ )
            {
                JSONObject newObj = new JSONObject();

                for(String key : keyset)
                {
                    if(!appointments.get(i).get(key).equals("NULL"))
                        newObj.put(key, appointments.get(i).get(key));            //put values in json object
                }

                objs.put(newObj);
            }

            return objs.toString();

        }
        catch(Exception e)
        {
            System.out.println(e);

            return null;
        }
    }
}
