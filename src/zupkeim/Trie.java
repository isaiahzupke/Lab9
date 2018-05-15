package zupkeim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private static TrieNode root;
    /*
     * Do a dynamically sized array to store the letters rather than a fixed size. Init time may be longer but search time will be short enough that it could make up for it (just use this in a situation where it is
     * always running. otherwise, we could do a fixed size array to store characters where memory is not limited for quicker insert time. Engineers could decide the most efficient ways to implement this
     * data structure depending on what they need it for. Maybe build both of them for a demonstration. Could do a method that allows it to intelligently decide what would be msot efficient depending on the amount
     * of words that will be added.
     *
     * THIS WILL BE THE FIXED SIZED ARRAY TRIENODE
     */
    private static class TrieNode {
        HashMap<Character, TrieNode> map;
        boolean isWord;
        String content;

        TrieNode(){
            map = new HashMap<>();
        }
    }

    public Trie(){
        root = new TrieNode();
    }

    public static void insert(String word){
        TrieNode currentNode = root;
        for(int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if(currentNode != null && currentNode.map.containsKey(currentChar)){
                currentNode = currentNode.map.get(currentChar);
            } else {
                TrieNode temp = new TrieNode();
                currentNode.map.put(currentChar, temp);
                currentNode = temp;
            }
        }
        currentNode.isWord = true;
        currentNode.content = word;
    }

    public static List<String> getAll(String word){
        TrieNode currentNode = root;
        for(int i = 0; i < word.length(); i++){
            currentNode = currentNode.map.get(word.charAt(i));
        }
        return getAll(currentNode, new ArrayList<>());

    }

    private static ArrayList<String> getAll(TrieNode currentNode, ArrayList list){
        if(currentNode != null){
            if(currentNode.isWord){
                list.add(currentNode.content);
            }
            for(TrieNode trieNode : currentNode.map.values()){
                getAll(trieNode, list);
            }
        }
        return list;
    }


//    public void insert(String word){
//        insert(word, root);
//    }
//
//    private void insert(String word, TrieNode currentNode){
//        if(word.length() > 0){
//            if(currentNode==null){
//
//            } else {
//
//            }
//        } else { //assign letter to current node
//
//        }
//    }


}
