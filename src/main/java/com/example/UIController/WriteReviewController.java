package com.example.UIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    private AnchorPane prevPane;

    PatientController patientController;
    int docId ;
    int patId ;
    Scene prev;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        commentBox.setWrapText(true);
    }

    @FXML
    void submitButton(ActionEvent event) {
        
        JSONObject review = new JSONObject();
        review.put("comment", commentBox.getText());
        review.put("experience", overallRating.getRating());
        review.put("recommend", ((RadioButton)recommend.getSelectedToggle()).getText());
        review.put("checkupRating", checkupRating.getRating());
        review.put("environmentRating", clinicRating.getRating());
        review.put("staffRating", staffRating.getRating());

        patientController.submitReview(review.toString(), docId, patId);

        try
        {
            this.submitButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors.fxml")));

            SearchDoctorController controller = new SearchDoctorController();
            controller.setData(patientController, patId);
            loader.setController(controller);

<<<<<<< HEAD
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            Stage prevWin = (Stage) prev.getWindow();

            stage.setWidth(prevWin.getWidth());
            stage.setHeight(prevWin.getHeight());
            stage.setX(prevWin.getX());
            stage.setY(prevWin.getY());

            stage.setMinWidth(825);
            stage.setMinHeight(680);

            stage.setScene(scene);
            stage.show();
=======
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

>>>>>>> 3d720c1f0fe74f6a5ee13655d34bbd2a4a98cbe9
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setData(PatientController patientController, int docId, int patId, AnchorPane prevPane, Scene prev) {
        this.patientController = patientController;
        this.docId = docId;
        this.patId = patId;
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
