package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.Patient;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class UpdatePatientProfileController implements Initializable{

    @FXML
    private Button btn_add1;
    @FXML
    private Button btn_remove1;
    @FXML
    private Button btn_remove2;
    @FXML
    private Button btn_updateProfile;
    @FXML
    private Button button_back;

    @FXML
    private TextField tf_country;
    @FXML
    private TextField tf_dob;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_gender;
    @FXML
    private TextField tf_number;
    @FXML
    private TextField tf_username;

    private String country;
    private String dob;
    private String email;
    private String gender;
    private String number;
    private String username;
    
    int patId = 1;
    PatientController patientController = new PatientController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
        JSONObject data = new JSONObject(patientController.getPatientData(patId));

        tf_username.setText(data.getString("name"));
        tf_email.setText(data.getString("email"));
        tf_dob.setText(data.getString("DOB"));
        tf_country.setText(data.getString("country"));
        tf_number.setText(data.getString("phoneNumber"));
        tf_gender.setText(data.getString("gender"));

        username = tf_username.getText();
        email = tf_email.getText();
        dob = tf_dob.getText();
        country = tf_country.getText();
        number = tf_number.getText();
        gender = tf_gender.getText();

        // patientController.getPatientHistory(patId);

    }

    @FXML
    void updateProfile(ActionEvent event) {

        try{

            boolean isChanged = false;
            if(!country.equals(tf_country.getText()))
            {
                country = tf_country.getText();
                isChanged = true;
            }
            else if(!dob.equals(tf_dob.getText()))
            {
                dob = tf_dob.getText();
                isChanged = true;
            }
            else if(!email.equals(tf_email.getText()))
            {
                email = tf_email.getText();
                isChanged = true;
            }
            else if(!gender.equals(tf_gender.getText()))
            {
                gender = tf_gender.getText();
                isChanged = true;
            }
            else if(!number.equals(tf_number.getText()))
            {
                number = tf_number.getText();
                isChanged = true;
            }
            else if(!username.equals(tf_username.getText()))
            {
                username = tf_username.getText();
                isChanged = true;
            } 
            else if(!isChanged)
            {
                System.out.println("No changes made");
                return;
            }
            
            JSONObject profile = new JSONObject();
            profile.put("username", this.tf_username.getText());
            profile.put("email", this.tf_email.getText());
            profile.put("DOB", this.tf_dob.getText());
            profile.put("country", this.tf_country.getText());
            profile.put("phoneNumber", this.tf_number.getText());
            profile.put("gender", this.tf_gender.getText());

            patientController.updateProfile(patId, profile.toString());
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}
