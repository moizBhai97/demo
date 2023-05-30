package com.example.UIController;

import java.time.LocalTime;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CompletedAppointmentDoctorCard {

    @FXML
    private Pane card1;

    @FXML
    private Label date;

    @FXML
    private Label doctorName;

    @FXML
    private Hyperlink leaveReviewLink;

    @FXML
    private Label ratingAmount;

    @FXML
    private ImageView ratingStar;

    @FXML
    private ImageView starOutline;

    @FXML
    private Label timing;

    int appointID;
    int patId;
    int docId;

    public void setCard(String result){

        JSONObject  jsonObject = new JSONObject(result);
        appointID = jsonObject.getInt("appId");
        doctorName.setText(jsonObject.getString("name"));
        date.setText(jsonObject.getString("date"));
        LocalTime startTime = LocalTime.parse(jsonObject.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);
        ratingAmount.setText(jsonObject.getFloat("rating") + "");
        double ratingPercentage = jsonObject.getFloat("rating") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

}
