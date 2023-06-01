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
            // history.getChildren().clear();

            // history.setSpacing(15);

            // history.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

            // HBox historyHead = new HBox();

            // historyHead.setSpacing(130);

            // Label historyDesc = new Label("Description");
            // historyDesc.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            // historyHead.getChildren().add(historyDesc);

            // Label historyType = new Label("Type");
            // historyType.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            // historyHead.getChildren().add(historyType);

            // history.getChildren().add(historyHead);

            // Separator separator = new Separator();

            // history.getChildren().add(separator);

            // JSONArray arr = new JSONArray(jsonString);

            // for(int i = 0; i < arr.length(); i++)
            // {
            //     HBox temp = new HBox();
            //     temp.setSpacing(130);

            //     JSONObject tempObj = arr.getJSONObject(i);
            //     Label label = new Label(tempObj.getString("description"));
            //     Text text = new Text(label.getText());
            //     text.setFont(label.getFont());
            //     double width = text.getBoundsInLocal().getWidth();
            //     label.setPrefWidth(width);

            //     //temp.getChildren().add(new Label(tempObj.getString("description")));
            //     //temp.getChildren().add(new Label(tempObj.getString("type")));

            //     // label = new Label(tempObj.getString("type"));
            //     // text = new Text(label.getText());
            //     // text.setFont(label.getFont());
            //     // width = text.getBoundsInLocal().getWidth();
            //     // label.setPrefWidth(width);

            //     history.getChildren().add(temp);

            //     Separator tempSeparator = new Separator();
            //     history.getChildren().add(tempSeparator);
            //}

            // Create a TableView
            history.getChildren().clear();
            table.getColumns().clear();
            //TableView<JSONObject> tableView = new TableView<>();
            // Create columns for description and type
            TableColumn<JSONObject, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("description")));

            descriptionColumn.setPrefWidth(124);

            //descriptionColumn.setStyle("-fx-alignment: RIGHT;");

            TableColumn<JSONObject, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("type")));

            typeColumn.setPrefWidth(124);

            // Add columns to the TableView
            table.getColumns().addAll(descriptionColumn, typeColumn);

            // Initialize history with all the previous medical history of the patient
            JSONArray arr = new JSONArray(jsonString);

            // Create an ObservableList to hold the JSONObjects
            ObservableList<JSONObject> historyList = FXCollections.observableArrayList();

            // Iterate over the array and add JSONObjects to the list
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                historyList.add(temp);
            }

            // Set the items in the TableView
            table.setItems(historyList);
            // Add line separator between rows
            table.setRowFactory(tv -> {
                TableRow<JSONObject> row = new TableRow<>();
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 0 0 10 0"); // Add padding to bottom of separator
                // Add line separator between rows
                row.setGraphic(separator);
                return row;
            });

            history.getChildren().add(table);

            // Add the TableView to the parent container or scene
            // parentContainer.getChildren().add(tableView);
            // or
            // Scene scene = new Scene(tableView);

        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
