package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReschAppointmentController  implements Initializable
{
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label datePick;
    
    @FXML
    private ToggleGroup radios;

    @FXML
    private TextArea reason;

    @FXML
    private Button reschButton;

    @FXML
    private ToggleButton day;

    @FXML
    private ToggleButton night;

    @FXML
    private ToggleGroup times;

    @FXML
    private HBox timesBox;

    private AnchorPane prevPane;

    PatientController pc = new PatientController();
    int patId = 1;
    int appID;
    int docId = 101;
    int time;
    String date;
    String selectedTime = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        time = 1;
        reason.setWrapText(true);

        String dateUnformatted = LocalDate.now().toString();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

        date = dateConvert.toString();

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        datePick.setText(formattedDate);

        //System.out.println(pc.getSchedule(docId, date, time));

        JSONArray objs = new JSONArray(pc.getSchedule(docId, date, time));

        refreshToggleButtonGroup(objs.toString());
    }
    
    public void setData(PatientController pc, int patId, int appID, int docId, AnchorPane prevPane)
    {
        this.pc = pc;
        this.patId = patId;
        this.appID = appID;
        this.docId = docId;
        this.prevPane = prevPane;
    }

    public void morningButton(ActionEvent event) 
    {
        if(time == 2)
        {
            time = 1;
            selectedTime = null;
            day.setStyle("-fx-background-color: #2854C3; -fx-border-color: #2854C3; -fx-text-fill: white;");
            night.setStyle("-fx-background-color: transparent; -fx-border-color: #8C8FA5; -fx-border-width: 1px 1px 1px 1px; -fx-text-fill: black;");
            System.out.println(pc.getSchedule(docId, date, time));
            refreshToggleButtonGroup(pc.getSchedule(docId, date, time));
        }
    }

    public void nightButton(ActionEvent event) 
    {
        if(time == 1)
        {
            time = 2;
            selectedTime = null;
            night.setStyle("-fx-background-color: #2854C3; -fx-border-color: #2854C3; -fx-text-fill: white;");
            day.setStyle("-fx-background-color: transparent; -fx-border-color: #8C8FA5; -fx-border-width: 1px 1px 1px 1px; -fx-text-fill: black;");
            System.out.println(pc.getSchedule(docId, date, time));
            refreshToggleButtonGroup(pc.getSchedule(docId, date, time));
        }
    }

    public void reschButton(ActionEvent event) 
    {
        if(selectedTime == null)
        {
            System.out.println("Please select a time");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) radios.getSelectedToggle();
        String data = selectedRadioButton.getText();

        if(data.equals("Other"))
        {
            data = reason.getText();
        }

        JSONObject obj = new JSONObject();

        obj.put("docId", docId);
        obj.put("date", date);
        obj.put("time", selectedTime);
        obj.put("reason", data);

        pc.reschAppointment(obj.toString(), patId, appID);

        try
        {
         //   this.reschButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/manageAppointment.fxml")));
            //-------------------------------------------------------------------------------------------------//
            ManageAppointmentController manageAppointmentController = new ManageAppointmentController();
            manageAppointmentController.setData(pc, patId);
            //-------------------------------------------------------------------------------------------------//
            loader.setController(manageAppointmentController);

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // rootPane.setVisible(false);
            ((AnchorPane) prevPane.getParent()).getChildren().clear();
            ((AnchorPane) prevPane.getParent()).getChildren().add(pane);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void datePickerPressed(ActionEvent event) {

        String dateUnformatted = (datePicker.getValue().toString());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

        date = dateConvert.toString();

        System.out.println(pc.getSchedule(docId, date, time));

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        datePick.setText(formattedDate);

        refreshToggleButtonGroup(pc.getSchedule(docId, date, time));
    }

    public void handleToggleButtonAction(ToggleButton selectedButton) 
    {
        for (Toggle toggle : times.getToggles()) 
        {
            ToggleButton button = (ToggleButton) toggle;

            if(!(button.getUserData() instanceof Boolean && (boolean) button.getUserData()))
            {
                continue;
            }

            else if (button != selectedButton) 
            {
                button.setStyle("-fx-background-color: #eff6fc; -fx-border-color: #eff6fc; -fx-text-fill: black;");
            } 
            
            else 
            {
                button.setStyle("-fx-background-color: #2854C3; -fx-border-color: #2854C3; -fx-text-fill: white;");
                selectedTime=selectedButton.getText();
                System.out.println("Selected ToggleButton text: " + selectedTime);
            }
        }
    }

    public void refreshToggleButtonGroup(String info) 
    {
        timesBox.getChildren().clear();
        times = new ToggleGroup();

        JSONArray objs = new JSONArray(info);

        for (int i = 0; i < objs.length(); i++) 
        {
            JSONObject obj = objs.getJSONObject(i);
            ToggleButton button = new ToggleButton(obj.getString("time").substring(0, 5));
            button.setMinWidth(85);
            button.setMinHeight(49);

            button.setUserData(obj.getBoolean("available"));

            if(!obj.getBoolean("available"))
            {
                button.getStyleClass().add("button_NotAvail");
            }
            else
            {
                button.getStyleClass().add("inner_big_pane3");
            }

            button.setToggleGroup(times);
            button.setOnAction(event -> handleToggleButtonAction(button));
            timesBox.getChildren().add(button);
        }

        timesBox.setSpacing(15);
    }

    
    public void backBtnPressed(ActionEvent event){
        prevPane.setVisible(true);
    AnchorPane mainParentPane = (AnchorPane)prevPane.getParent();
    //remove last 
    mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);

    }
}
