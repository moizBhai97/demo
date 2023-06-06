package com.example.BackEnd;

import org.json.JSONArray;
import org.json.JSONObject;

public class PatientController {

    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public PatientController() {
        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    public void signup(String info)
    {
        try{
            patientLedger.addPatient(info);
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());

        }
    }

    public String login(String info)
    {
        System.out.println("Pateint controller login");
        try{

            Patient patient = patientLedger.getPatient(info);
            if(patient == null)
            {
                throw new Exception("Patient not found");
            }

            patient.populateAppointments();
            doctorLedger.setAppointmentDoctors(patient.getpatId());
            doctorLedger.setTopDoctors();

            System.out.println("Patient controller login success");

            System.out.println("Login: " + patient.toString());

            return "" + (patient.getpatId());
            
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }

    public String getPatientData(int patId)
    {
        try{

            return patientLedger.getPatient(patId).getDetails();
        }catch (Exception e) {

            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
    }

    public void updateProfile(int patId, String info)
    {
        patientLedger.updateProfile(patId, info);

    }

    public void addIllness(int patId, String info)
    {
        patientLedger.getPatient(patId).addIllness(patId, info);
    }

    public void removeIllness(int patId, int sid)
    {
        patientLedger.getPatient(patId).removeIllness(patId, sid);
    }

    public String getPatientHistory(int patId)
    {
        Patient patient = patientLedger.getPatient(patId);

        return patient.getHistory();
    }

    public void submitReview(String info, int docId, int patId)
    {
        try
        {
            doctorLedger.getDoctor(docId).getDoctorDetails().addReview(info, patId, docId);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());

        }
    }

    public void bookSlot(String info, int patId)
    {
        try
        {
            patientLedger.getPatient(patId).bookSlot(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());

        }
    }

    public void cancelSlot(String info, int patId)
    {
        try
        {
            patientLedger.getPatient(patId).cancelSlot(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());

        }
    }

    public void saveAppointment(String info, int patId)
    {
        try
        {
            patientLedger.getPatient(patId).saveAppointment(info);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public String getAppointList(int patId, int value) 
    {
        String info = patientLedger.getPatient(patId).getAppointList(value);

        JSONArray arr = new JSONArray(info);

        for(int i=0; i<arr.length(); i++)
        {
            arr.getJSONObject(i).put("name", doctorLedger.getDoctor(arr.getJSONObject(i).getInt("docId")).getName());
            arr.getJSONObject(i).put("rating", doctorLedger.getDoctor(arr.getJSONObject(i).getInt("docId")).getRating());
        }

        return arr.toString();
    }

    public String getAppointment(int patId, int appId) 
    {
        String info = patientLedger.getPatient(patId).getAppointment(appId);

        JSONObject obj = new JSONObject(info);

        obj.put("doctor", new JSONObject(doctorLedger.getDoctor(obj.getInt("docId")).getDetails()));

        obj.put("patient", new JSONObject(patientLedger.getPatient(patId).getDetails()));

        return obj.toString();
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

    public String getCertificates(int docId)
    {
        try{
            JSONArray certificates = new JSONArray(doctorLedger.getDoctor(docId).getDoctorDetails().getCertificates());
            System.out.println(certificates.toString());
            return certificates.toString();

        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
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
            JSONArray arr= new JSONArray(doctorLedger.getDoctor(docId).getDoctorDetails().getReviewList(docId));

            for(int i=0; i<arr.length(); i++)
            {
                int patId = arr.getJSONObject(i).getInt("patId");
                if(patientLedger.getPatient(patId) == null)
                {
                    patientLedger.setPatient(patId);
                    arr.getJSONObject(i).put("name", patientLedger.getPatient(patId).getName());
                    continue;
                }

                arr.getJSONObject(i).put("name", patientLedger.getPatient(patId).getName());

            }

            return arr.toString();
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getSchedule(int docId, String date, int value)
    {
        try
        {
            return doctorLedger.getDoctor(docId).getDoctorDetails().getSchedule(docId, date, value);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
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
}
