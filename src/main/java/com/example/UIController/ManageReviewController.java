package com.example.UIController;

import java.net.URL;

import org.json.JSONArray;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class ManageReviewController implements Initializable {
    @FXML
    ScrollPane reviewsScrollPane;

    private PatientController patientController;
    private int patId;
    private int docId;
    private AnchorPane prevPane;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        fillReviews();
    }

    public void setData(PatientController pc, int patId, int docId, AnchorPane prevPane)
    {
        System.out.println("Rev: " + docId);
        this.docId = docId;
        this.patientController = pc;
        this.patId = patId;
        this.prevPane = prevPane;
    }

    public void fillReviews() {
        try {

            HBox hbox = new HBox();

            GridPane reviewsGridPane = new GridPane();

            reviewsGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            reviewsGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);

            reviewsGridPane.setPrefWidth(618);
            reviewsGridPane.setPrefHeight(234);

            reviewsGridPane.setMaxWidth(Double.MAX_VALUE);
            reviewsGridPane.setMaxHeight(Double.MAX_VALUE);

            reviewsGridPane.setVgap(10);

            reviewsGridPane.setHgap(30);

            hbox.getChildren().add(reviewsGridPane);

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            reviewsGridPane.getColumnConstraints().add(columnConstraints);

            reviewsScrollPane.setContent(null);

            reviewsGridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

            String result = getReviews();
            JSONArray jsonArray = new JSONArray(result);
            int rowindex = 0;

            for (int i = 0; i < jsonArray.length(); i++) {

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(Priority.ALWAYS); // Set the vertical growth policy for each row
                reviewsGridPane.getRowConstraints().add(rowConstraints);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/singleReview.fxml")));
                try {

                    Pane pane = fxmlLoader.load();
                    SingleReviewController singleReviewController = fxmlLoader.getController();
                    singleReviewController .setCard(jsonArray.getJSONObject(i).toString());
                    singleReviewController .setData(patientController, patId, docId);

                    reviewsGridPane.add(pane, 0, rowindex);

                    rowindex++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            HBox.setHgrow(reviewsGridPane, Priority.ALWAYS);

            reviewsScrollPane.setContent(hbox);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReviews() 
    {
        String result = patientController.getReviews(docId);

        return result;
    }

    
    public void backBtnPressed(ActionEvent event){
        //  prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)prevPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);

        SearchDoctorController.removeTopTitle();    
    }

}