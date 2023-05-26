package com.example.BackEnd;

import java.security.cert.CertPathValidatorException.Reason;

public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    PatientController()
    {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public String bookAppointment(int docId, String date, String time, String problem, int patId)
    {
        return patientLedger.getPatient(patId).bookAppointment(patId, date, time, problem).getDetails();
    }

    public String getAppointList(int patId)
    {
        return patientLedger.getPatient(patId).getAppointList();
    }

    public Boolean cancelAppointment(String Reason, int patId, int appId)
    {
        return patientLedger.getPatient(patId).cancelAppointment(Reason, appId);
    }
    
}
