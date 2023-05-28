package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

public class PatientLedger {

    DBFactory dbFactory;
    private List<Patient> patients;

    public PatientLedger()
    {
        dbFactory = DBFactory.getInstance();
        patients = new ArrayList<Patient>();
        patients.add(new Patient(1)); // for testing
    }

    

    public Patient getPatient(int patId)
    {
        for(int i = 0; i < patients.size(); i++)
        {
            if(patients.get(i).getpatId() == patId)
            {
                return patients.get(i);
            }
        }

        return null;
    }    

    public Patient getPatient(String info)
    {
        try{

            String patientInfo = dbFactory.createHandler("SQL").getPatient(info);
            
            Patient patient = new Patient(patientInfo);
            patients.add(patient);
            
            return patient;
        } catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }
    
}
