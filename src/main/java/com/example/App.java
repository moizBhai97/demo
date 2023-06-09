package com.example;

import java.io.IOException;

import com.example.BackEnd.Setting;

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

    private void startApp()
    {
        try
        {
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage initialStage) throws IOException {

        Setting setting = new Setting();

        try {

            if(setting.testConnection())
            {
                startApp();
                return;
            }

            GridPane grid = new GridPane();

            Stage stage = new Stage();

            grid.setPadding(new Insets(10));
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setAlignment(Pos.CENTER);

            grid.add(new Label("Enter your Server name"), 0, 0);

            TextField textField = new TextField();
            textField.setPromptText("Enter");
            grid.add(textField, 1, 0);

            Button btn = new Button("Create Database");

            btn.setOnAction(e -> {
                try {

                    if(textField.getText().isEmpty())
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please enter server name");
                        alert.showAndWait();
                        return;
                    }
                    
                    setting.setServer(textField.getText());

                    Boolean flag = setting.createDatabaseAndTables();

                    if (flag) 
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Success");
                        alert.setContentText("Database created successfully");
                        alert.showAndWait();

                        stage.close();

                        startApp();
                    } 
                    else 
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Database not created");
                        alert.showAndWait();
                    }
                } 
                catch (Exception e1) 
                {
                    e1.printStackTrace();
                }
            });

            grid.add(btn, 1, 1);

            Button btn2 = new Button("Use Existing Database");

            btn2.setOnAction(e -> {
                try {

                    if(textField.getText().isEmpty())
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please enter server name");
                        alert.showAndWait();
                        return;
                    }

                    setting.setServer(textField.getText());

                    Boolean flag = setting.testConnection();

                    if(flag)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Success");
                        alert.setContentText("Connected to database successfully");
                        alert.showAndWait();

                        stage.close();

                        startApp();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Server not found");
                        alert.showAndWait();
                    }

                } 
                catch (Exception e1) 
                {
                    e1.printStackTrace();
                }
            });

            grid.add(btn2,0, 1);

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

    public static void main(String[] args) {
        launch();
    }

}