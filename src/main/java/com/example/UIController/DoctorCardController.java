package com.example.UIController;
import java.net.URL;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DoctorCardController {

    private SearchDoctorController parentController;
    private PatientController patientController;

    int doctorId;
    int patientId;
    
    @FXML
    private Pane card1;

    @FXML
    private Button card1_book_apt_btn;

    @FXML
    private Label docName;

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
    private ImageView ratingStar;

    @FXML
    private Label specialization;

    public void setParentController(SearchDoctorController parentController) {
        this.parentController = parentController;
    }

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }   

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDoctor(DoctorTemp doctor) {
        docName.setText(doctor.name);
      //  card1_speciality_label.setText(doctor.speciality);
    }
    public void setDoctor(String name, String specialization, String price, String rating) {
        docName.setText(name);
        this.specialization.setText(specialization);
        fee_amount.setText(String.valueOf(price));
            String formattedRating = String.format("%.2f", Double.parseDouble(rating) );
        card1_satisfied_label.setText(formattedRating);

        double ratingPercentage = Double.parseDouble(rating) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);


    }

    public void setDoctor(String result){
        JSONObject jsonObject = new JSONObject(result);
        docName.setText(jsonObject.getString("name"));
        specialization.setText(jsonObject.getString("specialization"));
        fee_amount.setText(jsonObject.getString("price"));
        card1_satisfied_label.setText(jsonObject.getString("rating"));
        double ratingPercentage = Double.parseDouble(jsonObject.getString("rating")) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage, ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

        doctorId = jsonObject.getInt("id");
    }


    //button action
    public void book(ActionEvent event) {
        System.out.println("book button clicked");
        try {
            this.card1_book_apt_btn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctor_details.fxml")));
            
            DoctorDetailsController doctorDetailsController= new DoctorDetailsController();
            
            doctorDetailsController.setData(patientController,doctorId, patientId);
            loader.setController(doctorDetailsController);
            
            Parent root = loader.load();
            //stage.setUserData(patientInfo);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
