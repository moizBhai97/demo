package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdatePatientProfileController implements Initializable{

    @FXML
    private Button add;
    @FXML
    private AnchorPane addPane;
    @FXML
    private Label addIndex;
    @FXML
    private Button btn_remove1;
    @FXML
    private Button btn_remove2;
    @FXML
    private Button btn_updateProfile;
    @FXML
    private Button button_back;
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField tf_country;
    @FXML
    private DatePicker dp_dob;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_gender;
    @FXML
    private TextField tf_number;
    @FXML
    private TextField tf_username;
    @FXML
    private AnchorPane parentPane;

    private String country;
    private String dob;
    private String email;
    private String gender;
    private String number;
    private String username;
    
    JSONArray history;

    int patId;
    PatientController patientController;
    private AnchorPane rootPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
        JSONObject data = new JSONObject(patientController.getPatientData(patId));

        tf_username.setText(data.getString("name"));
        tf_email.setText(data.getString("email"));
        dp_dob.getEditor().setText(data.getString("DOB"));
        tf_country.setText(data.getString("country"));
        tf_number.setText(data.getString("phoneNumber"));
        tf_gender.setText(data.getString("gender"));

        username = tf_username.getText();
        email = tf_email.getText();
        dob = dp_dob.getEditor().getText();
        country = tf_country.getText();
        number = tf_number.getText();
        gender = tf_gender.getText();

        history = new JSONArray(patientController.getPatientHistory(patId));
        
        refresh();
    }

    public void setData(PatientController patientController, int patId,AnchorPane rootPane)
    {
        this.patId = patId;
        this.patientController = patientController;
        this.rootPane = rootPane;
    }
    public void refresh()
    {
        flowPane.getChildren().clear();
        for(int i = 0; i < history.length(); i++)
        {
            //add in table
            JSONObject obj = history.getJSONObject(i);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((new URL("file:src/main/resources/com/example/patientRecordCard.fxml")));
                PatientRecordCardController patientRecordCardController = new PatientRecordCardController();
                patientRecordCardController.setData(patId, i+1, obj.toString(), history, patientController);
                loader.setController(patientRecordCardController);
                AnchorPane aPane = loader.load();
                flowPane.getChildren().add(aPane);
                addIndex.setText(i+2 + ". ");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        flowPane.getChildren().add(addPane);
    }

    @FXML
    public void addIllness(ActionEvent event)
    {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/addRecordPopup.fxml")));

            AddRecordController updatePatientProfile2Controller = new AddRecordController();
            updatePatientProfile2Controller.setData(patId, history, patientController);

            loader.setController(updatePatientProfile2Controller);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            BoxBlur blur = new BoxBlur();
            blur.setWidth(10);
            blur.setHeight(10);
            blur.setIterations(3);

            Effect otherEffects = parentPane.getEffect();

            parentPane.setEffect(blur);

            stage.setOnHidden(e-> {   
                parentPane.setEffect(null);
                parentPane.setEffect(otherEffects); 
                refresh();
            });
            stage.setScene(scene);
            stage.show();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    public void updateProfile(ActionEvent event) {

        try{

            boolean isChanged = false;
            if(!country.equals(tf_country.getText()))
            {
                country = tf_country.getText();
                isChanged = true;
            }
            if(!dob.equals(dp_dob.getEditor().getText()))
            {
                dob = dp_dob.getEditor().getText();
                isChanged = true;
            }
            if(!email.equals(tf_email.getText()))
            {
                email = tf_email.getText();
                isChanged = true;
            }
            if(!gender.equals(tf_gender.getText()))
            {
                gender = tf_gender.getText();
                isChanged = true;
            }
            if(!number.equals(tf_number.getText()))
            {
                number = tf_number.getText();
                isChanged = true;
            }
            if(!username.equals(tf_username.getText()))
            {
                username = tf_username.getText();
                isChanged = true;
            } 
            if(!isChanged)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No changes made");
                alert.setContentText("Please make some changes to update profile");
                alert.showAndWait();
                return;
            }
            
            JSONObject profile = new JSONObject();
            profile.put("name", this.tf_username.getText());
            profile.put("email", this.tf_email.getText());
            profile.put("DOB", this.dp_dob.getEditor().getText());
            profile.put("country", this.tf_country.getText());
            profile.put("phoneNumber", this.tf_number.getText());
            profile.put("gender", this.tf_gender.getText());

            patientController.updateProfile(patId, profile.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Profile updated");
            alert.setContentText("Your profile has been updated successfully");
            alert.showAndWait();

            SearchDoctorController.clearHeaderTitles();
            SearchDoctorController.addHeaderTitle("Settings");
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/Settings.fxml")));
            PatientSettingsController controller = new PatientSettingsController();
            controller.setData(patientController, patId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            
            Parent parent = rootPane.getParent();
            if(parent!=null){
                ((AnchorPane)parent).getChildren().clear();
            }
            ((AnchorPane)parent).getChildren().add(root);
            
        }
        catch(Exception e)
        {
            System.out.println(e);
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

}
