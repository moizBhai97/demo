package com.example.BackEnd;

import java.util.ArrayList;

public class DoctorController {
    private ComplaintCatalog complaintCatalog;
    private DoctorLedger doctorLedger;

    public DoctorController() {

        doctorLedger = new DoctorLedger();
    }

    //public void setDate()

    public void newComplaint(int patID, String details, int docID) {
        complaintCatalog.newComplaint(patID, details, docID);

    }

    public String login(String info)
    {
        try{

            Doctor doctor = doctorLedger.getDoctorInstance(info);
            //doctor.setAppointments();

            return "" + (doctor.getId());
            
        }catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return "";
        }
        
    }
    
}
