package com.example.BackEnd;

import java.io.FileReader;
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
    private String image;
    // private //String description;

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

    // public String getImage() {
    // return image;
    // }

    // public void setImage(String image) {
    // this.image = image;
    // }

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
            JSONObject obj = new JSONObject(parser.parse(new FileReader("src/main/resources/JSONPackage/Doctor.json")).toString());
            Set<String> keyset = obj.keySet();
            for (String key : keyset) {
                if (get(key) != null)
                    obj.put(key, get(key));
            }
            System.out.println(obj.toString());
            return obj.toString();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());
            return null;
        }
    }

}