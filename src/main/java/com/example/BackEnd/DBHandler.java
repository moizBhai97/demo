package com.example.BackEnd;

abstract public class DBHandler {

  public abstract int saveAppointment(String info, int patId);
  public abstract void updateAppointment(int appID, String Reason, int value);
  public abstract String getDoctors(String name);
  public abstract String getTopDoctors();
  public abstract String getPatient(String info);
  public abstract String getDoctor(String info);
  public abstract String getReviewList(int docId);
  public abstract void addPayment(String info, int appId);
  public abstract String getAppointments(int patId);
  public abstract String getDoctorDetails(int docID);
  public abstract void addComplaint(int patID, String details, int docID);
  public abstract void updatePatientProfile(int patId, String info);
}