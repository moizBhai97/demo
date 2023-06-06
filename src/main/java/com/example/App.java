package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;

import com.example.UIController.SearchDoctorController;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // GridPane root = new GridPane();
        // scene = new Scene(root, 640, 480);
        // root.setAlignment(javafx.geometry.Pos.CENTER);

        // Button button = new Button("Boook Appointment Screen");
        // button.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("book_apt.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button, 0, 0);

        // Button button2 = new Button("Search Doctors Screen 1");
        // button2.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {

        // FXMLLoader loader = new FXMLLoader();
        // // loader.setLocation(getClass().getResource("search_doctors.fxml"));
        // loader.setLocation(getClass().getResource("search_doctors - Copy.fxml"));

        // //loader.setLocation((new
        // URL("file:src/main/resources/com/example/search_doctors.fxml")));

        // SearchDoctorController searchDoctorController = new SearchDoctorController();

        // //searchDoctorController.setData(patientController, info.getInt("patId"));
        // loader.setController(searchDoctorController);
        // Parent root = loader.load();
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // // stage.setScene(scene);
        // // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button2, 0, 1);

        // Button button3 = new Button("Search Doctors Screen 2");
        // button3.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root =
        // FXMLLoader.load(getClass().getResource("search_doctors_Searched.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button3, 0, 2);

        // Button button4 = new Button("Doctor details");
        // button4.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("doctor_details.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button4, 0, 3);

        // Button button5 = new Button("Manage Appointments");
        // button5.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root =
        // FXMLLoader.load(getClass().getResource("manageAppointment.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button5, 0, 4);

        // Button button6 = new Button("Cancel Appointments");
        // button6.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("cancel.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button6, 0, 5);

        // Button button7 = new Button("pendingAppointment Details");
        // button7.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("app_detail.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button7, 0, 6);

        // Button button8 = new Button("finished Appointment Details 2");
        // button8.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("app_detail 2.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button8, 0, 7);

        // Button button9 = new Button("Review");
        // button9.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("Review.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button9, 0, 8);

        // Button button10 = new Button("Update profile 1");
        // button10.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("UpdateProfile1.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button10, 0, 9);

        // Button button11 = new Button("Update profile 2");
        // button11.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("UpdateProfile2.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button11, 0, 10);

        // Button button12 = new Button("Update profile 3");
        // button12.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root =
        // FXMLLoader.load(getClass().getResource("patient_details.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button12, 0, 11);

        // Button button13 = new Button("Resched apt");
        // button13.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("certificate.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // //turn maximize off
        // stage.setResizable(false);
        // stage.initModality(Modality.APPLICATION_MODAL); // Set the stage as modal

        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button13, 0, 12);

        // Button button14 = new Button("Start");
        // button14.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // try {
        // Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // root.add(button14, 0, 13);

        // stage.setScene(scene);

        // stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("Gay world");
    }

}