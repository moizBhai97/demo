package com.example.BackEnd;

public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public PatientController()
    {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public void bookAppointment(String info, int patId)
    {
        try
        {
            patientLedger.getPatient(patId).bookAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getAppointList(int patId)
    {
        return patientLedger.getPatient(patId).getAppointList();
    }

    public void cancelAppointment(String Reason, int patId, int appId)
    {
        try
        {
            patientLedger.getPatient(patId).cancelAppointment(Reason, appId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void reschAppointment(String info, int patId, int appId)
    {
        try
        {
            patientLedger.getPatient(patId).reschAppointment(info, appId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void getDoctorDetails(int docId)
    {
        try
        {
            //doctorLedger.getDoctor(docId).getDoctorDetails();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String searchDoctor(String name){
         return doctorLedger.getDoctor(name);
    }
}
