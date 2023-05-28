package com.example.UIController;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PendingAppointmentDoctorCard {

    @FXML
    private Button cancelBtn;

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
    public void setDoctor(String name, String date, String timing, String rating) {
        doctorName.setText(name);
        this.date.setText(date);
        this.timing.setText(timing);
        ratingAmount.setText(rating);
          double ratingPercentage = Double.parseDouble(rating) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);
    }

    public void setCard(String reeult){
        JSONObject  jsonObject = new JSONObject(reeult);
        doctorName.setText(jsonObject.getString("doctorName"));
        date.setText(jsonObject.getString("date"));
        appointID = jsonObject.getInt("appID");
        timing.setText(jsonObject.getString("timing"));
        ratingAmount.setText(jsonObject.getString("rating"));
        double ratingPercentage = Double.parseDouble(jsonObject.getString("rating")) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }
}
