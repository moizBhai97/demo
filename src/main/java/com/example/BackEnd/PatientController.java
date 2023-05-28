package com.example.BackEnd;

public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public PatientController() {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public String login(String info)
    {
        try{

            Patient patient = patientLedger.getPatient(info);
            patient.setAppointments();

            doctorLedger.setTopDoctors();

            return "" + (patient.getpatId());
            
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
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

    public String getAppointList(int patId) {
        return patientLedger.getPatient(patId).getAppointList();
    }

    public void cancelAppointment(String Reason, int patId, int appId) {
        try {
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

    public String getDocDetails(int docId)
    {
        try
        {
            return doctorLedger.getDoctor(docId).getDetails();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getReviews(int docId)
    {
        try
        {
            return doctorLedger.getDoctor(docId).getDoctorDetails().getReviewList(docId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public void makePayment(String info, int patId, int appId)
    {
        try
        {
            patientLedger.getPatient(patId).makePayment(info, appId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String searchDoctor(String name) {
        return doctorLedger.getDoctor(name);
    }

    public String getTopDoctors(){
        return doctorLedger.getTopDoctors();
    }
    
    public String sortDoctors(String name, String type,Boolean reversed, double ratingFilter, String specialtyFilter) {

        if (type.equals("A-Z")) {
            return doctorLedger.sortByAlphabetical(name,reversed,ratingFilter,specialtyFilter);
        }
        else if (type.equals("Rating")) {
           return doctorLedger.sortByRating(name,reversed,ratingFilter,specialtyFilter);
        }
        else if (type.equals("Price")) {
            return doctorLedger.sortByPrice(name,reversed,ratingFilter,specialtyFilter);
        }
            return "Invalid type";
        
    }

    public String editDetails(String info, int patId) {
        try {
         //   patientLedger.editPatientDetails(info);
            return "Details edited successfully";
        }
        catch(Exception e) {
            return "Error editing details";
        }
    }
}
