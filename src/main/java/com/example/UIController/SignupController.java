package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ResourceBundle;

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

public class SignupController implements Initializable
{

    @FXML
    private DatePicker DOBdatePicker;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private ComboBox<?> genderComboBox;
    
    @FXML
    private String gender_1;

    @FXML
    private String gender_2;

    @FXML
    private TextField nameTextField;

    @FXML
    private Pane passwordTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        return;
    }

    public void signupButton(ActionEvent event){
        System.out.println("Signup button pressed");
        
        
    }

    public void loginHyperlink(ActionEvent event){
        System.out.println("Login hyperlink pressed");

        try {
            this.signupButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/login.fxml")));
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
