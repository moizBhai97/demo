package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

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
    private TextField usernameTextField;

    //DummyController dummyController;

    SearchDoctorController searchDoctorController;
    PatientController patientController = new PatientController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        return;
    }

    public void loginButton(ActionEvent event){
        // System.out.println("Login Button pressed");

        // JSONObject loginInfo = new JSONObject();
        // loginInfo.put("username", this.usernameTextField.getText());
        // loginInfo.put("password", this.passwordTextField.getText());

        // int patId = Integer.parseInt(patientController.login(loginInfo.toString()));
        // System.out.println(patId);

        // try {
        //     this.loginButton.getScene().getWindow().hide();

        //     FXMLLoader loader = new FXMLLoader();
        //     loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors.fxml")));
            
        //     searchDoctorController = new SearchDoctorController();
            
        //     searchDoctorController.setData(patientController, patId);
        //     loader.setController(searchDoctorController);
            
        //     Parent root = loader.load();
        //     //stage.setUserData(patientInfo);
        //     Scene scene = new Scene(root);
        //     Stage stage = new Stage();
        //     stage.setScene(scene);
        //     stage.show();
            
        // } catch (IOException e) {
        //     System.err.println(String.format("Error: %s", e.getMessage()));
        // }







                     // pass data between screens
        try {
            this.loginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/dummyScreen.fxml")));

            DummyController dummyController = new DummyController();
            
            int dummyPatId = 1;
            dummyController.setData(patientController, dummyPatId);
            loader.setController(dummyController);
            
            Parent root = loader.load();
            //stage.setUserData(patientInfo);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
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
