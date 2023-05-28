package com.example.UIController;

import java.net.URL;

import org.json.JSONArray;

import com.example.BackEnd.PatientController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ManageAppointmentController implements Initializable {
    @FXML
    ScrollPane completedAppointmentsScrollPane;

    @FXML
    ScrollPane pendingAppointmentsScrollPane;

    private GridPane completedAppointmentsGridPane;
    private GridPane pendingAppointmentsGridPane;

    private PatientController patientController;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        patientController = new PatientController();

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
                        (new URL("file:src/main/resources/com/example/PendingAppointmentDoctorCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    PendingAppointmentDoctorCard pendingAppointmentDoctorCard = fxmlLoader.getController();
                    pendingAppointmentDoctorCard.setCard(jsonArray.getJSONObject(i).toString());
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
        String result = "[{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"2.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"}]";

        return result;
    }

    public String getCompletedAppointments() {
        // Dummy Data for JSON array
        String result = "[{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"1.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"},{\"doctorName\":\"Dr. John Doe\",\"date\":\"12/12/2020\",\"timing\":\"12:00 PM\",\"rating\":\"4.5\"}]";

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
                        (new URL("file:src/main/resources/com/example/CompletedAppointmentDoctorCard.fxml")));
                try {
                    Pane pane = fxmlLoader.load();
                    CompletedAppointmentDoctorCard completedAppointmentDoctorCard = fxmlLoader.getController();
                    completedAppointmentDoctorCard.setCard(jsonArray.getJSONObject(i).toString());
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