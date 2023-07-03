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

    public void newComplaint(int patID, String details, int docID) 
    {
        try
        {
            complaintCatalog.newComplaint(patID, details, docID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String login(String info) throws Exception
    {
        try{
            Doctor doctor = doctorLedger.getDoctorInstance(info);
            doctor.setAppointments(doctor.getId());
            patientLedger.setAppointmentPatients(doctor.getId());

            return "" + (doctor.getId());
            
        }catch (Exception e) {
            throw e;
        }
        
    }

    public void addCertification(String info, int docId) throws Exception
    {
        try{
            doctorLedger.getDoctor(docId).getDoctorDetails().addCertification(info, docId);

        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        

    }

    public String getCertificates(int docId)
    {
        try{
            JSONArray certificates = new JSONArray(doctorLedger.getDoctor(docId).getDoctorDetails().getCertificates());
            return certificates.toString();

        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void verifyPayment(int docId, int appId)
    {
        try
        {
            doctorLedger.getDoctor(docId).verifyPayment(appId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getDoctorData(int docId)
    {
        try{
            return doctorLedger.getDoctor(docId).getMainDetails();
        }catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return "";
        }
        
    }

    public String getPatientHistory(int patId)
    {
        try
        {
            Patient patient = patientLedger.getPatient(patId);

            return patient.getHistory();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public String getAppointList(int docId, int value) 
    {
        try
        {
            String info = doctorLedger.getDoctor(docId).getAppointList(value);

            JSONArray arr = new JSONArray(info);

            for(int i=0; i<arr.length(); i++)
            {
                arr.getJSONObject(i).put("name", patientLedger.getPatient(arr.getJSONObject(i).getInt("patId")).getName());
            }

            return arr.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    public String getAppointment(int docId, int appId) 
    {
        try
        {
            String info = doctorLedger.getDoctor(docId).getAppointment(appId);

            JSONObject obj = new JSONObject(info);

            obj.put("doctor", doctorLedger.getDoctor(docId).getName());

            obj.put("patient", new JSONObject(patientLedger.getPatient(obj.getInt("patId")).getDetails()));

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public String getPatientAppointment(int patId, int appId) 
    {
        try
        {
            String info = patientLedger.getPatient(patId).getAppointment(appId);

            JSONObject obj = new JSONObject(info);

            obj.put("doctor", doctorLedger.getDoctor(obj.getInt("docId")).getName());

            obj.put("patient", new JSONObject(patientLedger.getPatient(patId).getDetails()));

            return obj.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
