package com.example.UIController;

import java.lang.String;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class UpdatePatientProfile2Controller {

    @FXML
    private Button addButton;

    @FXML
    private Label descriptionError;

    @FXML
    private TextField descriptionTextBox;

    @FXML
    private Pane pane_add_rec;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private Label typeError;

    @FXML
    private String type_1;

    @FXML
    private String type_2;

    @FXML
    private String type_3;

    @FXML
    private String type_4;

    @FXML
    private String type_5;

    PatientController patientController = new PatientController();
    int patId;
    JSONArray history;

    public void setData(int patId, JSONArray history)
    {
        this.patId = patId;
        this.history = history;
        System.out.println("Patient ID: " + this.patId);
    }
    
    @FXML
    void addRecord(ActionEvent event) {
        
        try{

            String type;
            if(typeComboBox.getValue() != null)
            type = typeComboBox.getValue().toString();
            else
            type = null;
            
            String description = descriptionTextBox.getText();
            
            typeError.setVisible(false);
            descriptionError.setVisible(false);
            
            boolean flag = false;
            if(type == null)
            {
                typeError.setText("*(Select a type)");
                typeError.setVisible(true);
                flag = true;
            }
            
            if(description == null || description.length() == 0)
            {
                descriptionError.setText("*(Enter a description)");
                descriptionError.setVisible(true);
                flag = true;
            }
            
            //SET RESTRICTION
            if(description.length() > 100)
            {
                descriptionError.setText("*(max characters: )");
                descriptionError.setVisible(true);
                flag = true;
            }
            
            if (flag) {return;}
            
            JSONObject record = new JSONObject();
            record.put("type", type);
            record.put("description", description);
            
            patientController.addIllness(patId, record.toString());
            history.put(record);
            System.out.println(history.toString());

            this.addButton.getScene().getWindow().hide();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}