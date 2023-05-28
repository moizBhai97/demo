package com.example.UIController;

import com.example.App;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    Button button_big;
    @FXML
    Pane big_pane;
    @FXML
    Button filter_btn;
    @FXML
    Pane filter_Pane;
    @FXML
    ImageView filter_img;
    public void filter_toggle(ActionEvent event){
        try{
            if(filter_Pane.isVisible()){
                filter_Pane.setVisible(false);
                filter_img.setVisible(false);
            }else{
                filter_Pane.setVisible(true);
                filter_img.setVisible(true);
                
            }
         
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static ObservableList<String> getComboBoxItems() {
        return FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");
    }
}
