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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Group signupGroup;

    
    @FXML
    private AnchorPane rootPane;

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
        return;
    }

    public void setData(DoctorController dc, PatientController pc, boolean isPatient) {
        this.doctorController = dc;
        this.patientController = pc;
        this.isPatient = isPatient;
    }

    public void loginButton(ActionEvent event) throws Exception{
        try{
            //  this.loginButton.getScene().getWindow().hide();
            
            if (this.emailTextField.getText().isBlank() || this.passwordTextField.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Error: Empty Fields");
                alert.setContentText("Please fill all fields");
                alert.showAndWait();

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
            

            if (isPatient) {
                try {

                    int patId = Integer.parseInt(patientController.login(loginInfo.toString()));
                    // this.loginButton.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors - Copy.fxml")));
                    
                    searchDoctorController = new SearchDoctorController();
                    searchDoctorController.setData(patientController, patId);
                    
                    loader.setController(searchDoctorController);
                    
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)this.loginButton.getScene().getWindow();
                    
                    stage.setScene(scene);
                    
                    stage.setMinWidth(825);
                    stage.setMinHeight(680);
                    
                    stage.show();

                    stage.centerOnScreen();
                    
                } catch (IOException e) {         
                    throw e;
                }
            } else if (!isPatient) {
                
                try {
                    int docId = Integer.parseInt(doctorController.login(loginInfo.toString()));
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation((new URL("file:src/main/resources/com/example/doctorMain.fxml")));
                    
                    DoctorMainController doctorMainController = new DoctorMainController();
                    
                    doctorMainController.setData(doctorController, docId);
                    loader.setController(doctorMainController);
                    
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) this.loginButton.getScene().getWindow();
                    stage.setScene(scene);
                    
                    stage.setMinWidth(825);
                    stage.setMinHeight(680);
                    
                    stage.show();
                    stage.centerOnScreen();
                    
                } catch (IOException e) {
                    
                    throw e;
                }
                
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error: User Not Found");
            alert.setContentText("Please enter a valid Email and Password");
            alert.showAndWait();
            throw e;
        }
    }

    @FXML
    public void signupHyperlink(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/signup.fxml")));
            Parent root = loader.load();

            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            Scene scene = new Scene(root);

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
    public void backBtnPressed(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/start.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(scene);

            stage.show();

            stage.sizeToScene();

            stage.setMinWidth(720);
            stage.setMinHeight(550);

            stage.centerOnScreen();
    }
    catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    public void rootPaneOnMouseClicked(MouseEvent  event) {
        rootPane.requestFocus();
      
    }

}
