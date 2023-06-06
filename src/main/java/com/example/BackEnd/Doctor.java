package com.example.BackEnd;

import java.io.FileReader;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Doctor {
    private int id;
    private String name;
    private String email;
    private String DOB;
    private String country;
    private String phoneNumber;
    private String gender;
    private AppointmentLedger appointmentLedger;
    private DoctorDetails doctorDetails;

    public DoctorDetails getDoctorDetails() 
    {
        return doctorDetails;
    }

    public String getDetails() 
    {
        System.out.println("Doctor getDetails");
        try 
        {
            String details = doctorDetails.toString();

            JSONObject obj = new JSONObject(details);

            obj.put("name", name);
            obj.put("email", email);
            obj.put("DOB", DOB);
            obj.put("country", country);
            obj.put("phoneNumber", phoneNumber);
            obj.put("gender", gender);

            System.out.println("--------------------------------"+obj.toString());
            return obj.toString();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public Doctor(int docId) 
    {
        this.id = docId;
        this.name = DBFactory.getInstance().createHandler("SQL").getDoctorName(docId);
    }
    
    public Doctor(String data) 
    {
        System.out.println("Constructing JSON doctor");

        JSONObject jsonObject = new JSONObject(data);
        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.email = jsonObject.getString("email");
        this.DOB = jsonObject.getString("DOB");
        this.country = jsonObject.getString("country");
        this.phoneNumber = jsonObject.getString("phoneNumber");
        this.gender = jsonObject.getString("gender");
        this.doctorDetails = new DoctorDetails(jsonObject.getJSONObject("details").toString(), id);
    }
    
    public Doctor()
    {
        doctorDetails = new DoctorDetails();
    }
    
    public void setDoctorDetails(String info)
    {
        JSONObject obj = new JSONObject(info);
        
        this.doctorDetails = new DoctorDetails(obj.toString(), id);
        
    }
    
    public void setAppointments(int docId)
    {
        if(appointmentLedger == null)
            appointmentLedger = new AppointmentLedger();
            
        appointmentLedger.setDoctorAppointments(docId);
    }

    public String getAppointList(int value)
    {
        return appointmentLedger.getAppointList(value);
    }

    public void verifyPayment(int appId)
    {
        appointmentLedger.getAppointment(appId).verifyPayment();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dOB) {
        DOB = dOB;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getFee()
    {
        return doctorDetails.getFee();
    }

    public float getRating()
    {
        return doctorDetails.getRating();
    }

    public String getAppointment(int appId)
    {
        System.out.println("Getting appointment");
        System.out.println(appointmentLedger.getAppointment(appId).toString());
        return appointmentLedger.getAppointment(appId).toString();
    }

    public String getMainDetails()
    {
        JSONObject obj = new JSONObject();

        obj.put("name", name);
        obj.put("email", email);
        obj.put("DOB", DOB);
        obj.put("country", country);
        obj.put("phoneNumber", phoneNumber);
        obj.put("gender", gender);
        

        return obj.toString();
    }

    private String get(String key) {
        if (key.equals("id")) {
            return String.valueOf(id);
        } else if (key.equals("name")) {
            return name;
        } else if (key.equals("email")){
            return email;
        } else if(key.equals("DOB")){
            return DOB;
        } else if(key.equals("country")){
            return country;
        } else if(key.equals("phoneNumber")){
            return phoneNumber;
        } else if(key.equals("gender")){
            return gender;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        try {
            JSONParser parser = new JSONParser();

            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());

            Set<String> keyset = obj.keySet();

            for (String key : keyset) 
            {
                if (get(key) != null)
                    obj.put(key, get(key));
            }

            obj.put("details", new JSONObject(getDoctorDetails().toString()));

            return obj.toString();

        } catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) obj;
        return Objects.equals(name, other.name) && Objects.equals(id, other.id)
                && Objects.equals(getDoctorDetails().getSpecialization(), other.getDoctorDetails().getSpecialization())
                && Objects.equals(getDoctorDetails().getLocation(), other.getDoctorDetails().getLocation());
    }

}