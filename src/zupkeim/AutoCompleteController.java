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
    private static boolean debugMode = false;

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
    @FXML
    private CheckMenuItem prefixTreeSearchTrie;
    @FXML
    private CheckMenuItem sortedArrayList;

    private ParallelStreamSearch parallelStreamSearch;
    private IndexSearch indexSearch;
    private ForeachSearch foreachSearch;
    private PrefixTreeSearch prefixTreeSearch;
    private SortedArrayListSearch sortedArrayListSearch;
    private File file;
    private List<String> list;

    private boolean benchmarkAll = false;
    private boolean benchmarkNewAndArrayList = false;

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
                String fileName = input.toString().substring(input.toString().lastIndexOf("\\"));
                if(fileName.equals("words.txt")){
                    benchmarkAll = true;
                } else if(fileName.equals("top-1m.csv")){
                    benchmarkNewAndArrayList = true;
                }
            } else {
                alertPopUp("Please select a correct file type", true);
            }
        } catch (IndexOutOfBoundsException outOfBoundsException) {
            outOfBoundsException.printStackTrace();
            logException("Error: " + outOfBoundsException.getMessage());
            alertPopUp("Error: File Read Failure. \nError Msg: " + outOfBoundsException.getMessage(), true);
        } catch (NullPointerException nullPointerException){
            alertPopUp("Error: You did not chose a file", true);
        }
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to index an arraylist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public String indexArrayList(){
        final String METHOD_NAME = "indexArrayList(): ";
        setMenuFalse();
        indexArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            indexSearch = new IndexSearch(new ArrayList<>(), exceptionLogger);
            indexSearch.initialize(file.toString());
            indexSearch.getOperationTime();
            this.list = indexSearch.allThatBeginWith(prefix);
            time = indexSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to index a linkedlist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public String indexLinkedList(){
        final String METHOD_NAME = "indexLinkedList(): ";
        setMenuFalse();
        indexLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            indexSearch = new IndexSearch(new LinkedList<>(), exceptionLogger);
            indexSearch.initialize(file.toString());
            indexSearch.getOperationTime();
            this.list = indexSearch.allThatBeginWith(prefix);
            time = indexSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to iterate an arraylist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public String iterateArrayList(){
        final String METHOD_NAME = "iterateArrayList(): ";
        setMenuFalse();
        iterateArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            foreachSearch = new ForeachSearch(new ArrayList<>(), exceptionLogger);
            foreachSearch.initialize(file.toString());
            foreachSearch.getOperationTime();
            this.list = foreachSearch.allThatBeginWith(prefix);
            time = foreachSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to iterate a linkedlist.
     * This method will also note the time the operation took and update the UI
     */
    @FXML
    public String iterateLinkedList(){
        final String METHOD_NAME = "iterateLinkedList(): ";
        setMenuFalse();
        iterateLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            foreachSearch = new ForeachSearch(new LinkedList<>(), exceptionLogger);
            foreachSearch.initialize(file.toString());
            foreachSearch.getOperationTime();
            this.list = foreachSearch.allThatBeginWith(prefix);
            time = foreachSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to use
     * a parallel stream to sort through an arraylist. This method will also note the time
     * the operation took and update the UI
     */
    @FXML
    public String parallelStreamArrayList(){
        final String METHOD_NAME = "parallelStreamArrayList(): ";
        setMenuFalse();
        parallelStreamArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            parallelStreamSearch = new ParallelStreamSearch(new ArrayList<>(), exceptionLogger);
            parallelStreamSearch.initialize(file.toString());
            parallelStreamSearch.getOperationTime();
            this.list = parallelStreamSearch.allThatBeginWith(prefix);
            time = parallelStreamSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is called by a checkMenuItem and will call the appropriate methods to use
     * a parallel stream to sort through an linkedlist. This method will also note the time
     * the operation took and update the UI.
     */
    @FXML
    public String parallelStreamLinkedList(){
        final String METHOD_NAME = "parallelStreamLinkedList(): ";
        setMenuFalse();
        parallelStreamLinkedList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")) {
            parallelStreamSearch = new ParallelStreamSearch(new LinkedList<>(), exceptionLogger);
            parallelStreamSearch.initialize(file.toString());
            parallelStreamSearch.getOperationTime();
            this.list = parallelStreamSearch.allThatBeginWith(prefix);
            time = parallelStreamSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This is called by a checkMenuItem and will call the appropriate methods to sort
     * through a trie (prefix tree). This method will also note the time the operation
     * took in the UI.
     */
    @FXML
    public String prefixTreeSearch(){
        final String METHOD_NAME = "prefixTreeSearch(): ";
        setMenuFalse();
        prefixTreeSearchTrie.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")){
            prefixTreeSearch = new PrefixTreeSearch();
            prefixTreeSearch.initialize(file.toString());
            prefixTreeSearch.getOperationTime();
            this.list = prefixTreeSearch.allThatBeginWith(prefix);
            time = prefixTreeSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    @FXML
    public String sortedArrayListSearch(){
        final String METHOD_NAME = "sortedArrayListSearch(): ";
        setMenuFalse();
        sortedArrayList.setSelected(true);
        String prefix = searchQuery.getText();
        long time = 0;
        if(!prefix.equals("")){
            sortedArrayListSearch = new SortedArrayListSearch(new ArrayList<>(), exceptionLogger);
            sortedArrayListSearch.initialize(file.toString());
            sortedArrayListSearch.getOperationTime();
            this.list = sortedArrayListSearch.allThatBeginWith(prefix);
            time = sortedArrayListSearch.getOperationTime();
            updateTimeRequired(time);
            updateMatches(this.list);
        } else {
            clearUI();
        }
        return METHOD_NAME + time;
    }

    /**
     * This method is ran when someone starts typing and it calls the appropriate strategy
     * for autocompleting the query
     */
    @FXML
    public void runSelectedSearch(){ //this is ran when someone starts typing
        try {
            if(benchmarkAll && searchQuery.getText().equals("f")){
                String benchmarkResults = "";
                benchmarkResults = indexArrayList() + "\n";
                benchmarkResults += indexLinkedList() + "\n";
                benchmarkResults += iterateArrayList() + "\n";
                benchmarkResults += iterateLinkedList() + "\n";
                benchmarkResults += parallelStreamArrayList() + "\n";
                benchmarkResults += parallelStreamLinkedList() + "\n";
                benchmarkResults += prefixTreeSearch() + "\n";
                benchmarkResults += sortedArrayListSearch();
                alertPopUp(benchmarkResults, false);
            } else if(benchmarkNewAndArrayList && searchQuery.getText().equals("v")){
                String benchmarkResults = "";
                benchmarkResults = indexArrayList() + "\n";
                benchmarkResults += iterateArrayList() + "\n";
                benchmarkResults += prefixTreeSearch() + "\n";
                benchmarkResults += sortedArrayListSearch();
                alertPopUp(benchmarkResults, false);
            }
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
            } else if (prefixTreeSearchTrie.isSelected()){
                prefixTreeSearch();
            } else if (sortedArrayList.isSelected()){
                sortedArrayListSearch();
            }
        } catch(NoSuchElementException noElement){
            logException("Error: " + noElement.getMessage());
            alertPopUp("Error: " + noElement.getMessage(), true);
        }
    }

    private void setMenuFalse(){
        indexArrayList.setSelected(false);
        indexLinkedList.setSelected(false);
        iterateArrayList.setSelected(false);
        iterateLinkedList.setSelected(false);
        parallelStreamArrayList.setSelected(false);
        parallelStreamLinkedList.setSelected(false);
        prefixTreeSearchTrie.setSelected(false);
        sortedArrayList.setSelected(false);
    }

    private void alertPopUp(String message, boolean isError){
        Alert alert = new Alert(isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle("Informational Popup");
        alert.setHeaderText("Notice");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void logException(String message){
        exceptionLogger.severe("Caused by: " + this.getClass().getSimpleName() + ". " + message);
    }

    private void updateMatches(List<String> list){
        if(!debugMode) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : list) {
                stringBuilder.append(line + "\n");
            }
            matches.setText(stringBuilder.toString());
            updateAmountMatches(list.size());
        }
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
