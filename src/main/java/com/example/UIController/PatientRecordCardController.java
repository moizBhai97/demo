package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class PatientRecordCardController implements Initializable{

    @FXML
    private AnchorPane aPane;
    @FXML
    private Label description;
    @FXML
    private Label index;
    @FXML
    private Button removeButton;
    @FXML
    private Label type;

    int patId;
    JSONArray history;
    String info;
    int sid;
    int idTag;

    PatientController patientController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        JSONObject obj = new JSONObject(info);
        this.index.setText(idTag + ". ");
        this.type.setText(obj.getString("type"));
        this.description.setText(obj.getString("description"));
    }

    public void setIndex(int index)
    {
        this.index.setText(index + ". ");
    }
    
    public void setData(int patId, int index, String info, JSONArray history, PatientController patientController)
    {
        this.patId = patId;
        this.history = history;
        this.info = info;        
        //sid = obj.getInt("sid");
        idTag = index;
        this.patientController = patientController;
    }

    @FXML
    void removeIllness(ActionEvent event) {

        try{

            FlowPane flowPane = (FlowPane)aPane.getParent();
            int index = flowPane.getChildren().indexOf(aPane);
            System.out.println(index);
            System.out.println(idTag);
            
            flowPane.getChildren().remove(aPane);

            System.out.println(history.toString());
            history.remove(index);
            patientController.removeIllness(patId, info);
            System.out.println(history.toString());
            
            for(int i = index; i < flowPane.getChildren().size(); i++)
            {
                AnchorPane aPane2 = (AnchorPane)flowPane.getChildren().get(i);
                HBox hBox = (HBox)aPane2.getChildren().get(1);
                Label label = (Label)hBox.getChildren().get(0);
                label.setText(i + 1 + ". ");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
