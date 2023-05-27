package com.example.UIController;

import com.example.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import com.example.BackEnd.PatientController;

public class BookAppointmentController implements Initializable {

    PatientController patientController;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML 
    Pane confirm_pane;

    @FXML
    Pane cancel_pane;
    @FXML
    void cancel(ActionEvent event){
        try{
            if(cancel_pane.isVisible()){
                cancel_pane.setVisible(false);
            }else{
                cancel_pane.setVisible(true);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void confirm(ActionEvent event){
        try{
            if(confirm_pane.isVisible()){
                confirm_pane.setVisible(false);
            }else{
                confirm_pane.setVisible(true);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public void setPatientController(PatientController patientController){
        this.patientController = patientController;
    }
    //initialize
   @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        return;
    }

    public void refresh(){
        try{
          //get data from database

        }catch(Exception e){
            System.out.println(e);
        }
    }


    



    
}