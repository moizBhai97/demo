package com.example.UIController;

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

     public void setCard(String result){

        JSONObject  jsonObject = new JSONObject(result);
        appointID = jsonObject.getInt("appID");
        doctorName.setText(jsonObject.getString("doctorName"));
        date.setText(jsonObject.getString("date"));
        timing.setText(jsonObject.getString("timing"));
        ratingAmount.setText(jsonObject.getString("rating"));
        double ratingPercentage = Double.parseDouble(jsonObject.getString("rating")) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

}
