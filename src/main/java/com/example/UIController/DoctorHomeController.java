package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.event.EventTarget;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DoctorHomeController implements Initializable{
    
    int docId;
    DoctorController dc;

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
    private Hyperlink certificateBtn;

    @FXML
    private Button appoints;

    @FXML
    private Pane big_pane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
         
        System.out.println(docId);

        String info = dc.getDocDetails(docId);

        JSONObject obj = new JSONObject(info);

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
        
    }

    public void setData(DoctorController dc, int id)
    {
        this.dc = dc;
        this.docId = id;
    }

    public void certificateButton()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/certificate.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            CertificateController certificateController = new CertificateController();

            certificateController.setData(null, dc, docId);

            loader.setController(certificateController);
            //-------------------------------------------------------------------------------------------------//
            
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
            

            // Apply the blur effect to the scene
            big_pane.setEffect(blur);
            
            stage.setOnHidden(event -> {
                big_pane.setEffect(null); // Remove the blur effect
            });

            big_pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                EventTarget target = event.getTarget();
                if (!root.getBoundsInParent().contains(event.getX(), event.getY())) {
                    stage.close(); // Close the child screen
                }
            });

            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
