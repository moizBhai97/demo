package com.example.UIController;
//JAVA FX IMPORTS
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.example.BackEnd.PatientController;



public class SearchDoctorController implements Initializable{
    // @FXML
    // GridPane results_grid;

    PatientController patientController;
    
    @FXML
    private Button alphabetical_sort_btn;

    @FXML
    private Button appointment_btn;

    @FXML
    private ToggleButton ascen_sort_toggle;

    @FXML
    private Pane big_pane;

    @FXML
    private Button filter_btn;

    @FXML
    private ImageView filter_img;

    @FXML
    private Button home_btn;

    @FXML
    private Pane iner_pane1;

    @FXML
    private Pane iner_pane11;

    @FXML
    private Pane iner_pane111;

    @FXML
    private Pane iner_pane1111;

    @FXML
    private Pane iner_pane1112;

    @FXML
    private Button location_sort_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button price_sort_btn;

    @FXML
    private ImageView prof_img;

    @FXML
    private ScrollPane results_scrollpane;

    @FXML
    private Button review_sort_btn;

    @FXML
    private HBox root;

    @FXML
    private TextField searchBar;

    @FXML
    private Pane searchPane;

    @FXML
    private Button search_btn;

    @FXML
    private Button settings_btn;

    @FXML
    private AnchorPane side_anchor;

    @FXML
    private Pane top_pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        return;
    }

    public void setPatientController(PatientController patientController){
        this.patientController = patientController;
    }
  
    public void refresh(){

        List<DoctorTemp> doctors=DoctorTemp.getDummyDoctor();
        GridPane results_grid = new GridPane();
        results_grid.setHgap(10);
        results_grid.setVgap(10);
        results_grid.getChildren().clear();
        results_scrollpane.getChildrenUnmodifiable().clear();
        //set padding
        results_grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        //set transparent background
        results_grid.setStyle("-fx-background-color: transparent;");
        int rowindex=0;
        int columnindex=0;
        for(int i=0;i<10;i++){
            try{
                FXMLLoader loader = new FXMLLoader();
                
                loader.setLocation((new URL("file:src/main/resources/com/example/DoctorCard.fxml")));
                Pane doctorCard = loader.load();
                DoctorCardController controller = loader.getController();
                controller.setDoctor(doctors.get(i));
                //controller.setParentController(this);
                results_grid.add(doctorCard, rowindex, columnindex);
                rowindex++;
                if(rowindex==2){
                    rowindex=0;
                    columnindex++;
                }

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        results_scrollpane.setContent(results_grid);



        
    }

    public void searchTextKeyPressed(ActionEvent event){
        System.out.println("searched");
        patientController.searchDoctor(searchBar.getText());

    }


    

    
}