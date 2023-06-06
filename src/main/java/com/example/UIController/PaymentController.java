package com.example.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.example.BackEnd.PatientController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PaymentController  implements Initializable
{
    @FXML
    private Label dateTime;

    @FXML
    private Label name;

    @FXML
    private Label amount;

    @FXML
    private Label totalAmount;

    @FXML
    private Button paymentBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private AnchorPane parentPane;

    private PatientController pc;
    private int patId;
    private String fee;
    private String docName;
    private String appoint;
    private String date;
    private String time;
    Scene prev;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        try
        {
            AnchorPane.setTopAnchor(parentPane, 0.0);
            AnchorPane.setBottomAnchor(parentPane, 0.0);
            AnchorPane.setLeftAnchor(parentPane, 0.0);
            AnchorPane.setRightAnchor(parentPane, 0.0);

            String dateUnformatted = LocalDate.now().toString();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

            date = dateConvert.toString();

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
            String formattedDate = dateConvert.format(outputFormatter);

            time = java.time.LocalTime.now().toString();
            time = time.substring(0, 5);

            formattedDate = formattedDate + " | " + time;

            System.out.println(formattedDate);
            dateTime.setText(formattedDate);

            amount.setText(fee);

            totalAmount.setText(fee);

            name.setText(docName);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    public void setData(PatientController pc, int patId, String fee, String docName, String appoint, Scene prev)
    {
        this.pc = pc;
        this.patId = patId;
        this.fee = fee;
        this.docName = docName;
        this.appoint = appoint;
        this.prev = prev;
    }

    public void paymentButton(ActionEvent event)
    {
        try
        {
            JSONObject obj = new JSONObject(appoint);
            
            JSONObject payment = new JSONObject();
            payment.put("amount", fee);
            payment.put("status", false);
            payment.put("date", date);
            payment.put("time", time);

            obj.put("payment", payment);

            pc.saveAppointment(obj.toString(), patId);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors - Copy.fxml")));

            SearchDoctorController searchDoctorController = new SearchDoctorController();
            searchDoctorController.setData(pc, patId);
            loader.setController(searchDoctorController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            prev.getWindow().hide();
            this.paymentBtn.getScene().getWindow().hide();

            Stage prevWin = (Stage) prev.getWindow();

            stage.setWidth(prevWin.getWidth());
            stage.setHeight(prevWin.getHeight());
            stage.setX(prevWin.getX());
            stage.setY(prevWin.getY());

            stage.setMinWidth(825);
            stage.setMinHeight(680);

            stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent event) 
    {
        try
        {
            pc.cancelSlot(appoint, patId);

            this.cancelBtn.getScene().getWindow().hide();
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
