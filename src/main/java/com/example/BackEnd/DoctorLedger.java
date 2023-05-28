package com.example.BackEnd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorLedger {

    private ArrayList<Doctor> doctorList;

    public DoctorLedger()
    {
        doctorList = new ArrayList<Doctor>();
    }

    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

    private void removeDuplicates() {
        Set<Doctor> doctorSet = new HashSet<>(doctorList);
        doctorList.clear();
        doctorList.addAll(doctorSet);
    }

    public String getDoctor(String name) {
        String json = DBFactory.getInstance().createHandler("SQL").getDoctors(name);

        JSONArray jsonArray = new JSONArray(json);

        List<Doctor> doctors = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Doctor doctor = new Doctor(jsonObject.toString());
            doctors.add(doctor);
        }

        doctorList.addAll(doctors);
        removeDuplicates();

        System.out.println(doctorList.toString());

        return json.toString();
    }

    public String sortByAlphabetical(String name, Boolean reversed, double ratingFilter, String specialtyFilter) {

        List<Doctor> tempDoctors = new ArrayList<>(doctorList);

        // Filter the list to only include doctors with the given name
        tempDoctors = tempDoctors.stream()
                .filter(doctor -> doctor.getName().contains(name))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name
        // print
        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All") ) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getSpecialization().equals(specialtyFilter))
                    .collect(Collectors.toList());
        }

        if (!reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getName));
        } else if (reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getName).reversed());
        }

        // convert to json
        JSONArray doctors = new JSONArray();
        for (int i = 0; i < tempDoctors.size(); i++) {
            doctors.put(new JSONObject(tempDoctors.get(i).toString()));
        }
        // System.out.println(doctors.toString());
        return doctors.toString();

    }

    public String sortByPrice(String name, Boolean reversed, double ratingFilter, String specialtyFilter) {
        List<Doctor> tempDoctors = new ArrayList<>(doctorList);

        // Filter the list to only include doctors with the given name
        tempDoctors = tempDoctors.stream()
                .filter(doctor -> doctor.getName().contains(name))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name

        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All") ) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getSpecialization().equals(specialtyFilter))
                    .collect(Collectors.toList());
        }

        if (!reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getPrice));
        } else if (reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getPrice).reversed());
        }
        // convert to json
        JSONArray doctors = new JSONArray();
        for (int i = 0; i < tempDoctors.size(); i++) {
            doctors.put(new JSONObject(tempDoctors.get(i).toString()));
        }
        // System.out.println(doctors.toString());
        return doctors.toString();
    }
    public Doctor getDoctor(int docId)
    {
        for(int i = 0; i < doctorList.size(); i++)
        {
            if(doctorList.get(i).getId() == docId)
            {
                return doctorList.get(i);
            }
        }

        return null;
    }

    public String sortByRating(String name, Boolean reversed, double ratingFilter, String specialtyFilter) {
        List<Doctor> tempDoctors = new ArrayList<>(doctorList);

        // Filter the list to only include doctors with the given name
        tempDoctors = tempDoctors.stream()
                .filter(doctor -> doctor.getName().contains(name))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name

        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All") ) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getSpecialization().equals(specialtyFilter))
                    .collect(Collectors.toList());
        }

        if (!reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getRating));
        } else if (reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getRating).reversed());
        }
        // convert to json
        JSONArray doctors = new JSONArray();
        for (int i = 0; i < tempDoctors.size(); i++) {
            doctors.put(new JSONObject(tempDoctors.get(i).toString()));
        }
        // System.out.println(doctors.toString());
        return doctors.toString();
    }

}