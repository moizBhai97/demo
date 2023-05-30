package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

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
import javafx.stage.Stage;

public class CancelAppointmentController  implements Initializable
{
    @FXML
    private ToggleGroup radios;

    @FXML
    private TextArea reason;

    @FXML
    private Button cancelButton;

    PatientController pc;
    int patId;
    int appID;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        reason.setWrapText(true);
    }
    
    public void setData(PatientController pc, int patId, int appID)
    {
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
    }

    public void cancelButton(ActionEvent event) 
    {
        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();
        String info = selectedRadioButton.getText();

        if(info.equals("Other"))
        {
            info = reason.getText();
        }

        JSONObject obj = new JSONObject();

        obj.put("reason", reason);

        pc.cancelAppointment(obj.toString(), patId, appID);

        try
        {
            this.cancelButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/manageAppointment.fxml")));
            //-------------------------------------------------------------------------------------------------//
            ManageAppointmentController manageAppointmentController = new ManageAppointmentController();
            manageAppointmentController.setData(pc, patId);
            //-------------------------------------------------------------------------------------------------//
            loader.setController(manageAppointmentController);
            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
