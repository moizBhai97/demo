package com.example.BackEnd;

abstract public class DBHandler {

  public abstract void saveAppointment(String info, int patId);
  public abstract void updateAppointment(int appID, String Reason, int value);
  public abstract String getDoctors(String name);
  public abstract String getReviewList(int docId);
}