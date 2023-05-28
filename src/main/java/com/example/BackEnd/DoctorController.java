package com.example.BackEnd;

import java.util.ArrayList;

public class DoctorController {
    private ComplaintCatalog complaintCatalog;

    public void newComplaint(int patID, String details){
        complaintCatalog.newComplaint(patID, details);

    }
    
}
