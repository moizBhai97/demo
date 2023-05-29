package com.example.UIController;

import com.example.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import com.example.BackEnd.PatientController;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class BookAppointmentController implements Initializable {

    PatientController patientController;
    int docId;
    int patId;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    Pane confirm_pane;

    String dateUnformatted;

    @FXML
    Label date;

    @FXML
    DatePicker datePicker;

    @FXML
    Pane cancel_pane;

    @FXML
    void cancel(ActionEvent event) {
        try {
            if (cancel_pane.isVisible()) {
                cancel_pane.setVisible(false);
            } else {
                cancel_pane.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void confirm(ActionEvent event) {
        try {
            if (confirm_pane.isVisible()) {
                confirm_pane.setVisible(false);
            } else {
                confirm_pane.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setData(PatientController patientController, int Id, int patId) {
        this.patientController = patientController;
        this.docId = Id;
        this.patId = patId;
    }

    // initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.getEditor().setDisable(true);
datePicker.getEditor().setOpacity(1);
        refresh();
        return;
    }

    public void refresh() {
        try {
            // get data from database

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void datePickerPressed(ActionEvent event) {

        String dateUnformatted = (datePicker.getValue().toString());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

        System.out.println(patientController.getSchedule(docId, dateConvert.toString(), 1));

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        date.setText(formattedDate);
    }

}