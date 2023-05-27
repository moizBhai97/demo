package com.example.UIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DoctorCardController {

    private SearchDoctorController parentController;

    @FXML
    Label card1_doc_label;

    @FXML
    Label card1_speciality_label;

    @FXML
    Label card1_dist_label;

    @FXML
    Button card1_book_button;

    public void setParentController(SearchDoctorController parentController) {
        this.parentController = parentController;
    }

    public void setDoctor(DoctorTemp doctor) {
        card1_doc_label.setText(doctor.name);
      //  card1_speciality_label.setText(doctor.speciality);
        card1_dist_label.setText(Float.toString(doctor.distance));
    }

    //button action
    public void book(ActionEvent event) {
        System.out.println("book button clicked");
        try {
          //print message box of doctor name

          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Appointment");
            alert.setHeaderText("Appointment Booked");
            alert.setContentText("Appointment booked with Dr. " + card1_doc_label.getText());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
