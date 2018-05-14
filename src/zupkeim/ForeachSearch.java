/*
 Course: CS2852 021
 Term: Spring 2018
 Assignment: Lab 4: Autocomplete
 Author: Isaiah Zupke
 Date: 04/08/2018
 */
package zupkeim;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class does is used by the AutoCompleteController to return results from a list that have the
 * same first character as a designated word. This class uses an enhanced loop to sort through a list
 * and returns results via a list.
 */
public class ForeachSearch implements AutoCompleter {
    private static Logger logger;
    private ParseFile parseFile = new ParseFile();
    private List<String> list;
    private long startTime = 0;
    private long endTime = 0;

    /**
     * constructor
     * @param list list that user wants the words copied to
     * @param logger an exception logger
     */
    public ForeachSearch(List<String> list, Logger logger){
        this.list = list;
        this.logger = logger;
    }

    /**
     * This initializes the file to be read by the specific strategy that is going to be auto completing
     * @param filename the name of the file that is to be opened
     */
    @Override
    public void initialize(String filename) {
        parseFile.parseFile(filename, list, logger); //is the "list =" necessary or would the changes happen directly to the list as a product of the method call.
    }

    /**
     * This is the brain of the auto completing function. A driver sends this method a prefix and it returns results
     * where the first character is the same.
     * @param prefix the character to compare
     * @return a list of strings that all have the same first character
     */
    @Override
    public List<String> allThatBeginWith(String prefix) {
        List<String> returnList = new ArrayList<>();
        for(String line : list){
            if(line.startsWith(prefix)){
                returnList.add(line);
            }
        }
        return returnList;
    }

    /**
     * this will return the operation time difference from the current call to the method and the last call to this method
     * @return operation time in nano seconds
     */
    @Override
    public long getOperationTime() {
        startTime = endTime;
        endTime = System.nanoTime();
        return endTime - startTime;
    }
}