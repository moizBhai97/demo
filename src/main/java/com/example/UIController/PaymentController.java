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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

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

    PatientController pc;
    int patId;
    String fee;
    String docName;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        try
        {
            String dateUnformatted = LocalDate.now().toString();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateConvert = LocalDate.parse(dateUnformatted, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.ENGLISH);
            String formattedDate = dateConvert.format(outputFormatter);

            String time = java.time.LocalTime.now().toString();
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
    
    public void setData(PatientController pc, int patId, String fee, String docName)
    {
        this.pc = pc;
        this.patId = patId;
        this.fee = fee;
        this.docName = docName;
    }

    public void cancelButton(ActionEvent event) 
    {
        
    }
}
