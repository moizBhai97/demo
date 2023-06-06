package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ResourceBundle;

//import javax.print.Doc;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartController implements Initializable{

    LoginController loginController;
    PatientController patientController;
    DoctorController doctorController;

    @FXML
    private Button doctorButton;

    @FXML
    private Button patientButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

        return;
    }

    public void patientButton(ActionEvent event){
        System.out.println("Patient Button pressed");

        try {
           // this.patientButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/login.fxml")));

            loginController = new LoginController();
            patientController = new PatientController();
            loginController.setData(doctorController, patientController, true);    // dc is null;

            loader.setController(loginController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) patientButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doctorButton(ActionEvent event){
        System.out.println("Doctor Button pressed");

        try {
            //this.doctorButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/login.fxml")));
            
            loginController = new LoginController();
            doctorController = new DoctorController();
            loginController.setData(doctorController, patientController, false);    // pc is null;

            loader.setController(loginController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) doctorButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
