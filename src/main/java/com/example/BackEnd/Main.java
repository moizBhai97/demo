package com.example.BackEnd;

public class Main {

    public static void main(String[] args) throws Exception {
        
        PatientController pc = new PatientController();
        System.out.println(pc.bookAppointment(3, "2020-12-10", "12:00:00", "Heart", 1));
    }
    
}
