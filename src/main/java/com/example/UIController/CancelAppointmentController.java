package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class CancelAppointmentController  implements Initializable
{
    @FXML
    private ToggleGroup radios;

    @FXML
    private TextArea reason;

    @FXML
    private Button cancelButton;

    PatientController pc;
    int patId;
    int appID;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        reason.setWrapText(true);
    }
    
    public void setData(PatientController pc, int patId, int appID)
    {
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
    }

    public void cancelButton(ActionEvent event) 
    {
        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();
        String info = selectedRadioButton.getText();

        if(info.equals("Other"))
        {
            info = reason.getText();
        }

        pc.cancelAppointment(info, patId, appID);
    }
}
