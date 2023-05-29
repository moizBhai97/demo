package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class DoctorDetailsController implements Initializable{
    
    int docId;
    int patId;
    PatientController pc;

    @FXML
    private Label name;
    
    @FXML
    private Label specialization;

    @FXML
    private Label description;

    @FXML
    private Label location;

    @FXML
    private Label stats;

    @FXML
    private Label patients;

    @FXML
    private Label experience;

    @FXML
    private Label rating;

    @FXML
    private Label services;

    @FXML
    private Label timing;

    @FXML
    private Label fee;

    @FXML
    private Label reviews;

    @FXML
    private Label avgCheckup;

    @FXML
    private Label avgEnv;

    @FXML
    private Label avgStaff;

    @FXML
    private Label availability;

    @FXML
    private ProgressBar checkProgress;

    @FXML
    private ProgressBar envProgress;

    @FXML
    private ProgressBar staffProgress;

    @FXML
    private Button bookButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
         
        System.out.println(docId);

        String info = pc.getDocDetails(docId);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getString("name"));
        specialization.setText(obj.getString("specialization"));
        description.setText(obj.getString("description"));
        location.setText(obj.getString("location"));
        stats.setText(String.format("%.1f", obj.getFloat("stats")) + "%");
        patients.setText(obj.getInt("patients") + "");
        experience.setText(obj.getInt("experience") + "");
        rating.setText(String.format("%.1f", obj.getFloat("rating")));
        services.setText(obj.getString("services"));
        timing.setText(obj.getString("workingHours"));
        fee.setText(String.format("%.1f", obj.getFloat("fee")));
        availability.setText(obj.getString("availability"));
        reviews.setText(obj.getInt("reviews") + "");
        avgCheckup.setText(String.format("%.1f", obj.getFloat("checkupRating")) + "%");
        avgEnv.setText(String.format("%.1f", obj.getFloat("environmentRating")) + "%");
        avgStaff.setText(String.format("%.1f", obj.getFloat("staffRating")) + "%");

        checkProgress.setProgress(obj.getFloat("checkupRating") / 100);
        envProgress.setProgress(obj.getFloat("environmentRating") / 100);
        staffProgress.setProgress(obj.getFloat("staffRating") / 100);

        if(availability.getText().equals("Available"))
            bookButton.setDisable(false);
        else
            bookButton.setDisable(true);
        
    }

    public void setData(PatientController pc, int id, int patId)
    {
        this.pc = pc;
        this.patId = patId;
        this.docId = id;
    }

    public void bookButton(ActionEvent event){
        try {
            this.bookButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/book_apt.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            BookAppointmentController bookAppointmentController = new BookAppointmentController();
            bookAppointmentController.setData(pc, docId, patId);

            loader.setController(bookAppointmentController);
            //-------------------------------------------------------------------------------------------------//
            
            Parent root = loader.load();
            //stage.setUserData(patientInfo);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
