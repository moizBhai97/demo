package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.DoctorController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PatientDetailsController implements Initializable{

    int patId = 1;
    DoctorController dc = new DoctorController();

    @FXML
    private TextField country;
    @FXML
    private TextField dob;
    @FXML
    private TextField gender;
    @FXML
    private TextField number;
    @FXML
    private TextField name;
    @FXML
    private VBox history;
    @FXML
    private VBox appoints;

    @FXML
    private TableView<JSONObject> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
        String jsonString = "{\"country\":\"United States\",\"dob\":\"1990-01-01\",\"gender\":\"Male\",\"number\":\"1234567890\",\"name\":\"John Doe\",\"history\":[{\"description\":\"Cancer\",\"type\":\"flu\"},{\"description\":\"Death\",\"type\":\"Heat\"}]}";
            
        JSONObject obj = new JSONObject(jsonString);

        country.setText(obj.getString("country"));
        dob.setText(obj.getString("dob"));
        gender.setText(obj.getString("gender"));
        number.setText(obj.getString("number"));
        name.setText(obj.getString("name"));

        setHistory(obj.getJSONArray("history").toString());

    }

    public void setHistory(String jsonString)
    {
        try
        {
            // Create a TableView
            history.getChildren().clear();
            table.getColumns().clear();

            TableColumn<JSONObject, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("description")));

            descriptionColumn.setPrefWidth(124);

            TableColumn<JSONObject, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("type")));

            typeColumn.setPrefWidth(124);

            table.getColumns().addAll(descriptionColumn, typeColumn);

            JSONArray arr = new JSONArray(jsonString);

            ObservableList<JSONObject> historyList = FXCollections.observableArrayList();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                historyList.add(temp);
            }

            table.setItems(historyList);

            table.setRowFactory(tv -> {
                TableRow<JSONObject> row = new TableRow<>();
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 0 0 10 0");

                row.setGraphic(separator);
                return row;
            });

            history.getChildren().add(table);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
