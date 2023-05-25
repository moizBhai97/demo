package com.example.BackEnd;

import org.json.simple.JSONObject;
public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    PatientController()
    {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public JSONObject bookAppointment(int docId, String date, String time, String problem, int patId)
    {
        return patientLedger.getPatient(patId).bookAppointment(patId, date, time, problem).getDetails();
    }
    
}
