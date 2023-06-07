package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DummyController implements Initializable{

    int patId;
    PatientController pc;

    @FXML
    private Label patientId;

    @FXML
    private Button details;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        patientId.setText(Integer.toString(patId));


    }

    public void setData(PatientController pc, int id)
    {
        this.pc = pc;
        patId = id;
        System.out.println(patId);

    }

    public void detailsButton(ActionEvent event){
        System.out.println("Details Button pressed");

        try {
            this.details.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctor_details.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            DoctorDetailsController dc = new DoctorDetailsController();

            loader.setController(dc);
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
