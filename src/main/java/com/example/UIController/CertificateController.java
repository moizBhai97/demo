package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.BackEnd.DoctorController;
import com.example.BackEnd.PatientController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CertificateController implements Initializable{

    private int docId;
    private DoctorController dc;
    private PatientController pc;

    @FXML
    private VBox certificate;

    @FXML
    private Button closeBtn;

    @FXML
    private TableView<JSONObject> table;

    public void setData(PatientController pc, DoctorController dc, int docId)
    {
        this.pc = pc;
        this.dc = dc;
        this.docId = docId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        String jsonString = "";

        if(pc == null)
        {
            jsonString = dc.getCertificates(docId);

        }
        else if(dc == null)
        {
            jsonString = pc.getCertificates(docId);
        }

        setCertificates(jsonString);

    }

    public void setCertificates(String jsonString)
    {
        try
        {
            // Create a TableView
            certificate.getChildren().clear();
            table.getColumns().clear();

            TableColumn<JSONObject, String> descriptionColumn = new TableColumn<>("Name");
            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("name")));

            descriptionColumn.setPrefWidth(248);

            table.getColumns().addAll(descriptionColumn);

            JSONArray arr = new JSONArray(jsonString);

            ObservableList<JSONObject> certificateList = FXCollections.observableArrayList();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                certificateList.add(temp);
            }


            table.setItems(certificateList);

            table.setRowFactory(tv -> {
                TableRow<JSONObject> row = new TableRow<>();
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 0 0 10 0");

                row.setGraphic(separator);
                return row;
            });

            certificate.getChildren().add(table);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void closeButton(ActionEvent event)
    {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
