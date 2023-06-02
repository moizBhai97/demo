package com.example.UIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.Rating;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.BackEnd.PatientController;

public class WriteReviewController{

    @FXML
    private Rating checkupRating;

    @FXML
    private Rating clinicRating;

    @FXML
    private TextArea commentBox;

    @FXML
    private Rating overallRating;

    @FXML
    private ToggleGroup recommend;

    @FXML
    private Rating staffRating;

    @FXML
    private Button submitButton;

    private AnchorPane prevPane;

    PatientController patientController;
    int docId ;
    int patId ;

    @FXML
    void submitButton(ActionEvent event) {
        
        /*
        "comment": "{{comment}}",
        "experience": "{{experience}}",
        "recommend": "{{recommend}}",
        "checkupRating": "{{checkupRating}}",
        "environmentRating": "{{environmentRating}}",
        "staffRating": "{{staffRating}}" 
         */
        JSONObject review = new JSONObject();
        review.put("comment", commentBox.getText());
        review.put("experience", overallRating.getRating());
        review.put("recommend", ((RadioButton)recommend.getSelectedToggle()).getText());
        review.put("checkupRating", checkupRating.getRating());
        review.put("environmentRating", clinicRating.getRating());
        review.put("staffRating", staffRating.getRating());

        patientController.submitReview(review.toString(), docId, patId);
        
    }
    
    public void setData(PatientController patientController, int docId, int patId,AnchorPane prevPane) {
        this.patientController = patientController;
        this.docId = docId;
        this.patId = patId;
        this.prevPane = prevPane;
    }

    
    public void backBtnPressed(ActionEvent event){
        prevPane.setVisible(true);
    AnchorPane mainParentPane = (AnchorPane)prevPane.getParent();
    //remove last 
    mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
    }

}
