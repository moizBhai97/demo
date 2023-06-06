package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PendingConsultationPatientCard {

    @FXML
    private Button viewBtn;

    @FXML
    private Pane card1;

    @FXML
    private Label date;

    @FXML
    private Label patName;

    @FXML
    private Label timing;

    @FXML
    private Label Status;

    int appointID;
    DoctorController dc;
    private AnchorPane rootPane;
    int docId;

    public void setDoctor(String name, String date, String timing) {
        patName.setText(name);
        this.date.setText(date);
        this.timing.setText(timing);

    }

    public void setCard(String result){
        JSONObject  jsonObject = new JSONObject(result);
        patName.setText(jsonObject.getString("name"));
        date.setText(jsonObject.getString("date"));
        appointID = jsonObject.getInt("appId");
        LocalTime startTime = LocalTime.parse(jsonObject.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);
        Status.setText(jsonObject.getString("status"));
    }

    public void setData(DoctorController dc, int docId,AnchorPane rootPane) {
        this.dc = dc;
        this.docId = docId;
        this.rootPane = rootPane;
    }
   

    public void viewButton(ActionEvent event) 
    {
        System.out.println("View Button Clicked");

        try {
           // this.viewBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/consultationDetails.fxml")));
            
            ConsultationControllerDoctor appointmentControllerDoctor = new ConsultationControllerDoctor();

            appointmentControllerDoctor.setData(dc, appointID, docId,rootPane);
            
            loader.setController(appointmentControllerDoctor);
            
            //-------------------------------------------------------------------------------------------------//
            //-------------------------------------------------------------------------------------------------//
            
            
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);
           DoctorMainController.addHeaderTitle("Consultation Details");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
