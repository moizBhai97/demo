package com.example.BackEnd;

abstract public class DBHandler {

  public abstract void saveAppointment(String info, int patId);
  public abstract void updateAppointment(int appID, String Reason, int value);
  public abstract String getDoctors(String name);
  public abstract String getPatient(String name);
  public abstract String getReviewList(int docId);
<<<<<<< HEAD
  public abstract void addPayment(String info, int appId);
=======
  public abstract String getAppointments(int patId);
>>>>>>> 83d1be6f875ba7d84706f2fe449c752ccc7b70f5
}