package com.example.BackEnd;

import org.json.JSONObject;

public class DoctorController {
    private ComplaintCatalog complaintCatalog;
    private DoctorLedger doctorLedger;
    private PatientLedger patientLedger;

    public DoctorController() {

        doctorLedger = new DoctorLedger();
        patientLedger = new PatientLedger();
    }

    //public void setDate()

    public void newComplaint(int patID, String details, int docID) {
        complaintCatalog.newComplaint(patID, details, docID);

    }

    public String login(String info)
    {
        try{

            Doctor doctor = doctorLedger.getDoctorInstance(info);
            doctor.setAppointments();
            patientLedger.setAppointmentPatients(doctor.getId());
            //doctor.setDetails();

            return "" + (doctor.getId());
            
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }

    public void addCertification(String info, int docId)
    {
        try{
            JSONObject json = new JSONObject(info);
            doctorLedger.getDoctor(docId).getDoctorDetails().addCertification(info, docId);


        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
        }
        

    }
    
}
