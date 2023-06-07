package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PendingAppointmentDoctorCard {

    @FXML
    private Button viewBtn;

    @FXML
    private Pane card1;

    @FXML
    private Label date;

    @FXML
    private Label doctorName;

    @FXML
    private Label ratingAmount;

    @FXML
    private ImageView ratingStar;

    @FXML
    private ImageView starOutline;

    @FXML
    private Label timing;

    @FXML
    private Label Status;

    @FXML
    private AnchorPane rootPane;

    int appointID;
    PatientController pc;
    int patId;
    String status;
    int docId;

    public void setCard(String result){
        JSONObject  jsonObject = new JSONObject(result);
        doctorName.setText(jsonObject.getString("name"));
        docId = jsonObject.getInt("docId");
        date.setText(jsonObject.getString("date"));
        appointID = jsonObject.getInt("appId");
        LocalTime startTime = LocalTime.parse(jsonObject.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);
        Status.setText(jsonObject.getString("status"));
        status = jsonObject.getString("status");
        ratingAmount.setText(String.format("%.1f", jsonObject.getFloat("rating")));
        double ratingPercentage = jsonObject.getFloat("rating") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

    public void setData(PatientController pc, int patId,AnchorPane rootPane) {
        this.pc = pc;
        this.patId = patId;
        this.rootPane = rootPane;
    }
 

    public void viewButton(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader();
            if(!status.equals("Booked"))
            {
                loader.setLocation((new URL("file:src/main/resources/com/example/app_detail 2.fxml")));
                CompletedAppointmentController completedAppointmentController = new CompletedAppointmentController();

                completedAppointmentController.setData(pc, patId, appointID, docId,rootPane);
                loader.setController(completedAppointmentController);
            }
            else if(status.equals("Booked"))
            {
                loader.setLocation((new URL("file:src/main/resources/com/example/app_detail.fxml")));
                PendingAppointmentController pendingAppointmentController = new PendingAppointmentController();

                pendingAppointmentController.setData(pc, patId, appointID, docId,rootPane);
                loader.setController(pendingAppointmentController);
            }
          
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);

           SearchDoctorController.addHeaderTitle("Appointment Details");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
