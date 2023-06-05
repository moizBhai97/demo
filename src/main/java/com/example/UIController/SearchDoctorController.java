package com.example.UIController;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import com.example.BackEnd.PatientController;

public class SearchDoctorController implements Initializable {

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
    private AnchorPane rootPane;

    @FXML
    private AnchorPane childAnchorPane;

    @FXML
    private Button searchBtn;

    @FXML
    private Button appointmentBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button logoutBtn;

    private Button selectedDashbordBtn;

    @FXML
    private Label headerTitle;

    private static ObservableList<Label> headerTitles = FXCollections.observableArrayList();

    public static void addHeaderTitle(String title) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        StringProperty textProperty = new SimpleStringProperty(title);
        label.textProperty().bind(textProperty);
        headerTitles.add(label);
        // headerTitle.setText(title);
    }

    public static void clearHeaderTitles() {
        headerTitles.clear();
    }

    public static void removeTopTitle() {
        headerTitles.remove(headerTitles.size() - 1);
    }

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

    public boolean isSearchNull() {

        if (searhcedName == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No search query");
            alert.setContentText("Please enter a name to search for");
            alert.showAndWait();
            return true;
        }
        return false;

    }

    @FXML
    public void filterApplyPressed(ActionEvent event) {
        String specialty = (String) specialtiesList.getValue();

        if (isSearchNull()) {
            return;
        }
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
        if (isSearchNull()) {
            return;
        }
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

    public void resetFilterPane() {
        resetFilterColors();
        ratingFilter = -1;
        specialtiesList.getSelectionModel().clearSelection();
        specialtiesList.setValue("All");
        specialty = "All";
    }

    @FXML
    public void alphabeticalSortPressed(ActionEvent event) {
        if (isSearchNull()) {
            return;
        }
        if (selectedToggle != alphabetical_sort_btn) {

            resetSortColors();
            alphabetical_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = alphabetical_sort_btn;

            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "A-Z", !ascen_sort_toggle.isSelected(), ratingFilter,
                            specialty));
        }
    }

    @FXML
    void ascendingPressed(ActionEvent event) {
        if (isSearchNull()) {
            return;
        }
        if (ascen_sort_toggle.isSelected()) {
            // change color
            ascen_sort_toggle.setStyle("-fx-background-color: #a1a1a1; -fx-text-fill: #ffffff;");
        } else {
            ascen_sort_toggle.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;");

        }
        if (selectedToggle != null)
            createDoctorCards(patientController.sortDoctors(searhcedName, selectedToggle.getText(),
                    !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
        else
            createDoctorCards(patientController.sortDoctors(searhcedName, "A-Z",
                    !ascen_sort_toggle.isSelected(), ratingFilter, specialty));
    }

    @FXML
    void priceSelected(ActionEvent event) {
        if (isSearchNull()) {
            return;
        }
        if (selectedToggle != price_sort_btn) {
            resetSortColors();
            price_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = price_sort_btn;
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Price", !ascen_sort_toggle.isSelected(), ratingFilter,
                            specialty));
        }

    }

    @FXML
    void reviewSortPressed(ActionEvent event) {
        if (isSearchNull()) {
            return;
        }
        if (selectedToggle != review_sort_btn) {
            resetSortColors();
            review_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Rating", !ascen_sort_toggle.isSelected(), ratingFilter,
                            specialty));
            selectedToggle = review_sort_btn;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ImageView imageView = null;
        if (searchBtn != null) {
            imageView = (ImageView) searchBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            searchBtn.setStyle("-fx-text-fill: #2854c3;");
        }

        addHeaderTitle("Search");

        headerTitles.addListener((ListChangeListener<Label>) change -> {
            if (headerTitles.isEmpty()) {
                headerTitle.setText("");
            } else {
                headerTitle.textProperty().bind(headerTitles.get(headerTitles.size() - 1).textProperty());
            }
        });
        selectedDashbordBtn = searchBtn;
        results_flowpane = new FlowPane();

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

    // GridPane results_grid;

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }

    public void refresh() {

        results_scrollpane.getChildrenUnmodifiable().clear();
        // set padding
        results_flowpane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        results_flowpane.setStyle("-fx-background-color: transparent;");

        createDoctorCards(patientController.getTopDoctors());
        results_scrollpane.setContent(results_flowpane);
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
                controller.setPrevPane(childAnchorPane);
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

    MediaPlayer mediaPlayer;

    public void searchTextKeyPressed(ActionEvent event) {

        System.out.println("searched");

        Media media = null;
        try {
            File file = new File("src/Rectangle.mp4");
            String path = file.getAbsolutePath();
            media = new Media(new File(path).toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer(media);
        if (mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED) {
            mediaPlayer.stop();
            System.out.println("stopped");
        }
        searhcedName = searchBar.getText();
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(200);
        mediaView.setFitHeight(200);

        // Create a StackPane to center the MediaView in the ScrollPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(mediaView);
        stackPane.setPrefWidth(results_scrollpane.getViewportBounds().getWidth() - 1);
        stackPane.setPrefHeight(results_scrollpane.getViewportBounds().getHeight() - 1);

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

    @FXML
    Button backBtn;

    public void resetDashboardButtons() {
        searchBtn.setStyle("-fx-text-fill: #979797");
        searchBtn.getGraphic().setEffect(null);
        appointmentBtn.setStyle("-fx-text-fill: #979797");
        appointmentBtn.getGraphic().setEffect(null);
        logoutBtn.setStyle("-fx-text-fill: #979797");
        logoutBtn.getGraphic().setEffect(null);
        settingsBtn.setStyle("-fx-text-fill: #979797");
        settingsBtn.getGraphic().setEffect(null);

    }

    public void searchBtnPressed(ActionEvent event) {
        // open search
        try {
            if (selectedDashbordBtn == searchBtn) {
                return;
            }

            clearHeaderTitles();
            addHeaderTitle("Search");

            selectedDashbordBtn = searchBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) searchBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            searchBtn.setStyle("-fx-text-fill: #2854c3;");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/search_doctors.fxml")));
            SearchDoctorController controller = new SearchDoctorController();
            controller.setData(patientController, patId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appointmentBtnPressed(ActionEvent event) {
        // open manage appointments
        try {
            if (selectedDashbordBtn == appointmentBtn) {
                return;
            }

            clearHeaderTitles();
            addHeaderTitle("Manage Appointments");
            selectedDashbordBtn = appointmentBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) appointmentBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            appointmentBtn.setStyle("-fx-text-fill: #2854c3;");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/ManageAppointment.fxml")));
            ManageAppointmentController controller = new ManageAppointmentController();
            controller.setData(patientController, patId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settingsBtnPressed(ActionEvent event){
        try{
            if(selectedDashbordBtn == settingsBtn){
                return;
            }

            clearHeaderTitles();
            addHeaderTitle("Settings");
            selectedDashbordBtn = settingsBtn;
            resetDashboardButtons();
            ImageView imageView = (ImageView) settingsBtn.getGraphic();
            imageView.setEffect(new InnerShadow(100, Color.web("#2854c3")));
            settingsBtn.setStyle("-fx-text-fill: #2854c3;");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((new URL("file:src/main/resources/com/example/Settings.fxml")));
            PatientSettingsController controller = new PatientSettingsController();
            controller.setData(patientController, patId);
            loader.setController(controller);

            AnchorPane root = loader.load();
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public void logoutBtnPressed(ActionEvent event){

        try{
            if(selectedDashbordBtn == logoutBtn){
                return;
            }

            FXMLLoader loader = new FXMLLoader(new URL("file:src/main/resources/com/example/start.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
