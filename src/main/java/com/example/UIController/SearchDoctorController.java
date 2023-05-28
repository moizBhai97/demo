package com.example.UIController;

//JAVA FX IMPORTS
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.example.BackEnd.PatientController;

public class SearchDoctorController implements Initializable {
    // @FXML
    // GridPane results_grid;

    PatientController patientController;
    int patId;

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
    private ComboBox<?> specialtiesList;

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

    double ratingFilter = -1;
    String specialty = "All";

    private ImageView blueStarIcon;

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
        // createDoctorCards(patientController.sortDoctors(searhcedName, "Rating",
        // !ascen_sort_toggle.isSelected()));

    }

    public void resetSortColors() {
        alphabetical_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        review_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        price_sort_btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2854c3;"); // background color
        // text color

    }

    @FXML
    public void alphabeticalSortPressed(ActionEvent event) {
        if (selectedToggle != alphabetical_sort_btn) {

            resetSortColors();
            alphabetical_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = alphabetical_sort_btn;

            // sortByAlphabetical();
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "A-Z", !ascen_sort_toggle.isSelected(), -1, "All"));
        }
    }

    public void sortByAlphabetical() {
        // Get the GridPane inside the ScrollPane
        GridPane gridPane = (GridPane) results_scrollpane.getContent();

        // Sort the GridPane based on the doctor name
        List<Node> nodes = new ArrayList<>(gridPane.getChildren());
        nodes.sort((node1, node2) -> {
            String name1 = "";
            String name2 = "";
            for (Node child : ((Pane) node1).getChildren()) {
                if (child instanceof Label && child.getId().equals("docName")) {
                    name1 = ((Label) child).getText();
                    break;
                }
            }
            for (Node child : ((Pane) node2).getChildren()) {
                if (child instanceof Label && child.getId().equals("docName")) {
                    name2 = ((Label) child).getText();
                    break;
                }
            }
            return name1.compareToIgnoreCase(name2);
        });

        // Clear the GridPane and add the sorted nodes back in
        gridPane.getChildren().clear();
        int row = 0;
        int col = 0;
        for (Node node : nodes) {
            gridPane.add(node, col, row);
            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }
        results_scrollpane.setContent(gridPane);

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
                !ascen_sort_toggle.isSelected(), -1, "All"));

    }

    @FXML
    void priceSelected(ActionEvent event) {
        if (selectedToggle != price_sort_btn) {
            resetSortColors();
            price_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            selectedToggle = price_sort_btn;
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Price", !ascen_sort_toggle.isSelected(), -1, "All"));

        }

    }

    @FXML
    void reviewSortPressed(ActionEvent event) {
        if (selectedToggle != review_sort_btn) {
            resetSortColors();
            review_sort_btn.setStyle("-fx-background-color: #2854C3; -fx-text-fill: #ffffff;");
            createDoctorCards(
                    patientController.sortDoctors(searhcedName, "Rating", !ascen_sort_toggle.isSelected(), -1, "All"));
            selectedToggle = review_sort_btn;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results_grid = new GridPane();

        patientController = new PatientController();
        results_grid.setHgap(10);
        results_grid.setVgap(10);
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
        // filter_Pane.getChildren().remove(filterRatingAllIcon);
        // filter_Pane.getChildren().add(filterRatingAllIcon);
        refresh();
        return;
    }

    public void setData(PatientController patientController, int patId)
    {
        this.patientController = patientController;
        this.patId = patId;
        System.out.println(patId);
    }

    GridPane results_grid;

    public void setPatientController(PatientController patientController) {
        this.patientController = patientController;
    }

    public void refresh() {

        List<DoctorTemp> doctors = DoctorTemp.getDummyDoctor();
        results_grid.setHgap(10);
        results_grid.setVgap(10);
        results_grid.getChildren().clear();
        results_scrollpane.getChildrenUnmodifiable().clear();
        // set padding
        results_grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        // set transparent background
        results_grid.setStyle("-fx-background-color: transparent;");
        int rowindex = 0;
        int columnindex = 0;
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation((new URL("file:src/main/resources/com/example/DoctorCard.fxml")));
                Pane doctorCard = loader.load();
                DoctorCardController controller = loader.getController();
                controller.setDoctor(doctors.get(i));
                // controller.setParentController(this);
                results_grid.add(doctorCard, rowindex, columnindex);
                rowindex++;
                if (rowindex == 2) {
                    rowindex = 0;
                    columnindex++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        results_scrollpane.setContent(results_grid);

    }

    public void searchDoctor(String value) {
        String result = patientController.searchDoctor(value);
        createDoctorCards(result);
        // read json and create doctorCard for each doctor

    }

    public void createDoctorCards(String result) {
        try {

            results_grid.getChildren().clear();
            JSONArray doctors = new JSONArray(result);
            doc_count.setText(doctors.length() + " Results");

            int rowindex = 0;
            int columnindex = 0;
            for (Object obj : doctors) {
                JSONObject doctor = (JSONObject) obj;
                String name = (String) doctor.get("name");
                String specialization = (String) doctor.get("specialization");
                String price = (String) doctor.get("price");
                String rating = (String) doctor.get("rating");
                // create doctorCard with these values

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation((new URL("file:src/main/resources/com/example/DoctorCard.fxml")));
                Pane doctorCard = loader.load();

                DoctorCardController controller = loader.getController();
                controller.setDoctor(name, specialization, price, rating);
                // controller.setParentController(this);
                results_grid.add(doctorCard, columnindex, rowindex);
                columnindex++;
                if (columnindex == 2) {
                    columnindex = 0;
                    rowindex++;
                }
            }
            results_scrollpane.setContent(results_grid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTextKeyPressed(ActionEvent event) {
        System.out.println("searched");
        searhcedName = searchBar.getText();
        searchDoctor(searhcedName);
        resetSortColors();

    }

}