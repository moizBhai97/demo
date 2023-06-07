package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.BackEnd.DoctorController;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DoctorMainController implements Initializable {

    @FXML
    private Button appointmentBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private ImageView prof_img;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button settingsBtn;

    @FXML
    public Label headerTitle;

    private DoctorController doctorController;
    int docId;

    private Button selectedDashbordBtn;

    private static ObservableList<Label> headerTitles = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        addHeaderTitle("Home");
        ImageView imageView = (ImageView) homeBtn.getGraphic();
        imageView = (ImageView) homeBtn.getGraphic();
        imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
        homeBtn.setStyle("-fx-text-fill: #2854c3;");

        headerTitles.addListener((ListChangeListener<Label>) change -> {
        if (headerTitles.isEmpty()) {
                if(headerTitle != null)
                headerTitle.setText("");
        } else {
                if (headerTitle != null) {
                    headerTitle.setText(headerTitles.get(headerTitles.size() - 1).getText());
                }
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctorHome.fxml")));
            DoctorHomeController controller = new DoctorHomeController();
            controller.setData(doctorController, docId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    public static void popHeaderTitle() {
        headerTitles.remove(headerTitles.size() - 1);
    }

    public static void addHeaderTitle(String title) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        headerTitles.add(label);

    }

    public void resetDashboardButtons() {
        appointmentBtn.setStyle("-fx-text-fill: #979797");
        appointmentBtn.getGraphic().setEffect(null);
        logoutBtn.setStyle("-fx-text-fill: #979797");
        logoutBtn.getGraphic().setEffect(null);
        settingsBtn.setStyle("-fx-text-fill: #979797");
        settingsBtn.getGraphic().setEffect(null);
        homeBtn.setStyle("-fx-text-fill: #979797");
        homeBtn.getGraphic().setEffect(null);

    }

    @FXML
    public void homeBtnPressed(ActionEvent event) {
        try {
            if (selectedDashbordBtn == homeBtn) {
                return;
            }

            headerTitles.clear();
            addHeaderTitle("Home");

            selectedDashbordBtn = homeBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) homeBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            homeBtn.setStyle("-fx-text-fill: #2854c3;");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/doctorHome.fxml")));
            DoctorHomeController controller = new DoctorHomeController();
            controller.setData(doctorController, docId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearHeaderTitleStack() {
        headerTitles.clear();
    }

    public void appointmentBtnPressed(ActionEvent event) {
        // open manage appointments
        try {
            if (selectedDashbordBtn == appointmentBtn) {
                return;
            }
            headerTitles.clear();
            addHeaderTitle("Manage Consultations");

            selectedDashbordBtn = appointmentBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) appointmentBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            appointmentBtn.setStyle("-fx-text-fill: #2854c3;");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/ManageAppointment.fxml")));
            ManageConsultationController controller = new ManageConsultationController();
            controller.setData(doctorController, docId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void settingsBtnPressed(ActionEvent event) {
        // open settings
        try {
            if (selectedDashbordBtn == settingsBtn) {
                return;
            }
            headerTitles.clear();
            addHeaderTitle("Settings");

            selectedDashbordBtn = settingsBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) settingsBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            settingsBtn.setStyle("-fx-text-fill: #2854c3;");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/settings.fxml")));
            DoctorSettingsController controller = new DoctorSettingsController();
            controller.setData(doctorController, docId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setData(DoctorController doctorController, int docId) {
        this.doctorController = doctorController;
        this.docId = docId;
    }

    public void logoutBtnPressed(ActionEvent event) {

        try {
            if (selectedDashbordBtn == logoutBtn) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(new URL("file:src/main/resources/com/example/start.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            
            stage.setMinWidth(825);
            stage.setMinHeight(480);

            stage.setWidth(825);
            stage.setHeight(480);
            
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}