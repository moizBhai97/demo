package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

public class PatientLedger {

    private List<Patient> patients;

    public PatientLedger()
    {
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
    
}
