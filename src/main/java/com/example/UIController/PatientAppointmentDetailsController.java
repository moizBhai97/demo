package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PatientAppointmentDetailsController  implements Initializable
{
    @FXML
    private Label docName;
    
    @FXML
    private Label date;

    @FXML
    private Label patName;

    @FXML
    private Label patAge;

    @FXML
    private Label patNum;

    @FXML
    private Label status;

    @FXML
    private Label amount;

    @FXML
    private Label timing;

    DoctorController dc;
    int patId;
    int appID;
    int docId;

    private AnchorPane rootPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        String info = dc.getPatientAppointment(patId, appID);

        JSONObject obj = new JSONObject(info);

        docName.setText(obj.getString("doctor"));

        LocalTime startTime = LocalTime.parse(obj.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);
        date.setText(obj.getString("date"));

        if(obj.getJSONObject("payment").getBoolean("status"))
        {
            status.setText("Paid");
        }
        else
        {
            status.setText("UnPaid");
        }

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + " Rs");

        patId = obj.getInt("patId");
        patName.setText(obj.getJSONObject("patient").getString("name"));

        String dobString = obj.getJSONObject("patient").getString("DOB");

        LocalDate dob = LocalDate.parse(dobString);
        int age = LocalDate.now().getYear() - dob.getYear();

        patAge.setText(age + "");
        patNum.setText(obj.getJSONObject("patient").getString("phoneNumber"));

    }
    
    public void setData(DoctorController dc, int appID, int docId, int patId,AnchorPane rootPane)
    {
        this.docId = docId;
        this.dc = dc;
        this.appID = appID;
        this.patId = patId;
        this.rootPane = rootPane;
    }
    
    public void backBtnPressed(ActionEvent event)
    {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)rootPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
        DoctorMainController.popHeaderTitle();  
    }
}
