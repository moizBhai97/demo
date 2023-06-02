package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private Button viewBtn;

    @FXML
    private Button reportBtn;

    DoctorController dc;
    int patId;
    int appID;
    int docId;
    String patGender;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        String info = dc.getAppointment(docId, appID);

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

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + "");

        patId = obj.getInt("patId");
        patName.setText(obj.getJSONObject("patient").getString("name"));

        String dobString = obj.getJSONObject("patient").getString("DOB");

        LocalDate dob = LocalDate.parse(dobString);
        int age = LocalDate.now().getYear() - dob.getYear();

        patAge.setText(age + "");
        patNum.setText(obj.getJSONObject("patient").getString("phoneNumber"));
        patGender = obj.getJSONObject("patient").getString("gender");

    }
    
    public void setData(DoctorController dc, int appID, int docId)
    {
        this.docId = docId;
        this.dc = dc;
        this.appID = appID;
    }

    public void viewButton(ActionEvent event) 
    {
        System.out.println("View Button Clicked");

        try {
            this.viewBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/patient_details.fxml")));
            
            PatientDetailsController patientDetailsController = new PatientDetailsController();

            patientDetailsController.setData(dc, docId, patId);
            loader.setController(patientDetailsController);
            
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
            reportController.setData(dc, patId, docId, patName.getText(), patGender);
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
