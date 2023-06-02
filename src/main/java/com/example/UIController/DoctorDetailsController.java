package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DoctorDetailsController implements Initializable{
    
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

    String screenInfo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
         
        System.out.println(docId);

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

        if(availability.getText().equals("Available"))
            bookButton.setDisable(false);
        else
            bookButton.setDisable(true);
        
    }

    public void setData(PatientController pc, int id, int patId, AnchorPane rootPane)
    {

        this.pc = pc;
        this.patId = patId;
        this.docId = id;
        this.rootPane = rootPane;
    }

    public void bookButton(ActionEvent event){
        try {
      //      this.bookButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/book_apt.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            BookAppointmentController bookAppointmentController = new BookAppointmentController();
            bookAppointmentController.setData(pc, docId, patId,screenInfo,rootPane);

            loader.setController(bookAppointmentController);
            //-------------------------------------------------------------------------------------------------//
            
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // rootPane.setVisible(false);
           // ((AnchorPane) rootPane.getParent()).getChildren().clear();
           AnchorPane parent= ((AnchorPane) rootPane.getParent());
            parent.getChildren().add(pane);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backBtnPressed(ActionEvent event)
    {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)rootPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);

        // mainParentPane.getChildren().add(rootPane);
            //   Node node = (Node)event.getSource();
            //     while (node != null && !(node instanceof AnchorPane)) {
            //         node = node.getParent();
            //     }
            //     ((AnchorPane)node.getParent()).getChildren().removeAll();
            //     ((AnchorPane)node.getParent()).getChildren().setAll(rootPane);
    }
    
    public void reviewsButton(ActionEvent event)
    {
        try {
        //    this.reviewsBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/manageReview.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            ManageReviewController reviewsController = new ManageReviewController();

            System.out.println("DocId: " + docId);
            reviewsController.setData(pc, patId, docId, rootPane);

            loader.setController(reviewsController);
            //-------------------------------------------------------------------------------------------------//
            
             AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // rootPane.setVisible(false);
AnchorPane parent= ((AnchorPane) rootPane.getParent());
            parent.getChildren().add(pane);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void certificateButton()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/certificate.fxml")));
            
            //-------------------------------------------------------------------------------------------------//
            CertificateController certificateController = new CertificateController();

            certificateController.setData(pc, null, docId);

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
            parentPane.setEffect(blur);
            
            stage.setOnHidden(event -> {
                parentPane.setEffect(null); // Remove the blur effect
            });

            parentPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
