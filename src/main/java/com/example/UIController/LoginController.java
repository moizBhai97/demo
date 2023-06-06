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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Group signupGroup;


    // DummyController dummyController;

    boolean isPatient = true;
    PatientController patientController = new PatientController();
    SearchDoctorController searchDoctorController;

    DoctorController doctorController = new DoctorController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        if (isPatient) {
            emailTextField.setText("ali.ahmed@example.com");
            passwordTextField.setText("password123");
        } else if (!isPatient) {
            emailTextField.setText("dr.asim@example.com");
            passwordTextField.setText("drpassword123");

            signupGroup.setVisible(false);
        }
        // /loginButton.fire();
        return;
    }

    public void setData(DoctorController dc, PatientController pc, boolean isPatient) {
        this.doctorController = dc;
        this.patientController = pc;
        this.isPatient = isPatient;
    }

    public void loginButton(ActionEvent event) {
        System.out.println("Login Button pressed");

        if(this.emailTextField.getText().isEmpty() || this.passwordTextField.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error: Empty Fields");
            alert.setContentText("Please fill all fields");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            return;
        }
        if (!this.emailTextField.getText().matches("[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error: Invalid Email");
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }
        if (!this.passwordTextField.getText().matches("[a-zA-Z0-9._]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error: Invalid Password");
            alert.setContentText("Password should not contain special characters other than '.' and '_'");
            alert.showAndWait();
            return;
        }
        JSONObject loginInfo = new JSONObject();
        loginInfo.put("email", this.emailTextField.getText());
        loginInfo.put("password", this.passwordTextField.getText());

        String email = this.emailTextField.getText();

        if (!email.matches("[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }

        System.out.println(loginInfo.toString());

        if(isPatient){
            try {
                System.out.println("Patient login");

                int patId = Integer.parseInt(patientController.login(loginInfo.toString()));
                System.out.println(patId);
             //   this.loginButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors - Copy.fxml")));
                
                searchDoctorController = new SearchDoctorController();
                searchDoctorController.setData(patientController, patId);
    
                loader.setController(searchDoctorController);
                
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            
            } catch (Throwable e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("User Not Found");
                alert.setHeaderText("Error: Email and Password do not match");
                alert.setContentText("Please enter a valid Email and Password");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
                System.err.println(String.format("Error: %s", e.getMessage()));
            }
        } else if (!isPatient) {

            try {
                int docId = Integer.parseInt(doctorController.login(loginInfo.toString()));
                System.out.println(docId);
                
               // this.loginButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((new URL("file:src/main/resources/com/example/doctorMain.fxml")));

                DoctorMainController doctorMainController = new DoctorMainController();

                doctorMainController.setData(doctorController, docId);
                loader.setController(doctorMainController);

                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: User Not Found");
                alert.setContentText("Please enter a valid Email and Password");
                alert.showAndWait();
                System.err.println(String.format("Error: %s", e.getMessage()));
                e.printStackTrace();
            }

        }

    }

    @FXML
    public void signupHyperlink(ActionEvent event){
        System.out.println("Signup hyperlink pressed");

        try {
           // this.loginButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/signup.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
