package com.example.UIController;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class DoctorCertificateCardController implements Initializable{

    @FXML
    private AnchorPane aPane;
    @FXML
    private HBox hBox;
    @FXML
    private Label index;
    @FXML
    private Label name;
    @FXML
    private Label status;

    int docId;
    JSONArray certificates;
    JSONObject obj;
    int idTag;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        this.index.setText(idTag + ". ");
        this.name.setText(obj.getString("name"));
        this.status.setText(obj.getString("approvedStatus"));
        if(status.getText().equals("Pending"))
        {
            status.setText(" (Pending)");
            status.setVisible(true);
        }
        else
        {
            status.setVisible(false);
        }
    }

    public void setData(int docId, int index, String info, JSONArray certificates)
    {
        this.docId = docId;
        this.certificates = certificates;
        obj = new JSONObject(info);
        idTag = index;
    }

}
