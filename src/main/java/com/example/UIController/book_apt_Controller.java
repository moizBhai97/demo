package com.example.UIController;

import com.example.App;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class book_apt_Controller {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML 
    Pane confirm_pane;

    @FXML
    Pane cancel_pane;
    @FXML
    void cancel(ActionEvent event){
        try{
            if(cancel_pane.isVisible()){
                cancel_pane.setVisible(false);
            }else{
                cancel_pane.setVisible(true);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void confirm(ActionEvent event){
        try{
            if(confirm_pane.isVisible()){
                confirm_pane.setVisible(false);
            }else{
                confirm_pane.setVisible(true);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    



    
}