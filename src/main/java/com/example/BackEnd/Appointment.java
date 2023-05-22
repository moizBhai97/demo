package com.example.BackEnd;

public class Appointment {
    
    private String date;
    private String time;
    private String problem;
    private int patId;

    Appointment()
    {
        this.date="";
        this.time="";
        this.problem="";
        this.patId=0;
    }

    Appointment(String date, String time, String problem, int patId)
    {
        this.date=date;
        this.time=time;
        this.problem=problem;
        this.patId=patId;
    }

    public String getDetails()
    {
        String s="";
        s=s+date+"\n";
        s=s+time+"\n";
        s=s+problem+"\n";
        s=s+patId+"\n";

        return s;
    } 



}
