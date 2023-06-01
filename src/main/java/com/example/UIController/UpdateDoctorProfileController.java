package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateDoctorProfileController {

    @FXML
    private Button btn_add1;

    @FXML
    private Button button_back;

    @FXML
    private ComboBox<?> genderComboBox;

    @FXML
    private String gender_1;

    @FXML
    private String gender_2;

    @FXML
    private TextField tf_country;

    @FXML
    private TextField tf_dob;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_number;

    @FXML
    private TextField tf_username;

    UpdateDoctorProfile2Controller updateDoctorProfile2Controller = new UpdateDoctorProfile2Controller();
    DoctorController doctorController = new DoctorController();
    int docId /* = 101 */;

    void setData(int docId) {

        this.docId = docId;
    }

    @FXML
    void addCertification(ActionEvent event) {

        System.out.println("Add Certification Button pressed");

        try {
            //this.btn_add1.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/updateProfile3Copy.fxml")));

            updateDoctorProfile2Controller = new UpdateDoctorProfile2Controller();
            updateDoctorProfile2Controller.setData(docId);
            loader.setController(updateDoctorProfile2Controller);

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
