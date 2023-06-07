package com.example.UIController;

import java.net.URL;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class DoctorCardController {

    private PatientController patientController;

    int doctorId;
    int patientId;

    private AnchorPane prevPane;

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

    String result;

    

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDoctor(DoctorTemp doctor) {
        docName.setText(doctor.name);
    }

    public void setPrevPane(AnchorPane prevPane) {
        this.prevPane = prevPane;
    }

    public void setDoctor(String name, String specialization, String price, String rating, String Location) {
        docName.setText(name);
        this.specialization.setText(specialization);
        fee_amount.setText(String.valueOf(price));
        card1_location_label.setText(Location);
        String formattedRating = String.format("%.2f", Double.parseDouble(rating));
        card1_satisfied_label.setText(formattedRating);
        double ratingPercentage = Double.parseDouble(rating) / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage,
                ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

    }

    public void setDoctor(String result) {
        JSONObject jsonObject = new JSONObject(result);

        docName.setText(jsonObject.getString("name"));
        JSONObject innerObject = jsonObject.getJSONObject("details");
        innerObject.put("name", jsonObject.getString("name") );
        this.result= innerObject.toString();
        specialization.setText(innerObject.getString("specialization"));
        fee_amount.setText(String.format("%.1f", innerObject.getFloat("fee")));
        card1_location_label.setText(innerObject.getString("location"));
        card1_satisfied_label.setText(String.format("%.1f", innerObject.getFloat("rating")));
        double ratingPercentage = innerObject.getFloat("rating") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getBoundsInLocal().getWidth() * ratingPercentage,
                ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

        doctorId = jsonObject.getInt("id");
    }

    // button action
    public void viewProfileBtn(ActionEvent event) {
        System.out.println("book button clicked");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctor_details.fxml")));

            DoctorDetailsController doctorDetailsController = new DoctorDetailsController();

            doctorDetailsController.setData(patientController, doctorId, patientId, prevPane);
            loader.setController(doctorDetailsController);

            AnchorPane anchorPane = (AnchorPane) prevPane.getParent();

            AnchorPane childPane = loader.load();
            anchorPane.getChildren().add(childPane);
            AnchorPane.setTopAnchor(childPane, 0.0);
            AnchorPane.setBottomAnchor(childPane, 0.0);
            AnchorPane.setLeftAnchor(childPane, 0.0);
            AnchorPane.setRightAnchor(childPane, 0.0);

            SearchDoctorController.addHeaderTitle("Doctor Details");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bookAppointmentPressed(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/book_apt.fxml")));

            BookAppointmentController bookAptController = new BookAppointmentController();
            bookAptController.setData(patientController, doctorId, patientId,result, prevPane);
            loader.setController(bookAptController);

            AnchorPane anchorPane = (AnchorPane) prevPane.getParent();
            
            AnchorPane childPane = loader.load();
            anchorPane.getChildren().add(childPane);
            AnchorPane.setTopAnchor(childPane, 0.0);
            AnchorPane.setBottomAnchor(childPane,0.0);
            AnchorPane.setLeftAnchor(childPane, 0.0);
            AnchorPane.setRightAnchor(childPane, 0.0);

            SearchDoctorController.addHeaderTitle("Book Appointment");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
