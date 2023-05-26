package com.example.UIController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;



public class SearchDoctorController implements Initializable{
    // @FXML
    // GridPane results_grid;

    @FXML
    ScrollPane results_scrollpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        return;
    }
  
    public void refresh(){

        List<DoctorTemp> doctors=DoctorTemp.getDummyDoctor();
        GridPane results_grid = new GridPane();
        results_grid.setHgap(10);
        results_grid.setVgap(10);
        results_grid.getChildren().clear();
        results_scrollpane.getChildrenUnmodifiable().clear();
        //set padding
        results_grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        //set transparent background
        results_grid.setStyle("-fx-background-color: transparent;");
        int rowindex=0;
        int columnindex=0;
        for(int i=0;i<10;i++){
            try{
                FXMLLoader loader = new FXMLLoader();
                
                loader.setLocation((new URL("file:src/main/resources/com/example/DoctorCard.fxml")));
                Pane doctorCard = loader.load();
                DoctorCardController controller = loader.getController();
                controller.setDoctor(doctors.get(i));
                //controller.setParentController(this);
                results_grid.add(doctorCard, rowindex, columnindex);
                rowindex++;
                if(rowindex==2){
                    rowindex=0;
                    columnindex++;
                }

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        results_scrollpane.setContent(results_grid);

        System.out.println("refreshed");



        
    }
    

    
}