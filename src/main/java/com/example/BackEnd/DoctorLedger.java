package com.example.BackEnd;

import java.util.ArrayList;


public class DoctorLedger {

    private ArrayList<Doctor> doctorList;

    public DoctorLedger()
    {
        doctorList = new ArrayList<Doctor>();
    }

    public void addDoctor(Doctor doctor)
    {
        doctorList.add(doctor);
    }

    public Doctor getDoctor(int docId)
    {
        for(int i = 0; i < doctorList.size(); i++)
        {
            if(doctorList.get(i).getId() == docId)
            {
                return doctorList.get(i);
            }
        }

        return null;
    }

    public String getDoctor(String name)
    {
        // DBHandler db = new DBHandler();
        String result = DBFactory.getInstance().createHandler("SQL").getDoctors(name);
       // ArrayList<Doctor> result = db.getDoctor(name);
        // for(Doctor doctor : doctorList)
        // {
        //     if(doctor.getName().equals(name))
        //     {
        //         result += doctor.getDetails();
        //     }
        // }
        //ArrayList<Doctor> resultDoctors =getDummyDoctor(name);
            

        
        return result;
    }
    
}