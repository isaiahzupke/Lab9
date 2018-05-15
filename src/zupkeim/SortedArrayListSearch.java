package zupkeim;

import java.util.List;
import java.util.logging.Logger;

public class SortedArrayListSearch implements AutoCompleter{

    private static Logger logger;
    private ParseFile parseFile = new ParseFile();
    private List<String> list;
    private long startTime = 0;
    private long endTime = 0;

    public SortedArrayListSearch(List<String> list, Logger logger){
        this.list = list;
        this.logger = logger;
    }

    /**
     * This initializes the file to be read by the specific strategy that is going to be auto completing
     *
     * @param filename the name of the file that is to be opened
     */
    @Override
    public void initialize(String filename) {
        //build the list sorted so that it is readable by binary search.
        parseFile.parseFile(filename, list, logger);
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
        return null;
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
}
