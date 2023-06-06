package com.example.UIController;

import java.net.URL;

import com.example.BackEnd.DoctorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class DoctorSettingsController {

  
    private DoctorController doctorController;
    private int docId;
    @FXML
    private AnchorPane rootPane;

    public void setData(DoctorController doctorController, int docId) {
        this.doctorController = doctorController;
        this.docId = docId;
    }

    public void updateProfilePressed(ActionEvent event)
    {
        try{
             FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/UpdateDoctorProfile.fxml")));


            UpdateDoctorProfileController updateDoctorProfileController = new UpdateDoctorProfileController();
            updateDoctorProfileController.setData(doctorController, docId, rootPane);
            loader.setController(updateDoctorProfileController);

         
            
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);

           DoctorMainController.addHeaderTitle("Update Profile");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public void backBtnPressed(ActionEvent event)
    {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)rootPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
        DoctorMainController.popHeaderTitle();
    }

}
