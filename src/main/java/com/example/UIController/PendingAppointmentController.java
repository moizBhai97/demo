package com.example.UIController;

import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PendingAppointmentController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label specialization;

    @FXML
    private Label patients;

    @FXML
    private Label experience;

    @FXML
    private Label rating;

    @FXML
    private Label date;

    @FXML
    private Label patName;

    @FXML
    private Label patAge;

    @FXML
    private Label patNum;

    @FXML
    private Label status;

    @FXML
    private Label amount;

    @FXML
    private Label timing;

    @FXML
    private Button rschBtn;

    @FXML
    private Button cancelBtn;

    PatientController pc;
    int patId;
    int appID;
    int docId;

    private AnchorPane prevPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String info = pc.getAppointment(patId, appID);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getJSONObject("doctor").getString("name"));
        specialization.setText(obj.getJSONObject("doctor").getString("specialization"));
        patients.setText(obj.getJSONObject("doctor").getString("patients"));
        experience.setText(obj.getJSONObject("doctor").getString("experience"));
        rating.setText(obj.getJSONObject("doctor").getString("rating"));
        LocalTime startTime = LocalTime.parse(obj.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);
        date.setText(obj.getString("date"));

        if (obj.getJSONObject("payment").getBoolean("status")) {
            status.setText("Paid");
        } else {
            status.setText("UnPaid");
        }

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + "");

    }

    public void setData(PatientController pc, int patId, int appID, int docId, AnchorPane prevPane) {
        this.docId = docId;
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
        this.prevPane = prevPane;
    }

    public void cancelButton(ActionEvent event) {
        System.out.println("Resch Button Clicked");

        try {
            // this.cancelBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/cancel.fxml")));

            // -------------------------------------------------------------------------------------------------//
            CancelAppointmentController cancelAppointmentController = new CancelAppointmentController();

            cancelAppointmentController.setData(pc, patId, appID, prevPane);
            loader.setController(cancelAppointmentController);
            // -------------------------------------------------------------------------------------------------//

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // rootPane.setVisible(false);
            ((AnchorPane) prevPane.getParent()).getChildren().add(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backBtnPressed(ActionEvent event) {
        prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane) prevPane.getParent();
        // remove last
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size() - 1);

    }

    public void rschButton(ActionEvent event) {
        System.out.println("Resch Button Clicked");

        try {
           // this.rschBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/resch.fxml")));

            // -------------------------------------------------------------------------------------------------//
            ReschAppointmentController reschAppointmentController = new ReschAppointmentController();

            System.out.println(docId);

            reschAppointmentController.setData(pc, patId, appID, docId, prevPane);
            loader.setController(reschAppointmentController);
            // -------------------------------------------------------------------------------------------------//

           AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // rootPane.setVisible(false);
            ((AnchorPane) prevPane.getParent()).getChildren().add(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
