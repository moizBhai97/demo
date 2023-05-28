package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.BackEnd.PatientController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DummyController implements Initializable{

    int patId;
    PatientController pc;

    @FXML
    private Label patientId;

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

    
}
