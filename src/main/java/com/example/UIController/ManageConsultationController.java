package com.example.UIController;

import java.net.URL;

import org.json.JSONArray;

import com.example.BackEnd.DoctorController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ManageConsultationController implements Initializable {
    @FXML
    ScrollPane completedAppointmentsScrollPane;

    @FXML
    ScrollPane pendingAppointmentsScrollPane;

    @FXML
    AnchorPane rootPane;
    private FlowPane completedAppointmentsFlowPane;
    private FlowPane pendingAppointmentsFlowPane;

    private DoctorController doctorController;
    private int docId;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        DoctorMainController.addHeaderTitle("Manage Consultations");
        completedAppointmentsFlowPane = new FlowPane();
        completedAppointmentsScrollPane.setContent(completedAppointmentsFlowPane);
        completedAppointmentsFlowPane.setVgap(10);
        completedAppointmentsFlowPane.setHgap(10);
        completedAppointmentsFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        pendingAppointmentsFlowPane = new FlowPane();
        pendingAppointmentsScrollPane.setContent(pendingAppointmentsFlowPane);
        pendingAppointmentsFlowPane.setVgap(10);
        pendingAppointmentsFlowPane.setHgap(10);
        pendingAppointmentsFlowPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        fillPendingAppointments();
        fillCompletedAppointments();
    }

    public void setData(DoctorController dc, int docId)
    {
        this.doctorController = dc;
        this.docId = docId;
    }

    public void fillPendingAppointments() {
        try {
            String result = getPendingAppointments();
            JSONArray jsonArray = new JSONArray(result);
            pendingAppointmentsFlowPane.getChildren().clear();
         

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/PendingConsultationPatientCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingConsultationPatientCard pendingAppointmentPatientCard = fxmlLoader.getController();
                    pendingAppointmentPatientCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentPatientCard.setData(doctorController, docId,rootPane);
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
        // Dummy Data for JSON array
        System.out.println("Pending Appointments");
        String result = doctorController.getAppointList(docId, 1);

        return result;
    }

    public String getCompletedAppointments() {
        // Dummy Data for JSON array
        System.out.println("Completed Appointments");
        String result = doctorController.getAppointList(docId, 2);

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
                        (new URL("file:src/main/resources/com/example/PendingConsultationPatientCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingConsultationPatientCard pendingAppointmentPatientCard = fxmlLoader.getController();
                    pendingAppointmentPatientCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentPatientCard.setData(doctorController, docId,rootPane);
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