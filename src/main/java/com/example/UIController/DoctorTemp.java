package com.example.UIController;

import java.util.ArrayList;
import java.util.List;

public class DoctorTemp{
    String name;
    String speciality;
    float distance;
    String address;
    

    static List<DoctorTemp> getDummyDoctor(){
        List<DoctorTemp> doctorTemps = new ArrayList<DoctorTemp>();
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        
        
        //generate random doctorTemps
        for(int i=0;i<10;i++){
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                sb.append(chars[(int) (Math.random() * chars.length)]);
            }
            String randomString = sb.toString();
            DoctorTemp doctorTemp = new DoctorTemp();
            doctorTemp.name = randomString;
            doctorTemp.speciality = randomString;
            doctorTemp.distance = (float) Math.random();
            doctorTemp.address = randomString;
            doctorTemps.add(doctorTemp);
        }
        return doctorTemps;
        
    }

}
