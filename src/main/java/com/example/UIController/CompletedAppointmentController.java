package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CompletedAppointmentController  implements Initializable
{
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

    private AnchorPane prevPane;

    PatientController pc;
    int patId;
    int appID;
    int docId;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        String info = pc.getAppointment(patId, appID);

        JSONObject obj = new JSONObject(info);

        name.setText(obj.getJSONObject("doctor").getString("name"));
        specialization.setText(obj.getJSONObject("doctor").getString("specialization"));
        patients.setText(obj.getJSONObject("doctor").getString("patients"));
        experience.setText(obj.getJSONObject("doctor").getString("experience"));
        rating.setText(String.format("%.1f", obj.getJSONObject("doctor").getFloat("rating")));

        LocalTime startTime = LocalTime.parse(obj.getString("time"));
        LocalTime endTime = startTime.plusHours(1);
        timing.setText(startTime + " - " + endTime);

        date.setText(obj.getString("date"));

        if(obj.getJSONObject("payment").getBoolean("status"))
        {
            status.setText("Paid");
        }
        else
        {
            status.setText("UnPaid");
        }

        amount.setText(obj.getJSONObject("payment").getFloat("amount") + "");

        patName.setText(obj.getJSONObject("patient").getString("name"));

        String dobString = obj.getJSONObject("patient").getString("DOB");
        LocalDate dob = LocalDate.parse(dobString);
        int age = LocalDate.now().getYear() - dob.getYear();
        patAge.setText(age + "");
        
        patNum.setText(obj.getJSONObject("patient").getString("phoneNumber"));

    }
    
    public void setData(PatientController pc, int patId, int appID, int docId, AnchorPane prevPane)
    {
        this.docId = docId;
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
        this.prevPane = prevPane;
    }
    
    public void backBtnPressed(ActionEvent event)
    {
        prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)prevPane.getParent();
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
    }

    public void writeButton(ActionEvent event)
    {
        try
        {
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation((new URL("file:src/main/resources/com/example/Review.fxml")));

            WriteReviewController controller = new WriteReviewController();
            controller.setData(pc, patId, docId, prevPane);
            fxmlLoader.setController(controller);

            AnchorPane pane = fxmlLoader.load();
            AnchorPane.setTopAnchor(pane, -2.0);
            AnchorPane.setBottomAnchor(pane, -2.0);
            AnchorPane.setLeftAnchor(pane, -2.0);
            AnchorPane.setRightAnchor(pane, -2.0);
            
            ((AnchorPane) prevPane.getParent()).getChildren().add(pane);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
