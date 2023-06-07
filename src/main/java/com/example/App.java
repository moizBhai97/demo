package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        System.out.println("Hello World!");
    }

}