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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class PatientDetailsController implements Initializable{

    int patId;
    int docId;
    DoctorController dc;

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
    private AnchorPane rootPane;

    public void setData(DoctorController dc, int docId, int patId,AnchorPane rootPane)
    {
        this.dc = dc;
        this.docId = docId;
        this.patId = patId;
        this.rootPane = rootPane;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
        String jsonString = dc.getPatientDetails(patId);

        System.out.println(jsonString);

        JSONObject obj = new JSONObject(jsonString);

        country.setText(obj.getString("country"));
        dob.setText(obj.getString("DOB"));
        gender.setText(obj.getString("gender"));
        number.setText(obj.getString("phoneNumber"));
        name.setText(obj.getString("name"));

        setHistory(obj.getString("history"));
        setAppointments(obj.getString("appointments"));

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
            doctorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("name")));

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
                                System.out.println("Appointment Id: "+obj.toString());
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/patient_appoint.fxml")));
            //-------------------------------------------------------------------------------------------------//
            
            PatientAppointmentDetailsController patientAppointmentDetailsController = new PatientAppointmentDetailsController();

            patientAppointmentDetailsController.setData(dc, appId, docId, patId,rootPane);
            loader.setController(patientAppointmentDetailsController);

            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
                  
           ((AnchorPane)rootPane.getParent()).getChildren().add(pane);
           DoctorMainController.addHeaderTitle("Patient Appointment Details");
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        
    }

    @FXML
    public void backBtnPressed(ActionEvent event)
    {
        rootPane.setVisible(true);
        AnchorPane mainParentPane = (AnchorPane)rootPane.getParent();
        //remove last 
        mainParentPane.getChildren().remove(mainParentPane.getChildren().size()-1);
        DoctorMainController.popHeaderTitle();  
    }



}
