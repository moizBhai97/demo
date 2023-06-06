package com.example.BackEnd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorLedger {

    private ArrayList<Doctor> topDoctors;
    private ArrayList<Doctor> doctorList;

    public DoctorLedger() {
        topDoctors = new ArrayList<Doctor>();
        doctorList = new ArrayList<Doctor>();
      
    }

    public void setTopDoctors() {
        System.out.println("Ledger Setting top doctors");
        try {
            String doctors = DBFactory.getInstance().createHandler("SQL").getTopDoctors();

            JSONArray jsonArray = new JSONArray(doctors);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Doctor doctor = new Doctor(jsonObject.toString());
                topDoctors.add(doctor);
                doctor.toString();
            }

            doctorList.addAll(topDoctors);
            removeDuplicates();

            topDoctors.toString();
            doctorList.addAll( topDoctors);
            removeDuplicates();
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    public void setAppointmentDoctors(int patId) {
        try {
            String doctors = DBFactory.getInstance().createHandler("SQL").getAppointmentDoctors(patId);

            JSONArray jsonArray = new JSONArray(doctors);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Doctor doctor = new Doctor(jsonObject.toString());
                doctorList.add(doctor);
            }

            //System.out.println(doctorList.toString());

        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

    private void removeDuplicates() {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor1 = doctorList.get(i);
            for (int j = i + 1; j < doctorList.size(); j++) {
                Doctor doctor2 = doctorList.get(j);
                if (doctor1.getName().equals(doctor2.getName())
                        && doctor1.getDoctorDetails().getSpecialization().equals(doctor2.getDoctorDetails().getSpecialization())
                        && doctor1.getDoctorDetails().getLocation().equals(doctor2.getDoctorDetails().getLocation())) {
                    doctorList.remove(j);
                    j--;
                }
            }

        }
    }

    public Doctor getDoctorInstance(String info) {
        try {

            String doctorInfo = DBFactory.getInstance().createHandler("SQL").getDoctor(info);

            Doctor doctor = new Doctor(doctorInfo);
            doctorList.add(doctor);
            System.out.println("-----" + doctorList.toString());

            return doctor;
        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            return null;
        }
    }

    public String getDoctor(String name) {
        String json = DBFactory.getInstance().createHandler("SQL").getDoctors(name);

        JSONArray jsonArray = new JSONArray(json);

        JSONArray jsonDoctors = new JSONArray();

        List<Doctor> doctors = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Doctor doctor = new Doctor(jsonObject.toString());
            jsonDoctors.put(new JSONObject(doctor.toString()));
            doctors.add(doctor);
        }

        doctorList.addAll(doctors);
        removeDuplicates();

        return jsonDoctors.toString();
    }

    public String sortByAlphabetical(String name, Boolean reversed, double ratingFilter, String specialtyFilter) {

        List<Doctor> tempDoctors = new ArrayList<>(doctorList);

        // Filter the list to only include doctors with the given name
        tempDoctors = tempDoctors.stream()
                .filter(doctor -> doctor.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name
        // print

        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All")) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getSpecialization().equals(specialtyFilter))
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
                .filter(doctor -> doctor.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name

        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All")) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getSpecialization().equals(specialtyFilter))
                    .collect(Collectors.toList());
        }

        if (!reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getFee));
        } else if (reversed) {
            tempDoctors.sort(Comparator.comparing(Doctor::getFee).reversed());
        }
        // convert to json
        JSONArray doctors = new JSONArray();
        for (int i = 0; i < tempDoctors.size(); i++) {
            doctors.put(new JSONObject(tempDoctors.get(i).toString()));
        }

        // print doctors

        return doctors.toString();
    }

    public Doctor getDoctor(int docId) 
    {
        System.out.println("Ledger getDoctor\n" + doctorList.toString());

        for(int i = 0; i < doctorList.size(); i++)
        {
            if(doctorList.get(i).getId() == docId)
            {
                return doctorList.get(i);
            }
        }

        return null;
    }

    public void setDoctor(int docId) {
        try {

            Doctor doctor = new Doctor(docId);
            doctorList.add(doctor);

        } catch (Exception e) {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {}.getClass().getEnclosingMethod().getName());
        }
    }

    public String sortByRating(String name, Boolean reversed, double ratingFilter, String specialtyFilter) {
        List<Doctor> tempDoctors = new ArrayList<>(doctorList);

        // Filter the list to only include doctors with the given name
        tempDoctors = tempDoctors.stream()
                .filter(doctor -> doctor.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        // Sort the list alphabetically by name

        if (ratingFilter != -1) {
            // Filter the list to only include doctors with the given rating
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getRating() >= ratingFilter)
                    .collect(Collectors.toList());
        }
        if (specialtyFilter != null && !specialtyFilter.equals("All")) {
            // Filter the list to only include doctors with the given specialty
            tempDoctors = tempDoctors.stream()
                    .filter(doctor -> doctor.getDoctorDetails().getSpecialization().equals(specialtyFilter))
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

    public String getTopDoctors() {

        JSONArray doctors = new JSONArray();
        for (int i = 0; i < topDoctors.size(); i++) {
            doctors.put(new JSONObject(topDoctors.get(i).toString()));
            System.out.println(topDoctors.get(i).toString());
        }

        return doctors.toString();
    }

}