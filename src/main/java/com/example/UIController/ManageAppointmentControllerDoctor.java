package com.example.UIController;

import java.net.URL;

import org.json.JSONArray;

import com.example.BackEnd.DoctorController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ManageAppointmentControllerDoctor implements Initializable {
    @FXML
    ScrollPane completedAppointmentsScrollPane;

    @FXML
    ScrollPane pendingAppointmentsScrollPane;

    private GridPane completedAppointmentsGridPane;
    private GridPane pendingAppointmentsGridPane;

    private DoctorController doctorController;
    private int docId;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        completedAppointmentsGridPane = new GridPane();
        completedAppointmentsScrollPane.setContent(completedAppointmentsGridPane);
        completedAppointmentsGridPane.setVgap(10);
        completedAppointmentsGridPane.setHgap(10);
        completedAppointmentsGridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        pendingAppointmentsGridPane = new GridPane();
        pendingAppointmentsScrollPane.setContent(pendingAppointmentsGridPane);
        pendingAppointmentsGridPane.setVgap(10);
        pendingAppointmentsGridPane.setHgap(10);
        pendingAppointmentsGridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

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
            pendingAppointmentsGridPane.getChildren().clear();
            int rowindex = 0;
            int columnindex = 0;

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/PendingAppointmentPatientCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingAppointmentPatientCard pendingAppointmentPatientCard = fxmlLoader.getController();
                    pendingAppointmentPatientCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentPatientCard.setData(doctorController, docId);
                    pendingAppointmentsGridPane.add(pane, columnindex, rowindex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                columnindex++;
                if (columnindex == 2) {
                    columnindex = 0;
                    rowindex++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getPendingAppointments() {
        // Dummy Data for JSON array
        System.out.println("Pending Appointments");
        String result =""; //= doctorController.getAppointList(docId, 1);

        return result;
    }

    public String getCompletedAppointments() {
        // Dummy Data for JSON array
        System.out.println("Completed Appointments");
        String result =""; //= doctorController.getAppointList(docId, 2);

        return result;
    }

    public void fillCompletedAppointments() {
        try {
            String result = getCompletedAppointments();
            JSONArray jsonArray = new JSONArray(result);
            completedAppointmentsGridPane.getChildren().clear();
            int rowindex = 0;
            int columnindex = 0;

            for (int i = 0; i < jsonArray.length(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(
                        (new URL("file:src/main/resources/com/example/PendingAppointmentPatientCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingAppointmentPatientCard pendingAppointmentPatientCard = fxmlLoader.getController();
                    pendingAppointmentPatientCard.setCard(jsonArray.getJSONObject(i).toString());
                    pendingAppointmentPatientCard.setData(doctorController, docId);
                    completedAppointmentsGridPane.add(pane, columnindex, rowindex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                columnindex++;
                if (columnindex == 2) {
                    columnindex = 0;
                    rowindex++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}