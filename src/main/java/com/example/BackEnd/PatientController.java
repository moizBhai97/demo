package com.example.BackEnd;

public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public PatientController() {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public void bookAppointment(String info, int patId) {
        try {
            patientLedger.getPatient(patId).bookAppointment(info);
        } catch (Exception e) {
            System.out.println(e + " " + getClass().getName());
        }
    }

    public String getAppointList(int patId) {
        return patientLedger.getPatient(patId).getAppointList();
    }

    public void cancelAppointment(String Reason, int patId, int appId) {
        try {
            patientLedger.getPatient(patId).cancelAppointment(Reason, appId);
        } catch (Exception e) {
            System.out.println(e + " " + getClass().getName());
        }
    }

    public String searchDoctor(String name) {
        return doctorLedger.getDoctor(name);
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
}
