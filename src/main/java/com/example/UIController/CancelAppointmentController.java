package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class CancelAppointmentController implements Initializable {
    @FXML
    private ToggleGroup radios;

    private AnchorPane prevPane;
    @FXML
    private TextArea reason;

    @FXML
    private Button cancelButton;

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

    PatientController pc;
    int patId;
    int appID;
    String screenInfo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        JSONObject obj = new JSONObject(screenInfo);

        name.setText(obj.getJSONObject("doctor").getString("name"));
        specialization.setText(obj.getJSONObject("doctor").getString("specialization"));
        patients.setText(obj.getJSONObject("doctor").getString("patients"));
        experience.setText(obj.getJSONObject("doctor").getString("experience"));
        rating.setText(String.format("%.1f", obj.getJSONObject("doctor").getFloat("rating")));

        reason.setWrapText(true);
        reason.setDisable(true);
        reason.setStyle("-fx-opacity: 1.0; -fx-background-color: #f4f4f4;");
        radios.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                String buttonText = selectedRadioButton.getText();
                reason.setDisable(!buttonText.equals("Other"));  // Enable/disable the text area based on selection
                reason.setStyle(buttonText.equals("Other") ? "-fx-opacity: 1.0;" : "-fx-opacity: 0.5;");  // Adjust opacity based on selection
            }
        });
    }

    public void setData(PatientController pc, int patId, int appID, AnchorPane prevPane, String info) {
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
        this.prevPane = prevPane;
        this.screenInfo = info;
    }

    public void cancelButton(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();

        if(selectedRadioButton == null)
        {
            System.out.println("Please select a reason");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Reason Selected!");
            alert.setContentText("Please select a reason to reschedule your appointment.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == javafx.scene.control.ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return;
        }
        
        String info = selectedRadioButton.getText();

        if (info.equals("Other")) {

            if (reason.getText().isEmpty()) {
                System.out.println("Please enter a reason");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Reason Entered!");
                alert.setContentText("Please enter a reason in the text area.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == javafx.scene.control.ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                return;
            }

            info = reason.getText();
        }

        JSONObject obj = new JSONObject();

        obj.put("reason", info);

        pc.cancelAppointment(obj.toString(), patId, appID);

        try {
            // this.cancelButton.getScene().getWindow().hide();
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/manageAppointment.fxml")));

            ManageAppointmentController manageAppointmentController = new ManageAppointmentController();
            manageAppointmentController.setData(pc, patId);
            loader.setController(manageAppointmentController);

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane parent = (AnchorPane) prevPane.getParent();
            if (parent != null) {
                parent.getChildren().clear();
            }
            parent.getChildren().add(pane);
          
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void backBtnPressed(ActionEvent event) {
        prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane) prevPane.getParent();
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size() - 1);
    }
}
