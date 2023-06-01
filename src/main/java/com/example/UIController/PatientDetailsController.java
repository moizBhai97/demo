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
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PatientDetailsController implements Initializable{

    int patId = 1;
    int docId;
    DoctorController dc;;

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

    @FXML
    private TableView<JSONObject> table2;

    public void setData(DoctorController dc, int docId, int patId)
    {
        this.dc = dc;
        this.docId = docId;
        this.patId = patId;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
        String jsonString = "{\"country\":\"United States\",\"dob\":\"1990-01-01\",\"gender\":\"Male\",\"number\":\"1234567890\",\"name\":\"John Doe\",\"history\":[{\"description\":\"Cancer\",\"type\":\"flu\"},{\"description\":\"Death\",\"type\":\"Heat\"}],\"appointment\":[{\"appId\":\"1\",\"docName\":\"Dr. Smith\",\"date\":\"2023-06-15\"},{\"appId\":\"2\",\"docName\":\"Dr. Johnson\",\"date\":\"2023-06-20\"}]}";

        JSONObject obj = new JSONObject(jsonString);

        country.setText(obj.getString("country"));
        dob.setText(obj.getString("dob"));
        gender.setText(obj.getString("gender"));
        number.setText(obj.getString("number"));
        name.setText(obj.getString("name"));

        setHistory(obj.getJSONArray("history").toString());
        setAppointments(obj.getJSONArray("appointment").toString());

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

    public void setAppointments(String jsonString)
    {
        try
        {
            // Create a TableView

            appoints.getChildren().clear();
            table2.getColumns().clear();

            JSONArray arr = new JSONArray(jsonString);

            TableColumn<JSONObject, String> doctorColumn = new TableColumn<>("Doctor");
            doctorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("docName")));

            doctorColumn.setPrefWidth(124);
            
            doctorColumn.setCellFactory(column -> {
                return new TableCell<JSONObject, String>() {
                    private final Text text;
                    
                    {
                        text = new Text();
                        setGraphic(text);
                        
                        setOnMouseEntered(event -> {
                            if (!isEmpty()) {
                                text.setCursor(Cursor.HAND);
                            }
                        });
            
                        setOnMouseExited(event -> {
                            if (!isEmpty()) {
                                text.setCursor(Cursor.DEFAULT);
                            }
                        });
            
                        setOnMousePressed(event -> {
                            if (event.isPrimaryButtonDown() && !isEmpty()) {
                                JSONObject obj = getTableRow().getItem();
                                getAppointment(obj.getInt("appId"), getScene().getWindow());
                            }
                        });
                    }
            
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            text.setText(null);
                        } else {
                            text.setText(item);
                            text.setStyle("-fx-fill: #2854c3;");
                        }
                    }
                };
            });
            

            TableColumn<JSONObject, String> dateColumn = new TableColumn<>("Date");
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("date")));

            dateColumn.setPrefWidth(124);

            table2.getColumns().addAll(doctorColumn, dateColumn);

            ObservableList<JSONObject> appointsList = FXCollections.observableArrayList();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = arr.getJSONObject(i);
                appointsList.add(temp);
            }

            table2.setItems(appointsList);

            table2.setRowFactory(tv -> {
                TableRow<JSONObject> row = new TableRow<>();
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 0 0 10 0");

                row.setGraphic(separator);
                return row;
            });

            appoints.getChildren().add(table2);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void getAppointment(int appId, Window win)
    {
        try
        {

            win.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/start.fxml")));
            //-------------------------------------------------------------------------------------------------//

            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }



}
