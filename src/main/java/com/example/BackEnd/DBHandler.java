package com.example.BackEnd;

abstract public class DBHandler {

  public abstract void bookAppointment(String info);
  public abstract void cancelAppointment(String info);
  public abstract int saveAppointment(String info, int patId);
  public abstract void updateAppointment(int appID, String Reason, int value);
  public abstract String getDoctors(String name);
  public abstract String getTopDoctors();
  public abstract String getAppointmentDoctors(int patId);
  public abstract String getAppointmentPatients(int docId);
  public abstract String getPatient(String info);
  public abstract String getPatientHistory(int patId);
  public abstract String getDoctor(String info);
  public abstract String getReviewList(int docId);
  public abstract String getCertificates(int docId);
  public abstract String getPatientAppointments(int patId);
  public abstract String getDoctorAppointments(int docId);
  public abstract String getDoctorDetails(int docID);
  public abstract void addCertification(String info, int docId);
  public abstract void addComplaint(int patID, String details, int docID);
  public abstract void addReview(String info, int patId, int docId);
  public abstract void addPatient(String info);
  public abstract void addPatientIllness(int patId, String info);
  public abstract void deletePatientIllness(int patId, int sid);
  public abstract void updatePatientProfile(int patId, String info);
  public abstract String getSchedule(int docId, String date);
  public abstract String getDoctorName(int docId);
  public abstract String getPatientName(int patId);
}