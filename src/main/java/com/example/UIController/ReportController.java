package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ReportController implements Initializable {
    @FXML
    private ToggleGroup radios;

    @FXML
    private TextArea reason;

    @FXML
    private Button reportBtn;

    @FXML
    private Label patName;

    @FXML
    private Label patGender;

    DoctorController dc;
    int patId;
    int docId;
    String gender;
    String name;

    private AnchorPane rootPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        patName.setText(name);
        patGender.setText(gender);
        reason.setWrapText(true);
    }

    public void setData(DoctorController dc, int patId, int docId, String patName, String patGender,AnchorPane rootPane) {
        this.dc = dc;
        this.patId = patId;
        this.docId = docId;
        this.gender = patGender;
        this.name = patName;
        this.rootPane = rootPane;
    }

    public void reportButton(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();
        String info = selectedRadioButton.getText();

        if (info.equals("Other")) {
            info = reason.getText();
        }

        JSONObject obj = new JSONObject();

        obj.put("reason", reason);

        dc.newComplaint(patId, obj.toString(), docId);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/ManageAppointment.fxml")));
            ManageConsultationController controller = new ManageConsultationController();
            controller.setData(dc, docId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            AnchorPane mainParentPane = (AnchorPane) rootPane.getParent();
            mainParentPane.getChildren().clear();
            mainParentPane.getChildren().add(root);
            DoctorMainController.clearHeaderTitleStack();
            DoctorMainController.addHeaderTitle("Manage Consultation");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void backBtnPressed(ActionEvent event) {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane) rootPane.getParent();
        // remove last
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size() - 1);
        DoctorMainController.popHeaderTitle();
    }
}
