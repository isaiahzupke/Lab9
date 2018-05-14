/*
 Course: CS2852 021
 Term: Spring 2018
 Assignment: Lab 4: Autocomplete
 Author: Isaiah Zupke
 Date: 04/08/2018
 */
package zupkeim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creates the stage for the autocomplete program
 */
public class AutoComplete extends Application {
    /**
     * Starts the primary stage
     * @param primaryStage is the stage
     * @throws Exception any exception thrown
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        final int WIDTH = 375, HEIGHT = 500;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("autocomplete.fxml"));
        root = loader.load();
        primaryStage.setTitle("Auto Complete");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    /**
     * starts program
     * @param args is launched
     */
    public static void main(String[] args) {
        launch(args);
    }
}
