package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

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
            Scene scene = new Scene(root, 700, 500);
            Stage stage = new Stage();
            Image img = new Image("file:src/main/resources/images/image 11.png");
            stage.setTitle("HealthySense");

            stage.getIcons().add(img);
            stage.setMinWidth(700);
            stage.setMinHeight(500);
            

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

   public void setServerName(String name) {
    String filePath = "config.json"; // replace with the actual file path
    try {
        // Read the contents of the JSON file
        File file = new File(filePath);
        FileReader reader = new FileReader(file);
        JSONObject json = new JSONObject(new JSONTokener(reader));

        // Update the serverName property
        json.put("serverName", name);

        // Write the changes back to the file
        FileWriter writer = new FileWriter(file);
        writer.write(json.toString());
        writer.flush();
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        launch();
       //App app = new App();
       //app.setServerName("localhost");
    }

}