/*
 Course: CS2852 021
 Term: Spring 2018
 Assignment: Lab 4: Autocomplete
 Author: Isaiah Zupke
 Date: 04/08/2018
 */
package zupkeim;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This is the controller for the autocomplete.fxml file and drives all of the
 * UI elements & computations for the AutoComplete program.
 */
public class AutoCompleteController implements Initializable{
    private static Logger exceptionLogger;

    @FXML
    private Label timeRequired;
    @FXML
    private Label amountMatches;
    @FXML
    private TextArea matches;
    @FXML
    private TextField searchQuery;

    @FXML
    private Menu strategyMenuDropdown;
    @FXML
    private CheckMenuItem indexArrayList;
    @FXML
    private CheckMenuItem indexLinkedList;
    @FXML
    private CheckMenuItem iterateArrayList;
    @FXML
    private CheckMenuItem iterateLinkedList;
    @FXML
    private CheckMenuItem parallelStreamArrayList;
    @FXML
    private CheckMenuItem parallelStreamLinkedList;

    private ParallelStreamSearch parallelStreamSearch;
    private IndexSearch indexSearch;
    private ForeachSearch foreachSearch;
    private File file;
    private List<String> list;

    /**
     * Init method, creates the exception logger
     * @param location the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            exceptionLogger = Logger.getLogger("ExceptionLogger");
            exceptionLogger.addHandler(new FileHandler("lab4.txt"));
        } catch (IOException logFile){
            System.err.println("Unable to open log file: " + logFile.getMessage());
        }
    }

    /**
     * This method creates a window for a user to select a file. This method then saves the string of that
     * file name to an instance variable and it is used later.
     */
    @FXML
    public void openFile(){
        try {
            FileChooser file = new FileChooser();
            file.setTitle("File to Open:");
            file.setInitialDirectory(new File("."));
            file.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("csv", "*.csv"),
                    new FileChooser.ExtensionFilter("txt", "*.txt")
            );
            File input = file.showOpenDialog(null);
            String fileExtension = input.toString().substring(input.toString().lastIndexOf("."));
            if(fileExtension.equals(".csv") || fileExtension.equals(".txt")){
                this.file = input;
                strategyMenuDropdown.setDisable(false);
            } else {
                alertPopUp("Please select a correct file type");
            }
        } catch (IndexOutOfBoundsException outOfBoundsException) {
            outOfBoundsException.printStackTrace();
            logException("Error: " + outOfBoundsException.getMessage());
            alertPopUp("Error: File Read Failure. \nError Msg: " + outOfBoundsException.getMessage());
        } catch (NullPointerException nullPointerException){
            alertPopUp("Error: You did not chose a file");
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to index an arraylist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public void indexArrayList(){
        setMenuFalse();
        indexArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            indexSearch = new IndexSearch(new ArrayList<>(), exceptionLogger);
            indexSearch.initialize(file.toString());
            indexSearch.getOperationTime();
            this.list = indexSearch.allThatBeginWith(prefix);
            updateTimeRequired(indexSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to index a linkedlist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public void indexLinkedList(){
        setMenuFalse();
        indexLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            indexSearch = new IndexSearch(new LinkedList<>(), exceptionLogger);
            indexSearch.initialize(file.toString());
            indexSearch.getOperationTime();
            this.list = indexSearch.allThatBeginWith(prefix);
            updateTimeRequired(indexSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to iterate an arraylist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public void iterateArrayList(){
        setMenuFalse();
        iterateArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            foreachSearch = new ForeachSearch(new ArrayList<>(), exceptionLogger);
            foreachSearch.initialize(file.toString());
            foreachSearch.getOperationTime();
            this.list = foreachSearch.allThatBeginWith(prefix);
            updateTimeRequired(foreachSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to iterate a linkedlist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public void iterateLinkedList(){
        setMenuFalse();
        iterateLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            foreachSearch = new ForeachSearch(new LinkedList<>(), exceptionLogger);
            foreachSearch.initialize(file.toString());
            foreachSearch.getOperationTime();
            this.list = foreachSearch.allThatBeginWith(prefix);
            updateTimeRequired(foreachSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to use
     * a parallel stream to sort through an arraylist. This method will also note the time
     * the operation took and update the UI
     */
    @FXML
    public void parallelStreamArrayList(){
        setMenuFalse();
        parallelStreamArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            parallelStreamSearch = new ParallelStreamSearch(new ArrayList<>(), exceptionLogger);
            parallelStreamSearch.initialize(file.toString());
            parallelStreamSearch.getOperationTime();
            this.list = parallelStreamSearch.allThatBeginWith(prefix);
            updateTimeRequired(parallelStreamSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to use
     * a parallel stream to sort through an linkedlist. This method will also note the time
     * the operation took and update the UI.
     */
    @FXML
    public void parallelStreamLinkedList(){
        setMenuFalse();
        parallelStreamLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        if(!prefix.equals("")) {
            parallelStreamSearch = new ParallelStreamSearch(new LinkedList<>(), exceptionLogger);
            parallelStreamSearch.initialize(file.toString());
            parallelStreamSearch.getOperationTime();
            this.list = parallelStreamSearch.allThatBeginWith(prefix);
            updateTimeRequired(parallelStreamSearch.getOperationTime());
            updateMatches(this.list);
        } else {
            clearUI();
        }
    }

    /**
     * This method is ran when someone starts typing and it calls the appropriate strategy
     * for autocompleting the query
     */
    @FXML
    public void runSelectedSearch(){ //this is ran when someone starts typing
        try {
            if (indexArrayList.isSelected()) {
                indexArrayList();
            } else if (indexLinkedList.isSelected()) {
                indexLinkedList();
            } else if (iterateArrayList.isSelected()) {
                iterateArrayList();
            } else if (iterateLinkedList.isSelected()) {
                iterateLinkedList();
            } else if (parallelStreamArrayList.isSelected()) {
                parallelStreamArrayList();
            } else if (parallelStreamLinkedList.isSelected()) {
                parallelStreamLinkedList();
            }
        } catch(NoSuchElementException noElement){
            logException("Error: " + noElement.getMessage());
            alertPopUp("Error: " + noElement.getMessage());
        }
    }

    private void setMenuFalse(){
        indexArrayList.setSelected(false);
        indexLinkedList.setSelected(false);
        iterateArrayList.setSelected(false);
        iterateLinkedList.setSelected(false);
        parallelStreamArrayList.setSelected(false);
        parallelStreamLinkedList.setSelected(false);
    }

    private void alertPopUp(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Popup");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void logException(String message){
        exceptionLogger.severe("Caused by: " + this.getClass().getSimpleName() + ". " + message);
    }

    private void updateMatches(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : list) {
            stringBuilder.append(line + "\n");
        }
        matches.setText(stringBuilder.toString());
        updateAmountMatches(list.size());
    }
    private void updateAmountMatches(int amountMatches){
        this.amountMatches.setText("Matches found: " + amountMatches);
    }

    /**
     * A time is passed in and this formats it correctly and submits it to the UI
     * @param timeReq time that a calculation took in nanoseconds
     */
    private void updateTimeRequired(long timeReq){
        final int MILLI_OFFSET = 3;
        final int MICRO_OFFSET = 6;
        final int NANO_OFFSET = 9;
        final int PICO_OFFSET = 12;
        final int SECONDS_PER_MINUTE = 60;
        final int SCIENTIFIC_NOTATION_VALUE = 10;

        int minutes = 0;
        int seconds = 0;
        int milliseconds = 0;
        int microseconds = 0;
        int nanoseconds = 0;

        String label;
        String formattedTime = "";

        double time = timeReq / Math.pow(SCIENTIFIC_NOTATION_VALUE, NANO_OFFSET); //time in seconds

        minutes = (int)(time/SECONDS_PER_MINUTE);
        time = time % SECONDS_PER_MINUTE;

        seconds = (int)(time);
        time = time % 1;

        milliseconds = (int)(time * Math.pow(SCIENTIFIC_NOTATION_VALUE, MILLI_OFFSET));
        time = (time) % (1 / Math.pow(SCIENTIFIC_NOTATION_VALUE, MILLI_OFFSET));

        microseconds = (int)(time * Math.pow(SCIENTIFIC_NOTATION_VALUE, MICRO_OFFSET));
        time = (time) % (1 / Math.pow(SCIENTIFIC_NOTATION_VALUE, MICRO_OFFSET));

        nanoseconds = (int)(time * Math.pow(SCIENTIFIC_NOTATION_VALUE, NANO_OFFSET));
        time = (time) % (1 / Math.pow(SCIENTIFIC_NOTATION_VALUE, NANO_OFFSET));

        if(minutes > 0 || seconds > 0){
            label = "";
            formattedTime = String.format("%02d:%02d.%.3s", minutes, seconds, milliseconds);
        } else if(milliseconds > 0 && seconds < 1){
            label = " milliseconds";
            formattedTime = String.format("%03d.%.3s", milliseconds, microseconds);
        } else if(microseconds > 1 && milliseconds < 1){
            label = " microseconds";
            formattedTime = String.format("%03d.%.3s", microseconds, nanoseconds);
        } else {
            label = " nanoseconds";
            formattedTime = String.format("%03d.%03d", nanoseconds,
                    (int)(time*Math.pow(SCIENTIFIC_NOTATION_VALUE, PICO_OFFSET)));
        }
        timeRequired.setText("Time required: " + formattedTime + label);
    }

    private void clearUI(){
        updateMatches(new ArrayList<>());
        updateTimeRequired(0);
    }
}
