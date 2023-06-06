package com.example.BackEnd;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorController {
    private ComplaintCatalog complaintCatalog;
    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public DoctorController() {

        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
        complaintCatalog = new ComplaintCatalog();
    }

    //public void setDate()

    public void newComplaint(int patID, String details, int docID) {
        complaintCatalog.newComplaint(patID, details, docID);

    }

    public String login(String info)
    {
        try{
            Doctor doctor = doctorLedger.getDoctorInstance(info);
            doctor.setAppointments(doctor.getId());
            patientLedger.setAppointmentPatients(doctor.getId());

            return "" + (doctor.getId());
            
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }

    public void addCertification(String info, int docId)
    {
        try{
            //JSONObject json = new JSONObject(info);
            doctorLedger.getDoctor(docId).getDoctorDetails().addCertification(info, docId);


        }catch (Exception e) {
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

    public String getDoctorData(int docId)
    {
        try{
            return doctorLedger.getDoctor(docId).getMainDetails();
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }

    public String getPatientDetails(int patId)
    {
        try
        {
            JSONObject obj = new JSONObject(patientLedger.getPatient(patId).toString());

            JSONArray arr = new JSONArray(obj.getString("appointments"));

            for(int i=0; i<arr.length(); i++)
            {
                int docId = arr.getJSONObject(i).getInt("docId");
                if(doctorLedger.getDoctor(docId) == null)
                {
                    doctorLedger.setDoctor(docId);
                    arr.getJSONObject(i).put("name", doctorLedger.getDoctor(docId).getName());
                    continue;
                }
                arr.getJSONObject(i).put("name", doctorLedger.getDoctor(docId).getName());
            }

            obj.put("appointments", arr.toString());

            return obj.toString();
        }
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }

    public String getPatientHistory(int patId)
    {
        Patient patient = patientLedger.getPatient(patId);

        return patient.getHistory();
    }

    public String getAppointList(int docId, int value) 
    {
        String info = doctorLedger.getDoctor(docId).getAppointList(value);

        JSONArray arr = new JSONArray(info);

        for(int i=0; i<arr.length(); i++)
        {
            arr.getJSONObject(i).put("name", patientLedger.getPatient(arr.getJSONObject(i).getInt("patId")).getName());
        }

        return arr.toString();
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

    public String getAppointment(int docId, int appId) 
    {
        String info = doctorLedger.getDoctor(docId).getAppointment(appId);

        JSONObject obj = new JSONObject(info);

        obj.put("doctor", doctorLedger.getDoctor(docId).getName());

        obj.put("patient", new JSONObject(patientLedger.getPatient(obj.getInt("patId")).getDetails()));

        System.out.println("Patient ID: " + obj.getInt("patId"));

        return obj.toString();
    }

    public String getPatientAppointment(int patId, int appId) 
    {
        String info = patientLedger.getPatient(patId).getAppointment(appId);

        JSONObject obj = new JSONObject(info);

        obj.put("doctor", doctorLedger.getDoctor(obj.getInt("docId")).getName());

        obj.put("patient", new JSONObject(patientLedger.getPatient(patId).getDetails()));

        return obj.toString();
    }
}
