package com.example.BackEnd;

abstract public class DBHandler {

  public abstract void saveAppointment(String info);
  public abstract void updateAppointment(int appID, String Reason, int value);
}