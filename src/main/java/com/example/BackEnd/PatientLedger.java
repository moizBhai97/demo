package com.example.BackEnd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PatientLedger {

    DBFactory dbFactory;
    private List<Patient> patientList;

    public PatientLedger()
    {
        dbFactory = DBFactory.getInstance();
        patientList = new ArrayList<Patient>();

        // Patient patient = new Patient(1);
        // patient.setName("Musa");
        // patient.setEmail("musa@gmail.com");
        // patient.setDOB("12/12/1999");
        // patient.setCountry("Pakistan");
        // patient.setPhoneNumber("123456789");
        // patient.setGender("Male");

        // patientList.add(patient);
        
    }

    public void addPatient(String info) throws Exception
    {
        try{
            dbFactory.createHandler("SQL").addPatient(info);
        }catch (Exception e) {
            //System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            throw e;
        }
    }

    public void setPatient(int patId)
    {
        try{
            Patient patient = new Patient(patId);
            patientList.add(patient);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }

    public void setAppointmentPatients(int docId)
    {
        try {
            String patients = DBFactory.getInstance().createHandler("SQL").getAppointmentPatients(docId);

            JSONArray jsonArray = new JSONArray(patients);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Patient patient = new Patient(jsonObject.toString());
                patientList.add(patient);
            }

        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    public Patient getPatient(int patId)
    {

        for(int i = 0; i < patientList.size(); i++)
        {
            if(patientList.get(i).getpatId() == patId)
            {
                return patientList.get(i);
            }
        }

        return null;
    }    

    public Patient getPatient(String info) throws Exception
    {
        try{

            String patientInfo = dbFactory.createHandler("SQL").getPatient(info);
            System.out.println(patientInfo);


            Patient patient = new Patient(patientInfo);
            patientList.add(patient);
            
            return patient;
        } catch(Exception e)
        {
            //System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            throw e;
        }
    }

    public String getPatientDetails(int patId)
    {
        try{
            return getPatient(patId).getDetails();
        } catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
    }
    
    public void updateProfile(int patId, String info)
    {
        try{
            
            dbFactory.createHandler("SQL").updatePatientProfile(patId, info);
            getPatient(patId).updateProfile(info);

        } catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
    }
}
