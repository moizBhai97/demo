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
<<<<<<< HEAD
<<<<<<< HEAD

        // testing
        Patient patient = new Patient(1);
        patient.setName("Musa");
        patient.setEmail("musa@gmail.com");
        patient.setDOB("12/12/1999");
        patient.setCountry("Pakistan");
        patient.setPhoneNumber("123456789");
        patient.setGender("Male");

        patientList.add(patient); 

=======
<<<<<<< HEAD

=======
        //pass dummy email and pass as json 
        //patientList.add(getPatient("{'email':'ali.ahmed@example.com','password':'password123'}") );
      //  patientList.get(0).setAppointments();
        
>>>>>>> 9e307e4084a1af7c934d91a35ea4f3997f39b9aa
>>>>>>> 111ccf957a8557031934701f31c688de5f97c967
=======
>>>>>>> 376907a92e74ebe204b154fb4db66a29b49d04fb
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

    public Patient getPatient(String info)
    {
        try{

            String patientInfo = dbFactory.createHandler("SQL").getPatient(info);
            System.out.println(patientInfo);


            Patient patient = new Patient(patientInfo);
            patientList.add(patient);
            
            return patient;
        } catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
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
    
    public boolean updateProfile(int patId, String info)
    {
        try{
            
            dbFactory.createHandler("SQL").updatePatientProfile(patId, info);
            getPatient(patId).updateProfile(info);


            return true;
        } catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return false;
        }
    }
}
