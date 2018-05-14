/*
 Course: CS2852 021
 Term: Spring 2018
 Assignment: Lab 4: Autocomplete
 Author: Isaiah Zupke
 Date: 04/08/2018
 */
package zupkeim;

import java.util.List;

/**
 * Classes that implement this interface will be used for an auto completing program.
 * This interface sets the standard for how these programs should be written.
 */
public interface AutoCompleter {
    /**
     * This initializes the file to be read by the specific strategy that is going to be auto completing
     * @param filename the name of the file that is to be opened
     */
    void initialize(String filename);

    /**
     * This is the brain of the auto completing function. A driver sends this
     * method a prefix and it returns results
     * where the first character is the same.
     * @param prefix the character to compare
     * @return a list of strings that all have the same first character
     */
    List<String> allThatBeginWith(String prefix);

    /**
     * this will return the operation time difference from the current
     * call to the method and the last call to this method
     * @return operation time in nano seconds
     */
    long getOperationTime();
}
/*
COMMENT FOR GRADE:
I tested each of the strategies in the by running the same letter with the
three different files given to us. The smaller files is where the linked lists
showed their advantages. But as the amount of words increased, the indexed
linkedlist saw is weaknesses displayed very vibrantly (by the not responding program).
The iterated linkedlist held up much better than I would have expected.
The parallel streamed arraylist started to show its strength at the files got
larger. Though it didn't do better than I would have expected (actually worse),
it was still neat to see how the additional thread(s) benefited the program.
 */
