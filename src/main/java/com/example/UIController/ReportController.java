package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ReportController  implements Initializable
{
    @FXML
    private ToggleGroup radios;

    @FXML
    private TextArea reason;

    @FXML
    private Button reportBtn;

    @FXML
    private Label patName;

    @FXML
    private Label patGender;

    DoctorController dc;
    int patId;
    int docId;
    String gender;
    String name;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        patName.setText(name);
        patGender.setText(gender);
        reason.setWrapText(true);
    }
    
    public void setData(DoctorController dc, int patId, int docId, String patName, String patGender)
    {
        this.dc = dc;
        this.patId = patId;
        this.docId = docId;
        this.gender = patGender;
        this.name = patName;
    }

    public void reportButton(ActionEvent event) 
    {
        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();
        String info = selectedRadioButton.getText();

        if(info.equals("Other"))
        {
            info = reason.getText();
        }

        JSONObject obj = new JSONObject();

        obj.put("reason", reason);

        dc.newComplaint(patId, obj.toString(), docId);

        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
