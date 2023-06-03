package com.example.UIController;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class SingleReviewController {

    @FXML
    private HBox hb;

    @FXML
    private Label date;

    @FXML
    private Label text;

    @FXML
    private Label name;

    @FXML
    private Label ratingAmount;

    @FXML
    private ImageView ratingStar;

    @FXML
    private ImageView starOutline;

    int appointID;
    PatientController pc;
    int patId;
    String status;
    int docId;

    public void setCard(String result){
        JSONObject  jsonObject = new JSONObject(result);

        name.setText(jsonObject.getString("name"));
        text.setText(jsonObject.getString("comment"));
        ratingAmount.setText(jsonObject.getFloat("experience") + "");
        double ratingPercentage = jsonObject.getFloat("experience") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

    public void setData(PatientController pc, int patId, int docId) 
    {
        this.docId = docId;
        this.pc = pc;
        this.patId = patId;

        
    }
}
