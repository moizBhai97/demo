package com.example.BackEnd;

import java.io.FileReader;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Doctor {
    private int id;
    private String name;
    private String location;
    private String specialization;
    private String experience;
    private double price;
    private double rating;

    private DoctorDetails doctorDetails;

    public DoctorDetails getDoctorDetails() 
    {
        return doctorDetails;
    }

    public String getDetails() 
    {
        try 
        {
            if(doctorDetails == null)
            {
                String json = DBFactory.getInstance().createHandler("SQL").getDoctorDetails(id);
                this.doctorDetails = new DoctorDetails(json, id);
            }

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

    public Doctor(String doctorName, String specialization, String location, String experience, Double priceDouble,
            Double ratingDouble) {
        this.name = doctorName;
        this.specialization = specialization;
        this.location = location;
        this.experience = experience;
        this.price = priceDouble;
        this.rating = ratingDouble;

    }

    public Doctor(String data) {
        System.out.println("Constructing JSON doctor");

        JSONObject jsonObject = new JSONObject(data);
        this.id = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.location = jsonObject.getString("location");
        this.specialization = jsonObject.getString("specialization");
        this.experience = jsonObject.getString("experience");
        this.price = jsonObject.getDouble("price");
        this.rating = jsonObject.getDouble("rating");
    }

    public Doctor()
    {

    }

    public void setDoctorDetails(String info)
    {
        JSONObject obj = new JSONObject(info);

        this.doctorDetails = new DoctorDetails(obj.toString(), id);
        
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private String get(String key) {
        if (key.equals("id")) {
            return String.valueOf(id);
        } else if (key.equals("name")) {
            return name;
        } else if (key.equals("location")) {
            return location;
        } else if (key.equals("specialization")) {
            return specialization;
        } else if (key.equals("experience")) {
            return experience;
        } else if (key.equals("price")) {
            return String.valueOf(price);
        } else if (key.equals("rating")) {
            return String.valueOf(rating);
        } else
            return null;
    }

    @Override
    public String toString() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = new JSONObject(
                    parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());
            Set<String> keyset = obj.keySet();
            for (String key : keyset) {
                if (get(key) != null)
                    obj.put(key, get(key));
            }
            System.out.println(obj.toString());
            return obj.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
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
                && Objects.equals(specialization, other.specialization)
                && Objects.equals(location, other.location);
    }

}