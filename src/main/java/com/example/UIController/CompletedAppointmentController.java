package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CompletedAppointmentController  implements Initializable
{
    @FXML
    private Label name;
    
    @FXML
    private Label specialization;

    @FXML
    private Label patients;

    @FXML
    private Label experience;

    @FXML
    private Label rating;

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

    PatientController pc;
    int patId;
    int appID;
    int docId;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        String info = pc.getAppointment(patId, appID);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getJSONObject("doctor").getString("name"));
        specialization.setText(obj.getJSONObject("doctor").getString("specialization"));
        patients.setText(obj.getJSONObject("doctor").getString("patients"));
        experience.setText(obj.getJSONObject("doctor").getString("experience"));
        rating.setText(obj.getJSONObject("doctor").getString("rating"));
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

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + "");

    }
    
    public void setData(PatientController pc, int patId, int appID, int docId)
    {
        this.docId = docId;
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
    }

    public void writeButton(ActionEvent event)
    {
        try
        {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/UI/writeReview.fxml"));
            // Parent root = loader.load();
            // WriteReviewController wrc = loader.getController();
            // wrc.setData(pc, patId, docId);
            // Stage stage = new Stage();
            // stage.setScene(new Scene(root));
            // stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
