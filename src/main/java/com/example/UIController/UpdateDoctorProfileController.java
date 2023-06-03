package com.example.UIController;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateDoctorProfileController implements Initializable{

    @FXML
    private Button btn_add1;
    @FXML
    private Button button_back;
    @FXML
    private ComboBox<?> genderComboBox;
    @FXML
    private String gender_1;
    @FXML
    private String gender_2;
    @FXML
    private TextField tf_country;
    @FXML
    private TextField tf_dob;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_number;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_gender;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Label addIndex;
    @FXML
    private AnchorPane addPane;

    UpdateDoctorProfile2Controller updateDoctorProfile2Controller = new UpdateDoctorProfile2Controller();
    DoctorController doctorController = new DoctorController();

    JSONArray certificates;
    int docId = 101 ;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        JSONObject data = new JSONObject(doctorController.getDoctorData(docId));

        System.out.println(data.toString());

        tf_username.setText(data.getString("name"));
        tf_email.setText(data.getString("email"));
        tf_dob.setText(data.getString("DOB"));
        tf_country.setText(data.getString("country"));
        tf_number.setText(data.getString("phoneNumber"));
        tf_gender.setText(data.getString("gender"));


        certificates = new JSONArray(doctorController.getCertificates(docId));
        System.out.println(certificates.toString() + "-----------------------------------");
        refresh();
    }

    void setData(int docId) {

        this.docId = docId;
    }

    public void refresh()
    {
        flowPane.getChildren().clear();
        for(int i = 0; i < certificates.length(); i++)
        {
            //add in table
            JSONObject obj = certificates.getJSONObject(i);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((new URL("file:src/main/resources/com/example/doctorCertificateCard.fxml")));
                DoctorCertificateCardController doctorCertificateCardController = new DoctorCertificateCardController();
                doctorCertificateCardController.setData(docId, i+1, obj.toString(), certificates);
                loader.setController(doctorCertificateCardController);
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
    void addCertification(ActionEvent event) {

        System.out.println("Add Certification Button pressed");

        try {
            //this.btn_add1.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/updateProfile3Copy.fxml")));

            updateDoctorProfile2Controller = new UpdateDoctorProfile2Controller();
            updateDoctorProfile2Controller.setData(docId, certificates);
            loader.setController(updateDoctorProfile2Controller);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setOnHidden(e-> {    
                refresh();
             });

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
