package com.example.BackEnd;

abstract public class DBHandler {

  public abstract void saveAppointment(String date, String time, String problem, int patId);
  
}