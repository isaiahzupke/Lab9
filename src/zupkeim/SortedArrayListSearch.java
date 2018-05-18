package zupkeim;

import java.util.ArrayList;
import java.util.Collections;
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
        //sorted at end:
        //https://stackoverflow.com/questions/3607593/is-it-faster-to-add
        // -to-a-collection-then-sort-it-or-add-to-a-sorted-collection
        Collections.sort(list);
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
        ArrayList<String> returnList = new ArrayList<>();
        //need to do binary search to get first match, then continue until there are no more matches.
        int index = Math.abs((Collections.binarySearch(list, prefix)) - 1);//CHECK IF THIS VALUE IS NEGATIVE BECAUSE OTHERWISE YOU COULD ADD OR SUBTRACT IN THE WRONG DIRECTION WHEN YOU TAKE ABS VAL.
        //THIS THING BELOW IS CONSTANTLY ABOUT 3 off from what it should be.
        int endIndex = Math.abs((Collections.binarySearch(list, prefix.substring(0, prefix.length() - 1) + String.valueOf((char)((int)(prefix.substring(prefix.length()-1).toCharArray()[0]) + 1))) -1));
        System.out.println("START: " + index + " | END: " + endIndex);
        if(index >= 0){
            boolean reachedEndOfMatches = false;
            for(int i = index; !reachedEndOfMatches && (i < endIndex); i++){
                returnList.add(list.get(i));
            }
        }
        return returnList;
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
