package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

    int appointID;
    PatientController pc;
    int patId;
    String status;
    int docId;

    public void setDoctor(String name, String date, String timing, String rating) {
        doctorName.setText(name);
        this.date.setText(date);
        this.timing.setText(timing);
        ratingAmount.setText(rating);
        double ratingPercentage = Double.parseDouble(rating) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);
    }

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
        ratingAmount.setText(jsonObject.getFloat("rating") + "");
        double ratingPercentage = jsonObject.getFloat("rating") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

    public void setData(PatientController pc, int patId) 
    {
        this.pc = pc;
        this.patId = patId;
    }

    public void viewButton(ActionEvent event) 
    {
        System.out.println("View Button Clicked");

        try {
            this.viewBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            if(!status.equals("Booked"))
            {
                loader.setLocation((new URL("file:src/main/resources/com/example/app_detail 2.fxml")));
                CompletedAppointmentController completedAppointmentController = new CompletedAppointmentController();

                completedAppointmentController.setData(pc, patId, appointID, docId);
                loader.setController(completedAppointmentController);
            }
            else if(status.equals("Booked"))
            {
                loader.setLocation((new URL("file:src/main/resources/com/example/app_detail.fxml")));
                PendingAppointmentController pendingAppointmentController = new PendingAppointmentController();

                pendingAppointmentController.setData(pc, patId, appointID, docId);
                loader.setController(pendingAppointmentController);
            }
            
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
}
