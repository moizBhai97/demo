package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ConsultationControllerDoctor  implements Initializable
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
    private Label amount;

    @FXML
    private Label timing;

    @FXML
    private Button viewBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Hyperlink verify;

    DoctorController dc;
    int patId;
    int appID;
    int docId;
    String patGender;

    private AnchorPane rootPane;
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
            verify.setDisable(true);
            verify.setText("Verified");
            verify.setStyle("-fx-opacity: 1.0; -fx-font-fill: #2854c3;");
        }

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + " Rs");

        patId = obj.getInt("patId");
        patName.setText(obj.getJSONObject("patient").getString("name"));

        String dobString = obj.getJSONObject("patient").getString("DOB");

        LocalDate dob = LocalDate.parse(dobString);
        int age = LocalDate.now().getYear() - dob.getYear();

        patAge.setText(age + "");
        patNum.setText(obj.getJSONObject("patient").getString("phoneNumber"));
        patGender = obj.getJSONObject("patient").getString("gender");

    }
    
    public void setData(DoctorController dc, int appID, int docId, AnchorPane rootPane)
    {
        this.docId = docId;
        this.dc = dc;
        this.appID = appID;
        this.rootPane = rootPane;
    }

    public void viewButton(ActionEvent event) 
    {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/patient_details.fxml")));
            
            PatientDetailsController patientDetailsController = new PatientDetailsController();

            patientDetailsController.setData(dc, docId, patId,rootPane);
            loader.setController(patientDetailsController);
                   
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);
            DoctorMainController.addHeaderTitle("Patient Details");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reportButton(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/report.fxml")));


            ReportController reportController = new ReportController();
            reportController.setData(dc, patId, docId, patName.getText(), patGender,rootPane );
            loader.setController(reportController);

         
            
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);
            DoctorMainController.addHeaderTitle("Report Patient");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void verifyButton(ActionEvent event)
    {
        try
        {
            dc.verifyPayment(docId, appID);

            verify.setDisable(true);
            verify.setText("Verified");
            verify.setStyle("-fx-opacity: 1.0; -fx-font-fill: #2854c3;");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
