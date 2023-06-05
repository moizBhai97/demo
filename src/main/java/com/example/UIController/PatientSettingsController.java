package com.example.UIController;

import java.net.URL;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class PatientSettingsController {

    private PatientController patientController;
    private int patId;

    @FXML
    private AnchorPane rootPane;

    public void setData(PatientController patientController, int patId) {

        this.patientController = patientController;
        this.patId = patId;
    }

    @FXML
    public void updateProfilePressed(ActionEvent event)
    {
        try{
             FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/UpdatePatientProfile.fxml")));


            UpdatePatientProfileController updateDoctorProfileController = new UpdatePatientProfileController();
            updateDoctorProfileController.setData(patientController, patId, rootPane);
            loader.setController(updateDoctorProfileController);

         
            
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);

           SearchDoctorController.addHeaderTitle("Update Profile");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public void backBtnPressed(ActionEvent event)
    {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)rootPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
        SearchDoctorController.removeTopTitle();
    }

}
