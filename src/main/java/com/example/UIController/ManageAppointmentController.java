package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;

import com.example.BackEnd.PatientController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ManageAppointmentController implements Initializable {
    @FXML
    ScrollPane completedAppointmentsScrollPane;

    @FXML
    ScrollPane pendingAppointmentsScrollPane;

    @FXML
    AnchorPane rootPane;

    private FlowPane completedAppointmentsFlowPane;
    private FlowPane pendingAppointmentsFlowPane;

    private PatientController patientController;
    private int patId;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        completedAppointmentsFlowPane = new FlowPane();
        completedAppointmentsScrollPane.setContent(completedAppointmentsFlowPane);
        completedAppointmentsFlowPane.setVgap(10);
        completedAppointmentsFlowPane.setHgap(10);
        completedAppointmentsFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        pendingAppointmentsFlowPane = new FlowPane();
         pendingAppointmentsFlowPane.setVgap(10);
        pendingAppointmentsFlowPane.setHgap(10);
        pendingAppointmentsFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        pendingAppointmentsScrollPane.getChildrenUnmodifiable().clear();
        pendingAppointmentsScrollPane.setContent(pendingAppointmentsFlowPane);

        fillPendingAppointments();
        fillCompletedAppointments();
    }

    public void setData(PatientController pc, int patId) 
    {
        this.patientController = pc;
        this.patId = patId;
    }

    public void fillPendingAppointments() {
        try {
            String result = getPendingAppointments();
            JSONArray jsonArray = new JSONArray(result);
            pendingAppointmentsFlowPane.getChildren().clear();
           

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/PendingAppointmentDoctorCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingAppointmentDoctorCard pendingAppointmentDoctorCard = fxmlLoader.getController();
                    pendingAppointmentDoctorCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentDoctorCard.setData(patientController, patId,rootPane);
                    pendingAppointmentsFlowPane.getChildren().add(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              
            }
            pendingAppointmentsScrollPane.setContent(pendingAppointmentsFlowPane);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getPendingAppointments() {
        String result = patientController.getAppointList(patId, 1);

        return result;
    }

    public String getCompletedAppointments() {
        
        String result = patientController.getAppointList(patId, 2);

        return result;
    }

    public void fillCompletedAppointments() {
        try {
            String result = getCompletedAppointments();
            JSONArray jsonArray = new JSONArray(result);
            completedAppointmentsFlowPane.getChildren().clear();
            

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/PendingAppointmentDoctorCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingAppointmentDoctorCard pendingAppointmentDoctorCard = fxmlLoader.getController();
                    pendingAppointmentDoctorCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentDoctorCard.setData(patientController, patId,rootPane);
                    completedAppointmentsFlowPane.getChildren().add(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            completedAppointmentsScrollPane.setContent(completedAppointmentsFlowPane);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}