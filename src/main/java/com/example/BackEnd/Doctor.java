package com.example.BackEnd;

import java.io.FileReader;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Doctor {
    private int id;
    private String name;

    private DoctorDetails doctorDetails;

    public DoctorDetails getDoctorDetails() 
    {
        return doctorDetails;
    }

    public String getDetails() 
    {
        try 
        {

            String details = doctorDetails.toString();

            JSONObject obj = new JSONObject(details);

            obj.put("name", name);

            return obj.toString();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public Doctor(int docId, String doctorName) 
    {
        this.id = docId;
        this.name = doctorName;
    }


    public Doctor(String data) 
    {
        System.out.println("Constructing JSON doctor");

        JSONObject jsonObject = new JSONObject(data);
        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.doctorDetails = new DoctorDetails(jsonObject.getJSONObject("details").toString(), id);
    }

    public Doctor()
    {

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

    public float getFee()
    {
        return doctorDetails.getFee();
    }

    public float getRating()
    {
        return doctorDetails.getRating();
    }

    private String get(String key) {
        if (key.equals("id")) {
            return String.valueOf(id);
        } else if (key.equals("name")) {
            return name;
        } else
            return null;
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