package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AppointmentControllerDoctor  implements Initializable
{
    @FXML
    private Label name;
    
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

    @FXML
    private Button viewBtn;

    @FXML
    private Button reportBtn;

    DoctorController dc;
    int patId;
    int appID;
    int docId;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        String info = "";//dc.getAppointment(patId, appID);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getJSONObject("doctor").getString("name"));

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
    
    public void setData(DoctorController dc, int patId, int appID, int docId)
    {
        this.docId = docId;
        this.dc = dc;
        this.patId = patId;
        this.appID = appID;
    }

    public void viewButton(ActionEvent event) 
    {
        System.out.println("View Button Clicked");

        try {
            this.viewBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/report.fxml")));
            CompletedAppointmentController completedAppointmentController = new CompletedAppointmentController();

            //completedAppointmentController.setData(dc, patId, appointID, docId);
            loader.setController(completedAppointmentController);
            
            //-------------------------------------------------------------------------------------------------//
            //-------------------------------------------------------------------------------------------------//
            
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reportButton(ActionEvent event)
    {
        try
        {
            this.reportBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/report.fxml")));


            ReportController reportController = new ReportController();
            reportController.setData(dc, patId, docId);
            loader.setController(reportController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
