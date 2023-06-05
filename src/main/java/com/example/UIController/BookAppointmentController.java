package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import com.example.BackEnd.PatientController;

public class BookAppointmentController implements Initializable {

    
    PatientController pc;
    int patId;
    int appID;
    int docId;
    int time;
    String date;
    String selectedTime = null;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button backBtn;

    @FXML
    private Button bookBtn;

    @FXML
    private Pane cancel_pane;

    @FXML
    private Pane confirm_pane;

    @FXML
    private Label datePick;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ToggleButton day;

    @FXML
    private Label experience;

    @FXML
    private Label feeLabel;

    @FXML
    private Label name;

    @FXML
    private ToggleButton night;

    @FXML
    private Button no_btn_cancel;

    @FXML
    private Button no_btn_confirm;

    @FXML
    private Label patients;

    @FXML
    private ToggleGroup period;

    @FXML
    private TextArea problem_text;

    @FXML
    private Label rating;

    @FXML
    private Label specialization;

    @FXML
    private ToggleGroup times;

    @FXML
    private HBox timesBox;

    @FXML
    private Button yes_btn_cancel;

    @FXML
    private Button yes_btn_confirm;


    private AnchorPane prevPane;

    private String screenInfo;

    @FXML
    void cancel(ActionEvent event) {
        try {
            if (cancel_pane.isVisible()) {
                cancel_pane.setVisible(false);
            } else {
                cancel_pane.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void confirm(ActionEvent event) {
        try {
            if (confirm_pane.isVisible()) {
                confirm_pane.setVisible(false);
            } else {
                confirm_pane.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        datePicker.setDayCellFactory(getDisablePastDatesCellFactory());

        time = 1;

        String dateUnformatted = LocalDate.now().toString();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

        date = dateConvert.toString();

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
        String formattedDate = dateConvert.format(outputFormatter);
        System.out.println(formattedDate);
        datePick.setText(formattedDate);

        JSONArray objs = new JSONArray(pc.getSchedule(docId, date, time));

        refreshToggleButtonGroup(objs.toString());

        JSONObject obj = new JSONObject(screenInfo);

        name.setText(obj.getString("name"));
        specialization.setText(obj.getString("specialization"));
        patients.setText(obj.getInt("patients") + "");
        experience.setText(obj.getInt("experience") + "");
        rating.setText(String.format("%.1f", obj.getFloat("rating")));
        feeLabel.setText(String.format("%.1f", obj.getFloat("fee")));
    }

    public void setData(PatientController patientController, int Id, int patId, String screenInfo, AnchorPane prevPane){
        this.screenInfo = screenInfo;
        this.pc = patientController;
        this.docId = Id;
        this.patId = patId;
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

    private Callback<DatePicker, DateCell> getDisablePastDatesCellFactory() {
        return new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Optional: Set a different background color for disabled dates
                        }
                    }
                };
            }
        };
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
            button.setCursor(javafx.scene.Cursor.HAND);

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

    public void bookButton(ActionEvent event) 
    {
        try
        {
            if(selectedTime == null)
            {
                System.out.println("Please select a time");
                return;
            }

            JSONObject obj = new JSONObject();
            obj.put("date", date);
            obj.put("time", selectedTime);
            obj.put("problem", problem_text.getText());
            obj.put("docId", docId);

            pc.bookSlot(obj.toString(), patId);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/payment.fxml")));

            PaymentController paymentController = new PaymentController();
            paymentController.setData(pc, patId, feeLabel.getText() , name.getText(), obj.toString(), this.bookBtn.getScene());
            loader.setController(paymentController);

            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            Point2D point = prevPane.localToScreen(-2, -2);

            double parentTopLeftX = point.getX();
            double parentTopLeftY = point.getY();

            System.out.println(parentTopLeftX + " " + parentTopLeftY);

            // Set the child stage position relative to the parent's top left corner
            stage.setX(parentTopLeftX);
            stage.setY(parentTopLeftY);
            stage.setWidth(parentPane.getWidth()-2);
            stage.setHeight(parentPane.getHeight()-2);
            
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    
    public void backBtnPressed(ActionEvent event){
        prevPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)prevPane.getParent();
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
    }

}