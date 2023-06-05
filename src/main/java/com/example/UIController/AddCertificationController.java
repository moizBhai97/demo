package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    DoctorController doctorController = new DoctorController();
    JSONArray certificates;
    int docId;

    void setData(int docId, JSONArray certificates) {

        this.docId = docId;
        this.certificates = certificates;
    }

    @FXML
    void addCertification(ActionEvent event) {

        try{

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

            e.printStackTrace();
        }
    }

    @FXML
    void close(ActionEvent event) {

        this.btn_approval.getScene().getWindow().hide();
    }

}
