package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.BackEnd.DBFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage2) throws IOException {

        try {

            GridPane grid = new GridPane();

            Stage stage = new Stage();

            grid.setPadding(new Insets(10));
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setAlignment(Pos.CENTER);

            grid.add(new Label("Enter your Server name:"), 0, 0);

            TextField textField = new TextField();
            textField.setPromptText("Enter");
            grid.add(textField, 1, 0);

            Button btn = new Button("Connect to Server");

            btn.setOnAction(e -> {
                try {
                    stage.close();
                    
                    setServerName(textField.getText());

                    DBFactory.getInstance().createHandler("SQL").createDatabaseAndTables("SDA_DB/SDA_DATABASE.sql","SDA_DB/SDA.sql");

                    Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                    Scene scene = new Scene(root, 700, 500);
                    Stage NewStage = new Stage();
                    Image img = new Image("file:src/main/resources/images/image 11.png");
                    NewStage.setTitle("HealthySense");

                    NewStage.getIcons().add(img);
                    NewStage.setMinWidth(700);
                    NewStage.setMinHeight(500);
                    

                    NewStage.setScene(scene);
                    NewStage.show();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });

            grid.add(btn, 0, 1);

            Button btn2 = new Button("Use Existing Server");

            btn2.setOnAction(e -> {
                try {
                    stage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                    Scene scene = new Scene(root, 700, 500);
                    Stage NewStage = new Stage();
                    Image img = new Image("file:src/main/resources/images/image 11.png");
                    NewStage.setTitle("HealthySense");

                    NewStage.getIcons().add(img);
                    NewStage.setMinWidth(700);
                    NewStage.setMinHeight(500);
                    

                    NewStage.setScene(scene);
                    NewStage.show();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Server not found");
                    alert.showAndWait();

                    e1.printStackTrace();
                }
            });

            grid.add(btn2,1, 1);

            Scene scene = new Scene(grid, 400, 200);
            stage.setScene(scene);
            stage.setTitle("HealthySense");

            stage.show();
        }
        catch(Exception e)
        {
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