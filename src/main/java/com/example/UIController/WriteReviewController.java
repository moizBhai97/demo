package com.example.UIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;
import org.json.JSONObject;

import com.example.BackEnd.PatientController;

public class WriteReviewController implements Initializable{
    
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
    @FXML
    private Label experience;
    @FXML
    private Label specialization;
    @FXML
    private Label rating;    
    @FXML
    private Label patients;    
    @FXML
    private Label name;


    private AnchorPane prevPane;

    PatientController patientController;
    int docId ;
    int patId ;
    int appId ;
    Scene prev;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        commentBox.setWrapText(true);
        String info = patientController.getAppointment(patId, appId);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getJSONObject("doctor").getString("name"));
        specialization.setText(obj.getJSONObject("doctor").getString("specialization"));
        patients.setText(obj.getJSONObject("doctor").getString("patients"));
        experience.setText(obj.getJSONObject("doctor").getString("experience"));
        rating.setText(String.format("%.1f", obj.getJSONObject("doctor").getFloat("rating")));

    }

    @FXML
    void submitButton(ActionEvent event) {
        try
        {
            boolean hasComment = true;
            if(commentBox == null || commentBox.getText() == "" || commentBox.getText().isBlank())
                hasComment = false;
        
            

            JSONObject review = new JSONObject();
            if(hasComment){review.put("comment", commentBox.getText());}
            //else {review.put("comment", "");}

            review.put("experience", overallRating.getRating());
            
            if(recommend.getSelectedToggle() != null){
                if(((RadioButton)recommend.getSelectedToggle()).getText().equals("Yes"))
                {
                    review.put("recommend", true);
                }
                else
                {
                    review.put("recommend", false);
                }
            }


            //review.put("recommend", ((RadioButton)recommend.getSelectedToggle()).getText());
            review.put("checkupRating", checkupRating.getRating());
            review.put("environmentRating", clinicRating.getRating());
            review.put("staffRating", staffRating.getRating());

            patientController.submitReview(review.toString(), docId, patId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Review Submission");
            alert.setHeaderText("Review Submitted");
            alert.setContentText("Your review has been submitted successfully");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors.fxml")));

            SearchDoctorController controller = new SearchDoctorController();
            controller.setData(patientController, patId);
            loader.setController(controller);

            Parent parent= prevPane.getParent();
            AnchorPane anchorPane = loader.load();

            AnchorPane.setTopAnchor(anchorPane, 0.0);
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            AnchorPane.setRightAnchor(anchorPane, 0.0);


            if(parent!=null){

                ((AnchorPane)parent).getChildren().clear();
                
            }
            ((AnchorPane)parent).getChildren().add(anchorPane);
            SearchDoctorController.clearHeaderTitles();
            SearchDoctorController.addHeaderTitle("Search Doctors");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setData(PatientController patientController, int docId, int patId, int appId, AnchorPane prevPane, Scene prev) {
        this.patientController = patientController;
        this.docId = docId;
        this.patId = patId;
        this.appId = appId;
        this.prevPane = prevPane;
        this.prev = prev;
    }

    public void backBtnPressed(ActionEvent event)
    {
        prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)prevPane.getParent(); 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);

        SearchDoctorController.removeTopTitle();
    }
}
