package com.example.BackEnd;

import java.util.ArrayList;

import org.json.JSONString;

public class DoctorLedger {
    private ArrayList<Doctor> doctorList;

    DoctorLedger()
    {
        doctorList = new ArrayList<Doctor>();
    }

    public void addDoctor(Doctor doctor)
    {
        doctorList.add(doctor);
    }


public ArrayList<Doctor> getDummyDoctor(String name){
    ArrayList<Doctor> dummyDoctors = new ArrayList<Doctor>();
    String[] specializations = {"Cardiology", "Dermatology", "Endocrinology", "Gastroenterology", "Hematology", "Neurology", "Oncology", "Pediatrics", "Psychiatry", "Urology"};
    String[] hospitals = {"Mayo Clinic", "Johns Hopkins Hospital", "Cleveland Clinic", "Massachusetts General Hospital", "UCSF Medical Center", "Brigham and Women's Hospital", "New York-Presbyterian Hospital", "Stanford Health Care-Stanford Hospital", "Hospitals of the University of Pennsylvania-Penn Presbyterian", "Cedars-Sinai Medical Center"};
    for(int i = 0; i < 10; i++){
        Doctor doctor = new Doctor();
        doctor.setName(name + " " + i);
        doctor.setSpecialization(specializations[i]);
        doctor.setExperience((i + 1) * 5 + " years");
        doctor.setRating( (Math.random() * 5));
        doctor.setLocation(hospitals[i]);
        dummyDoctors.add(doctor);
    }
    return dummyDoctors;

    }

    public String getDoctor(String name)
    {
        // DBHandler db = new DBHandler();
        String result = "";
       // ArrayList<Doctor> result = db.getDoctor(name);
        // for(Doctor doctor : doctorList)
        // {
        //     if(doctor.getName().equals(name))
        //     {
        //         result += doctor.getDetails();
        //     }
        // }
        ArrayList<Doctor> resultDoctors =getDummyDoctor(name);
            

        
        return result;
    }
    
}