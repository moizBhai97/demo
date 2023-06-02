package com.example.UIController;

import java.net.URL;

import org.json.JSONArray;

import com.example.BackEnd.PatientController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ManageReviewController implements Initializable {
    @FXML
    ScrollPane reviewsScrollPane;

    @FXML
    private GridPane reviewsGridPane;

    private PatientController patientController;
    private int patId;
    private int docId;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        reviewsGridPane.getChildren().clear();

        reviewsGridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        fillReviews();
    }

    public void setData(PatientController pc, int patId, int docId) 
    {
        System.out.println("Rev: " + docId);
        this.docId = docId;
        this.patientController = pc;
        this.patId = patId;
    }

    public void fillReviews() {
        try {
            String result = getReviews();
            JSONArray jsonArray = new JSONArray(result);
            int rowindex = 0;

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/singleReview.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    SingleReviewController singleReviewController = fxmlLoader.getController();
                    singleReviewController .setCard(jsonArray.getJSONObject(i).toString());
                    System.out.println("Rev: " + docId);
                    singleReviewController .setData(patientController, patId, docId);
                    reviewsGridPane.add(pane, 0, rowindex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                rowindex++;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getReviews() 
    {
        String result = patientController.getReviews(docId);

        return result;
    }

}