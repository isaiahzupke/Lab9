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

public class PrefixTreeSearch implements AutoCompleter {

    private static Logger logger;
    private Trie trie;
    private long startTime = 0;
    private long endTime = 0;

    public PrefixTreeSearch(){
        trie = new Trie();
    }

    /**
     * This initializes the file to be read by the specific strategy that is going to be auto completing
     *
     * @param filename the name of the file that is to be opened
     */
    @Override
    public void initialize(String filename) {
        if(filename.substring(filename.lastIndexOf(".")).equalsIgnoreCase(".csv")){
            String line = "";
            final String CSV_SPLIT = ",";
            try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
                while((line = in.readLine()) != null){
                    String[] domain = line.split(CSV_SPLIT);
                    trie.insert(domain[1]);
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
                    trie.insert(in.nextLine());
                }
            } catch (IOException ioException){
                logException("Error: " + ioException.getMessage());
                alertPopUp("Error: " + ioException.getMessage());
            }
        }
    }

    /**
     * This is the brain of the auto completing function. A driver sends this
     * method a prefix and it returns results
     * where the first character is the same.
     *
     * @param prefix the character to compare
     * @return a list of strings that all have the same first character
     */
    @Override
    public List<String> allThatBeginWith(String prefix) {
        return trie.getAll(prefix);
    }

    /**
     * this will return the operation time difference from the current
     * call to the method and the last call to this method
     *
     * @return operation time in nano seconds
     */
    @Override
    public long getOperationTime() {
        startTime = endTime;
        endTime = System.nanoTime();
        return endTime - startTime;
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
