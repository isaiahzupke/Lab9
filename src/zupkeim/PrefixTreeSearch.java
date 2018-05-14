package zupkeim;

import java.util.HashMap;
import java.util.List;

public class PrefixTreeSearch implements AutoCompleter {

    private TrieNode root;


    /*
    * Do a dynamically sized array to store the letters rather than a fixed size. Init time may be longer but search time will be short enough that it could make up for it (just use this in a situation where it is
    * always running. otherwise, we could do a fixed size array to store characters where memory is not limited for quicker insert time. Engineers could decide the most efficient ways to implement this
    * data structure depending on what they need it for. Maybe build both of them for a demonstration. Could do a method that allows it to intelligently decide what would be msot efficient depending on the amount
    * of words that will be added.
    *
    * THIS WILL BE THE FIXED SIZED ARRAY TRIENODE
     */
    private class TrieNode {
        TrieNode root;
        TrieNode[] chars = new TrieNode[39]; //39 is the size used for the array
        String content;
        boolean isWord;

        TrieNode(){
            root = new TrieNode();
        }

        void insert(TrieNode node){
            TrieNode start = node;
        }
    }

    public PrefixTreeSearch(){
        root = new TrieNode();
    }

    /**
     * This initializes the file to be read by the specific strategy that is going to be auto completing
     *
     * @param filename the name of the file that is to be opened
     */
    @Override
    public void initialize(String filename) {

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
        return 0;
    }


}
