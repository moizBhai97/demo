package com.example.UIController;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import com.example.BackEnd.PatientController;

public class SearchDoctorController implements Initializable 
{
    PatientController patientController;
    int patId;

    double ratingFilter = -1;
    String specialty = "All";

    private ImageView blueStarIcon;


    @FXML
    Label doc_count;

    @FXML
    private Button alphabetical_sort_btn;

    @FXML
    private Button appointment_btn;

    @FXML
    private Pane big_pane;

    @FXML
    private Button filter_btn;

    @FXML
    private Button home_btn;

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

    @FXML
    private ToggleButton ascen_sort_toggle;

    private Button selectedToggle;

    @FXML
    private ToggleGroup sortingButtons;

    public String searhcedName;

    @FXML
    Pane filter_Pane;

    @FXML
    ImageView filter_img;

    ImageView whiteStarIcon;

    @FXML
    private Button filterApply;

    @FXML
    private Button filterRating1;

    @FXML
    private ImageView filterRating1Icon;

    @FXML
    private Button filterRating2;

    @FXML
    private ImageView filterRating2Icon;

    @FXML
    private Button filterRating3;

    @FXML
    private ImageView filterRating3Icon;

    @FXML
    private Button filterRating4;

    @FXML
    private ImageView filterRating4Icon;

    @FXML
    private Button filterRating5;

    @FXML
    private ImageView filterRating5Icon;

    @FXML
    private Button filterRatingAll;

    @FXML
    private ImageView filterRatingAllIcon;

    @FXML
    private ComboBox<String> specialtiesList;

    @FXML
    private String specialty_0;

    @FXML
    private String specialty_1;

    @FXML
    private String specialty_2;

    @FXML
    private String specialty_3;

    @FXML
    private String specialty_4;

    private FlowPane results_flowpane;

    @FXML
    public void filter_toggle(ActionEvent event) {
        try {
            if (filter_Pane.isVisible()) {
                filter_Pane.setVisible(false);
                filter_img.setVisible(false);
            } else {
                filter_Pane.setVisible(true);
                filter_img.setVisible(true);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void resetFilterColors() {
        filterRating1.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRating1.setGraphic(filterRating1Icon);
        filterRating2.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRating2.setGraphic(filterRating2Icon);
        filterRating3.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRating3.setGraphic(filterRating3Icon);
        filterRating4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRating4.setGraphic(filterRating4Icon);
        filterRating5.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRating5.setGraphic(filterRating5Icon);
        filterRatingAll.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        filterRatingAll.setGraphic(blueStarIcon);
    }

    @FXML
    public void filterApplyPressed(ActionEvent event) {
        String specialty = (String) specialtiesList.getValue();
        if (selectedToggle != null)
            createDoctorCards(patientController.sortDoctors(searhcedName, selectedToggle.getText(),
                    !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
        else
            createDoctorCards(patientController.sortDoctors(searhcedName, "A-Z", !ascen_sort_toggle.isSelected(),
                    ratingFilter, specialty));
        filter_Pane.setVisible(false);
        filter_img.setVisible(false);

    }

    @FXML
    public void ratingButtonPressed(ActionEvent event) {

        Button btn = (Button) event.getSource();
        String id = btn.getId();
        if (id.equals("filterRatingAll")) {
            ratingFilter = -1;
        } else {
            ratingFilter = Double.parseDouble(id.substring(id.length() - 1));
        }

        // get selected comboBox item
        resetFilterColors();

        btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;"); // background color
        btn.setGraphic(whiteStarIcon);
    }

    public void resetSortColors() {
        alphabetical_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        review_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        price_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        // text color
    }

    public void resetFilterPane()
    {
        resetFilterColors();
        ratingFilter = -1;
        specialtiesList.getSelectionModel().clearSelection();
        specialtiesList.setValue("All");
        specialty = "All";
    }

    @FXML
    public void alphabeticalSortPressed(ActionEvent event) {
        if (selectedToggle != alphabetical_sort_btn) {

            resetSortColors();
            alphabetical_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = alphabetical_sort_btn;

            // sortByAlphabetical();
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "A-Z", !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
        }
    }

    @FXML
    void ascendingPressed(ActionEvent event) {
        if (ascen_sort_toggle.isSelected()) {
            // change color
            ascen_sort_toggle.setStyle("-fx-background-color: #a1a1a1; -fx-text-fill: #ffffff;");
        } else {
            ascen_sort_toggle.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;");

        }

        createDoctorCards(patientController.sortDoctors(searhcedName, selectedToggle.getText(),
                !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
    }

    @FXML
    void priceSelected(ActionEvent event) {
        if (selectedToggle != price_sort_btn) {
            resetSortColors();
            price_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = price_sort_btn;
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Price", !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
        }

    }

    @FXML
    void reviewSortPressed(ActionEvent event) {
        if (selectedToggle != review_sort_btn) {
            resetSortColors();
            review_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Rating", !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
            selectedToggle = review_sort_btn;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        results_grid = new GridPane();
        results_flowpane = new FlowPane();

        //patientController = new PatientController();
        results_grid.setHgap(10);
        results_grid.setVgap(10);

        results_flowpane.setHgap(10);
        results_flowpane.setVgap(10);


        whiteStarIcon = new ImageView(filterRatingAllIcon.getImage());
        whiteStarIcon.setFitWidth(filterRatingAllIcon.getFitWidth());
        whiteStarIcon.setFitHeight(filterRatingAllIcon.getFitHeight());
        whiteStarIcon.setPreserveRatio(filterRatingAllIcon.isPreserveRatio());
        whiteStarIcon.setSmooth(filterRatingAllIcon.isSmooth());
        whiteStarIcon.setCache(filterRatingAllIcon.isCache());

        blueStarIcon = new ImageView(filterRating1Icon.getImage());
        blueStarIcon.setFitWidth(filterRating1Icon.getFitWidth());
        blueStarIcon.setFitHeight(filterRating1Icon.getFitHeight());
        blueStarIcon.setPreserveRatio(filterRating1Icon.isPreserveRatio());
        blueStarIcon.setSmooth(filterRating1Icon.isSmooth());
        blueStarIcon.setCache(filterRating1Icon.isCache());

        filterRatingAll.setGraphic(blueStarIcon);
        filter_Pane.setVisible(false);

        refresh();
        return;
    }

    public void setData(PatientController patientController, int patId) {
        this.patientController = patientController;
        this.patId = patId;
        System.out.println(patId);
    }

    GridPane results_grid;

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }

    public void refresh() {

        patientController = new PatientController();
        
        //esults_grid.setHgap(10);
        //results_grid.setVgap(10);
        //results_grid.getChildren().clear();
        results_scrollpane.getChildrenUnmodifiable().clear();
        // set padding
   //     results_grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        results_flowpane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        // set transparent background
  //      results_grid.setStyle("-fx-background-color: transparent;");
        results_flowpane.setStyle("-fx-background-color: transparent;");

        createDoctorCards(patientController.getTopDoctors());
        results_scrollpane.setContent(results_grid);
        doc_count.setText("Top Doctors");

    }

    public void searchDoctor(String value) {
        resetFilterPane();
        String result = patientController.searchDoctor(value);
        createDoctorCards(result);


    }

    public void createDoctorCards(String result) {
        try {
            results_flowpane.getChildren().clear();
            JSONArray doctors = new JSONArray(result);

            for (Object obj : doctors) {
                JSONObject doctor = (JSONObject) obj;

                // create doctorCard with these values
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation((new URL("file:src/main/resources/com/example/DoctorCard.fxml")));
                Pane doctorCard = loader.load();

                DoctorCardController controller = loader.getController();
                controller.setDoctor(doctor.toString());
                controller.setPatientController(patientController);

                controller.setPatientId(patId);
                results_flowpane.getChildren().add(doctorCard);

                
            }

            results_scrollpane.setContent(results_flowpane);
            doc_count.setText(results_flowpane.getChildren().size() + " Results");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTextKeyPressed(ActionEvent event) {
        System.out.println("searched");
        searhcedName = searchBar.getText();

        Media media = null;
        try {
            File file = new File("src/Rectangle.mp4");
            String path = file.getAbsolutePath();
            media = new Media(new File(path).toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(200);
        mediaView.setFitHeight(200);

        // Create a StackPane to center the MediaView in the ScrollPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(mediaView);
        stackPane.setPrefWidth(results_scrollpane.getViewportBounds().getWidth()-1);
        stackPane.setPrefHeight(results_scrollpane.getViewportBounds().getHeight()-1);

        // Calculate the size of the MediaView
        double mediaWidth = stackPane.getPrefWidth() * 0.6;
        double mediaHeight = stackPane.getPrefHeight() * 0.6;

        // Set the size of the MediaView
        mediaView.setFitWidth(mediaWidth);
        mediaView.setFitHeight(mediaHeight);

        // Center the MediaView in the StackPane
        StackPane.setAlignment(mediaView, Pos.CENTER);

        results_scrollpane.setContent(stackPane);

        mediaPlayer.setOnEndOfMedia(() -> {

                searchDoctor(searhcedName);
        });
        selectedToggle = null;

        resetSortColors();

    }
}