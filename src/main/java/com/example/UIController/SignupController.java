package com.example.UIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignupController implements Initializable {
    @FXML
    private DatePicker DOBdatePicker;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox<?> genderComboBox;
    @FXML
    private ComboBox<?> countryComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button signupButton;

    @FXML
    private AnchorPane rootPane;

    PatientController patientController = new PatientController();
    DoctorController doctorController = new DoctorController();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        return;
    }
    
    public void signupButton(ActionEvent event) throws Exception{
        try{

            if(nameTextField.getText().trim().isBlank() || emailTextField.getText().trim().isBlank() || passwordTextField.getText().trim().isBlank() || phoneNumberTextField.getText().trim().isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Empty fields");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
                return;
            }
            if(!nameTextField.getText().matches("[a-zA-Z._ ]+")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Invalid Name");
                alert.setContentText("Name should only contain alphabets, spaces, '.' and '_'");
                alert.showAndWait();
                return;
            }
            if (!emailTextField.getText().matches("[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Invalid Email");
                alert.setContentText("Please enter a valid email address.");
                alert.showAndWait();
                return;
            }
            if (!passwordTextField.getText().matches("[a-zA-Z0-9._]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Invalid Password");
                alert.setContentText("Password should not contain special characters other than '.' and '_'");
                alert.showAndWait();
                return;
            }
            if (passwordTextField.getText().length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Invalid Password");
                alert.setContentText("Password should be at least 8 characters long");
                alert.showAndWait();
                return;
            }
            if(!passwordTextField.getText().equals(confirmPasswordTextField.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Passwords do not match");
                alert.setContentText("Please enter the same password in both fields");
                alert.showAndWait();
                return;
            }
            if (!phoneNumberTextField.getText().matches("[0-9]+") || phoneNumberTextField.getText().trim().length() != 11) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Invalid Phone Number");
                alert.setContentText("Please enter a valid phone number.");
                alert.showAndWait();
                return;
            }

            JSONObject patient = new JSONObject();
            
            patient.put("name", nameTextField.getText().trim());
            patient.put("email", emailTextField.getText().trim());
            patient.put("password", passwordTextField.getText().trim());
            patient.put("DOB", DOBdatePicker.getValue().toString());
            patient.put("gender", genderComboBox.getValue().toString());
            patient.put("country", countryComboBox.getValue().toString());
            patient.put("phoneNumber", phoneNumberTextField.getText().trim());
            
            patientController.signup(patient.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Account created successfully");
            alert.setContentText("Your account has been created successfully. Please login to continue.");
            alert.showAndWait();

            this.signupButton.getScene().getWindow().hide();
            loadLogin();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Account already exists");
            alert.setContentText("An account has already been made using that email address");
            alert.showAndWait();

            throw e;
        }
    }

    public void loginHyperlink(ActionEvent event) {
        loadLogin();
    }

    public void loadLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/login.fxml")));

            LoginController loginController = new LoginController();
            patientController = new PatientController();
            loginController.setData(doctorController, patientController, true); // dc is null;
            
            loader.setController(loginController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signupButton.getScene().getWindow();

            stage.setScene(scene);

            stage.show();

            stage.sizeToScene();

            stage.setMinWidth(720);
            stage.setMinHeight(550);

            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        @FXML
        public void rootPaneOnMouseClicked(MouseEvent  event) {
            rootPane.requestFocus();
          
        }

    @FXML
    public void genderComboBoxChanged(ActionEvent event) {
        genderComboBox.setStyle(
                " -fx-background-color:transparent; -fx-border-color:transparent;   -fx-effect: innershadow(gaussian, black, 100, 0, 0, 0);");

    }

    @FXML
    public void countryComboBoxChanged(ActionEvent event) {
        countryComboBox.setStyle(
                " -fx-background-color:transparent; -fx-border-color:transparent;   -fx-effect: innershadow(gaussian, black, 100, 0, 0, 0);");
    }
}