package com.example.UIController;

import com.example.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.example.BackEnd.PatientController;

public class BookAppointmentController implements Initializable {

    PatientController patientController;
    int docId;
    int patId;
    String fee;
    String docName;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    private String dateFormatted;
    private String selectedTime;

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
    Button bookBtn;

    @FXML
    Label feeLabel;

    @FXML
    Label doctorName;

    @FXML
    ToggleGroup time;

    @FXML
    TextArea problem_text;

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

    public void setData(PatientController patientController, int Id, int patId, String fee, String docName) {
        this.fee = fee;
        this.docName = docName;
        this.patientController = patientController;
        this.docId = Id;
        this.patId = patId;
    }

    // initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        feeLabel.setText(fee);
        doctorName.setText(docName);

        String dateUnformatted = LocalDate.now().toString();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

        dateFormatted = dateConvert.toString();

        System.out.println(patientController.getSchedule(docId, dateConvert.toString(), 1));

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        date.setText(formattedDate);

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

        dateFormatted = dateConvert.toString();

        System.out.println(patientController.getSchedule(docId, dateConvert.toString(), 1));

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        date.setText(formattedDate);
    }

    public void timeSelected()
    {
        ToggleButton selectedToggleButton = (ToggleButton) time.getSelectedToggle();
            if (selectedToggleButton != null) 
            {
                selectedTime = selectedToggleButton.getText();
                System.out.println("Selected ToggleButton text: " + selectedTime);
            }
            else
            {
                System.out.println("No ToggleButton selected.");
            } 
    }

    public void bookButton(ActionEvent event) 
    {
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("date", dateFormatted);
            obj.put("time", selectedTime);
            obj.put("problem", problem_text.getText());
            obj.put("docId", docId);

            patientController.bookSlot(obj.toString(), patId);

            this.bookBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/payment.fxml")));
            //-------------------------------------------------------------------------------------------------//
            PaymentController paymentController = new PaymentController();
            paymentController.setData(patientController, patId, fee, docName, obj.toString());
            //-------------------------------------------------------------------------------------------------//
            loader.setController(paymentController);
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