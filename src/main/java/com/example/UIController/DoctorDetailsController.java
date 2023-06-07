package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DoctorDetailsController implements Initializable {

    int docId;
    int patId;
    PatientController pc;

    @FXML
    private Label name;

    @FXML
    private Label specialization;

    @FXML
    private Label description;

    @FXML
    private Label location;

    @FXML
    private Label stats;

    @FXML
    private Label patients;

    @FXML
    private Label experience;

    @FXML
    private Label rating;

    @FXML
    private Label services;

    @FXML
    private Label timing;

    @FXML
    private Label fee;

    @FXML
    private Label reviews;

    @FXML
    private Label avgCheckup;

    @FXML
    private Label avgEnv;

    @FXML
    private Label avgStaff;

    @FXML
    private Label availability;

    @FXML
    private ProgressBar checkProgress;

    @FXML
    private ProgressBar envProgress;

    @FXML
    private ProgressBar staffProgress;

    @FXML
    private Button bookButton;

    private AnchorPane rootPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button reviewsBtn;

    @FXML
    private Hyperlink certificateBtn;

    @FXML
    private ImageView ratingStar;

    String screenInfo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        screenInfo = pc.getDocDetails(docId);

        JSONObject obj = new JSONObject(screenInfo);

        name.setText(obj.getString("name"));
        specialization.setText(obj.getString("specialization"));
        description.setText(obj.getString("description"));
        location.setText(obj.getString("location"));
        stats.setText(String.format("%.1f", obj.getFloat("stats")) + "%");
        patients.setText(obj.getInt("patients") + "");
        experience.setText(obj.getInt("experience") + "");
        rating.setText(String.format("%.1f", obj.getFloat("rating")));
        services.setText(obj.getString("services"));
        timing.setText(obj.getString("workingHours"));
        fee.setText(String.format("%.1f", obj.getFloat("fee")));
        availability.setText(obj.getString("availability"));
        reviews.setText(obj.getInt("reviews") + "");
        avgCheckup.setText(String.format("%.1f", obj.getFloat("checkupRating")) + "%");
        avgEnv.setText(String.format("%.1f", obj.getFloat("environmentRating")) + "%");
        avgStaff.setText(String.format("%.1f", obj.getFloat("staffRating")) + "%");

        checkProgress.setProgress(obj.getFloat("checkupRating") / 100);
        envProgress.setProgress(obj.getFloat("environmentRating") / 100);
        staffProgress.setProgress(obj.getFloat("staffRating") / 100);

        double ratingPercentage = obj.getFloat("rating") / 5.0;

        Rectangle clip = new Rectangle(0, 0, ratingStar.getFitWidth() * ratingPercentage,
                ratingStar.getBoundsInLocal().getHeight());
        ratingStar.setClip(clip);

        if (availability.getText().equals("Available"))
            bookButton.setDisable(false);
        else
            bookButton.setDisable(true);

    }

    public void setData(PatientController pc, int id, int patId, AnchorPane rootPane) {

        this.pc = pc;
        this.patId = patId;
        this.docId = id;
        this.rootPane = rootPane;
    }

    public void bookButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/book_apt.fxml")));

            BookAppointmentController bookAppointmentController = new BookAppointmentController();
            bookAppointmentController.setData(pc, docId, patId, screenInfo, rootPane);
            loader.setController(bookAppointmentController);

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

            ((AnchorPane) rootPane.getParent()).getChildren().add(pane);

            SearchDoctorController.addHeaderTitle("Book Appointment");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backBtnPressed(ActionEvent event) {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane) rootPane.getParent();
        // remove last
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size() - 1);
        SearchDoctorController.removeTopTitle();
    }

    public void reviewsButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/manageReview.fxml")));

            ManageReviewController reviewsController = new ManageReviewController();
            reviewsController.setData(pc, patId, docId, rootPane);
            loader.setController(reviewsController);

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, -1.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

            ((AnchorPane) rootPane.getParent()).getChildren().add(pane);

            SearchDoctorController.addHeaderTitle("Reviews");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void certificateButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/certificate.fxml")));

            CertificateController certificateController = new CertificateController();
            certificateController.setData(pc, null, docId);
            loader.setController(certificateController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            BoxBlur blur = new BoxBlur();
            blur.setWidth(10);
            blur.setHeight(10);
            blur.setIterations(3);

            Effect otherEffects = parentPane.getEffect();

            parentPane.setEffect(blur);

            stage.setOnHidden(event -> {
                parentPane.setEffect(null);
                parentPane.setEffect(otherEffects);
            });

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
