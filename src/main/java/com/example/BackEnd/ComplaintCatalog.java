package com.example.BackEnd;

import java.util.ArrayList;

public class ComplaintCatalog {

    private ArrayList<Complaint> complaints;

    public ComplaintCatalog()
    {
        complaints = new ArrayList<>();
    }

    public void newComplaint(int patID, String details, int docID)
    {
        try
        {
            DBFactory.getInstance().createHandler("SQL").addComplaint(patID, details, docID);
            complaints.add(new Complaint(patID, details, docID));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
