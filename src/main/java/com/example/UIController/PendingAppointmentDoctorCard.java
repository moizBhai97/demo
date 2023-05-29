package com.example.UIController;

import java.net.URL;
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
    private Button cancelBtn;

    @FXML
    private Button reschBtn;

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
    private Button rescheduleBtn;

    @FXML
    private ImageView starOutline;

    @FXML
    private Label timing;

    int appointID;
    PatientController pc;
    int patId;
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
        doctorName.setText(jsonObject.getString("doctorName"));
        docId = jsonObject.getInt("docId");
        date.setText(jsonObject.getString("date"));
        appointID = jsonObject.getInt("appID");
        timing.setText(jsonObject.getString("timing"));
        ratingAmount.setText(jsonObject.getString("rating"));
        double ratingPercentage = Double.parseDouble(jsonObject.getString("rating")) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

    public void setData(PatientController pc, int patId) 
    {
        this.pc = pc;
        this.patId = patId;
    }

    public void cancelButton(ActionEvent event) 
    {
        System.out.println("Cancel Button Clicked");

        try {
            this.cancelBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/cancel.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            CancelAppointmentController cancelAppointmentController = new CancelAppointmentController();

            cancelAppointmentController.setData(pc, patId, appointID);
            loader.setController(cancelAppointmentController);
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

    public void reschButton(ActionEvent event) 
    {
        System.out.println("Resch Button Clicked");

        try {
            this.reschBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/resch.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            ReschAppointmentController reschAppointmentController = new ReschAppointmentController();

            reschAppointmentController.setData(pc, patId, appointID, docId);
            loader.setController(reschAppointmentController);
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
