package com.example.BackEnd;

import java.util.ArrayList;

public class ComplaintCatalog {

    private ArrayList<Complaint> complaints;

    public ComplaintCatalog()
    {
        complaints = new ArrayList<>();
    }

    public void newComplaint(int patID, String details){
        complaints.add(new Complaint(patID, details));
        //call db to add complaint

         DBFactory.getInstance().createHandler("SQL").addComplaint(patID, details);
    }


}