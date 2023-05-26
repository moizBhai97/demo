package com.example.BackEnd;

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
    
}
