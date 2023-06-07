package com.example.UIController;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddCertificationController{

    @FXML
    private DatePicker DOBdatePicker;

    @FXML
    private DatePicker DOBdatePicker1;

    @FXML
    private Button btn_approval;

    @FXML
    private Button btn_cross;

    @FXML
    private Button btn_gallery;

    @FXML
    private TextField tf_cert;

    @FXML
    private TextField tf_expire;

    @FXML
    private TextField tf_issue;

    @FXML
    private DatePicker dp_issue;

    @FXML
    private DatePicker dp_expire;

    DoctorController doctorController;
    JSONArray certificates;
    int docId;

    void setData(int docId, JSONArray certificates, DoctorController doctorController) {

        this.docId = docId;
        this.certificates = certificates;
        this.doctorController = doctorController;
    }

    @FXML
    void addCertification(ActionEvent event) throws Exception{

        try{
            if(this.tf_cert.getText().isEmpty() || this.tf_cert.getText().isBlank()|| this.dp_issue.getValue() == null || this.dp_expire.getValue() == null){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
                return;
            }
            if(this.tf_cert.getText().length() > 255){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Maximum length of certification name is 20 characters");
                alert.showAndWait();
                return;
            }
            if(this.dp_issue.getValue().isAfter(this.dp_expire.getValue()) || this.dp_issue.getValue().isEqual(this.dp_expire.getValue()) || this.dp_issue.getValue().isAfter(LocalDate.now())){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter valid dates");
                alert.showAndWait();
                return;
            }

            JSONObject json = new JSONObject();
            json.put("name", this.tf_cert.getText());
            json.put("issueDate", this.dp_issue.getValue().toString());
            json.put("expiryDate", this.dp_expire.getValue().toString());
            json.put("approvedStatus", "Pending");
            
            System.out.println(json.toString());
            
            doctorController.addCertification(json.toString(), docId);
            certificates.put(json);
            System.out.println(certificates.toString());
            
            this.btn_approval.getScene().getWindow().hide();
        }
        catch(Exception e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Certification");
            alert.setContentText("The Certification has already been added");
            alert.showAndWait();

            throw e;
        }
    }

    @FXML
    void close(ActionEvent event) {

        this.btn_approval.getScene().getWindow().hide();
    }

}
