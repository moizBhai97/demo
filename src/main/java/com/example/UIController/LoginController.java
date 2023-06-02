package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable{
    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;

    //DummyController dummyController;

    boolean isPatient = true;
    PatientController patientController = new PatientController();
    SearchDoctorController searchDoctorController;

    DoctorController doctorController = new DoctorController();

    DoctorDetailsControllerDoctor doctorDetailsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        if(isPatient){
        emailTextField.setText("ali.ahmed@example.com");
        passwordTextField.setText("password123");
        }
        else if(!isPatient){
            emailTextField.setText("dr.asim@example.com");
            passwordTextField.setText("drpassword123");
        }
        // /loginButton.fire();
        return;
    }

    public void setData(DoctorController dc, PatientController pc, boolean isPatient){
        this.doctorController = dc;
        this.patientController = pc;
        this.isPatient = isPatient;
    }

    public void loginButton(ActionEvent event){
        System.out.println("Login Button pressed");

        JSONObject loginInfo = new JSONObject();
        loginInfo.put("email", this.emailTextField.getText());
        loginInfo.put("password", this.passwordTextField.getText());

        System.out.println(loginInfo.toString());

        if(isPatient){
            System.out.println("Patient login");

            int patId = Integer.parseInt(patientController.login(loginInfo.toString()));
            System.out.println(patId);

            try {
            this.loginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors - Copy.fxml")));
            
            searchDoctorController = new SearchDoctorController();
            searchDoctorController.setData(patientController, patId);
 
            loader.setController(searchDoctorController);
            
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException e) {
                System.err.println(String.format("Error: %s", e.getMessage()));
            }
        }
        else if (!isPatient){

            int docId = Integer.parseInt(doctorController.login(loginInfo.toString()));
            System.out.println(docId);

            try {
            this.loginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctor_detailsDoctor.fxml")));
            
            doctorDetailsController = new DoctorDetailsControllerDoctor();
            
            doctorDetailsController.setData(doctorController, docId);
            loader.setController(doctorDetailsController);
            
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException e) {
                System.err.println(String.format("Error: %s", e.getMessage()));
                e.printStackTrace();
            }

        }
        
    }

    public void signupHyperlink(ActionEvent event){
        System.out.println("Signup hyperlink pressed");

        try {
            this.loginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/signup.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
