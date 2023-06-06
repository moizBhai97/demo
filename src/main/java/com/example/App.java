package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    public void start(Stage stage2) throws IOException {
      
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    Image img=new Image("file:src/main/resources/images/image 11.png");
                    stage.setTitle("HealthySense");
                    
                    stage.getIcons().add(img);

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            
       

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