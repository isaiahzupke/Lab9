/*
 Course: CS2852 021
 Term: Spring 2018
 Assignment: Lab 4: Autocomplete
 Author: Isaiah Zupke
 Date: 04/08/2018
 */
package zupkeim;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * this purpose of this class is to open a file and return its contents in an accepted manner
 */
public class ParseFile {
    private static Logger logger;

    /**
     * this is the brain of this class, it parses the file, with the precondition that the file is of the correct type
     * @param filename name of the file that user wants parsed
     * @param list name of the list the user wants the words copied to
     */
    public void parseFile(String filename, List<String> list, Logger logger){
        if(filename.substring(filename.lastIndexOf(".")).equalsIgnoreCase(".csv")){
            String line = "";
            final String CSV_SPLIT = ",";
            try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
                while((line = in.readLine()) != null){
                    String[] domain = line.split(CSV_SPLIT);
                    list.add(domain[1]);
                }
            } catch (FileNotFoundException notFound) {
                logException("Error: " + notFound.getMessage());
                alertPopUp("Error: " + notFound.getMessage());
            } catch (IOException ioException) {
                logException("Error: " + ioException.getMessage());
                alertPopUp("Error: " + ioException.getMessage());
            }
        } else {
            try(Scanner in = new Scanner(Paths.get(filename))){
                while(in.hasNextLine()){
                    list.add(in.nextLine());
                }
            } catch (IOException ioException){
                logException("Error: " + ioException.getMessage());
                alertPopUp("Error: " + ioException.getMessage());
            }
        }
    }

    private void alertPopUp(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File Read Error");
        alert.setHeaderText("Error when reading file");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void logException(String message){
        logger.severe("Caused by: " + this.getClass().getSimpleName() + ". " + message);
    }
}
