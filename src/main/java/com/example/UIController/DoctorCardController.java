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
    private Pane card1;

    @FXML
    private Button card1_book_apt_btn;

    @FXML
    private Label card1_doc_label;

    @FXML
    private Label card1_exp_label;

    @FXML
    private Pane card1_hbox_exp_pane;

    @FXML
    private Pane card1_hbox_satisfied_pane;

    @FXML
    private Label card1_location_label;

    @FXML
    private Label card1_satisfied_label;

    @FXML
    private Button card1_view_prof_btn;

    @FXML
    private Label fee_amount;

    @FXML
    private Label specialization;

    public void setParentController(SearchDoctorController parentController) {
        this.parentController = parentController;
    }

    public void setDoctor(DoctorTemp doctor) {
        card1_doc_label.setText(doctor.name);
      //  card1_speciality_label.setText(doctor.speciality);
    }
    public void setDoctor(String name, String specialization, double price, String rating) {
        card1_doc_label.setText(name);
        this.specialization.setText(specialization);
        fee_amount.setText(String.valueOf(price));
        card1_satisfied_label.setText(rating);

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
