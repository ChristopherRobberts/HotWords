import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Trie {
    private static final int ASCII_MAX = 256;
    private Node root = new Node();
    private int assCount = 0;
    private int distinctCount;
    private String biggest;

    private static class Node{
        private int value;
        private Node[] children = new Node[ASCII_MAX];
    }

    public Trie(){
    }
    public void put(String key){
        if (key == null)    //if the key doesn't have any characters return null.
            return;

        root = put(root, key, 0); //the root of the trie creates a new node
    }

    private Node put(Node x, String key, int d){
        if (x == null) {       //if the node doesn't exist, create a new one.
            x = new Node();
        }
        if (d == key.length()){ //our base case, after the recursive calls until the increasing value of d
            x.value++;          //reaches key length, then we stop calling and return our new created node.
            return x;           //When the key length is reached, we add + 1 to the node value, this helps us check how
        }                       //many times a certain key as been inserted to the Trie.
        char c = key.charAt(d);
        x.children[c] = put(x.children[c], key, d + 1); //recursive, inserts every char position of the key in the
        return x;                                           //children array.
    }

    public int getVal(String key){
        if (key == null)
            return 0;

        Node x = getVal(root, key, 0);   //x points to the root and follow the path were the key is put, it then
        if (x == null)                      //counts the value of that certain key if it exists, and it iterates the
            return 0;                       //x node the same as when we add a key, difference is we do not create a new
                                            //node since we are only checking for the count value, and checking if the
        return x.value;                     //key is in the trie. We return the amount of times this same sequence has
    }                                       //been added to the trie.

    private Node getVal(Node x, String key, int d){
        if (x == null)
            return null;

        if (d == key.length())
            return x;                               //Searches through the trie with the given key, d is the index
                                                    //of our key, and when it reaches the keys length we return the
        char c = key.charAt(d);                     //node and confirm the key is in our trie. We do this recursively
        return getVal(x.children[c], key, d + 1);//adding one to the d integer that iterates through the key,
    }                                               //and checking if it matches the iteration through node children.

    public int countSamePrefix(String prefix){
        if (prefix == null)
            return 0;
        Node x = getVal(root, prefix, 0);           //Here we count how many strings have the same prefix, and add
        prefixCollector(x, new StringBuilder(prefix)); //the value of all the times the string has been inserted in the
        return assCount;                               //trie. We add the values together and return it when the client
    }                                                  //searches for a certain prefix.

    private void prefixCollector(Node x, StringBuilder prefix){
        if (x == null)
            return;
        if (x.value != 0) {             //if the key exists, i.e value is not equal to 0, add that value to the
            assCount += x.value;        //association count.
        }
        for(char c = 0; c < ASCII_MAX; c++){           //append every character in the ascii table to the prefix given,
            prefix.append(c);                          //then recursively call back to collect to check if that sequence
            prefixCollector(x.children[c], prefix);    //exists in the trie, if it doesn't it returns null, if x.value
        }                                              //exists, we add that value to the assCount variable.
    }

    public int distinctKey(String prefix){
        if (prefix == null)
            return 0;
        Node x = getVal(root, prefix, 0);
        distinctCollector(x, new StringBuilder(prefix));
        return distinctCount;
    }

    private void distinctCollector(Node x, StringBuilder prefix){
        if (x == null)
            return;
        if (x.value != root.value) {                    //if the key exists, and if the x node value isn't equal to the
            distinctCount++;                            //root node value, increase the counter. This is because if the
        }                                               //value differs somewhere it means they have the same prefix but
        for(char c = 0; c < ASCII_MAX; c++){            //different postfix. The counter counts how many different
            prefix.append(c);                           //postfix one prefix has.
            distinctCollector(x.children[c], prefix);
        }
    }


    public ArrayList<String> keysWithPrefix(String prefix){
        ArrayList<String> lst = new ArrayList<String>();
        Node x = getVal(root, prefix, 0);
        collect(x, new StringBuilder(prefix), lst);
        return lst;
    }

    /*public ArrayList <Integer> keysWithPref(String prefix){
        ArrayList<Integer> lst = new ArrayList<Integer>();
        Node x = getVal(root, prefix, 0);
        collect2(x, new StringBuilder(prefix), lst);
        return lst;
    }*/

    private void collect(Node x, StringBuilder prefix, List<String> results){
        if (x == null)
            return;
        if (x.value != 0) {
            results.add(prefix.toString());             //append the value to the word.
        }
        for(char c = 0; c < ASCII_MAX; c++){            //we call recursively upon this method whilst appending a new
            prefix.append(c);                           //char to the prefix, if the pattern matches a previous key in
            collect(x.children[c], prefix, results);    //the trie, we add this appended prefix to our list.
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /*private void collect2(Node x, StringBuilder prefix, List<Integer> results){
        if (x == null)
            return;
        if (x.value != 0) {
            results.add(x.value);                       //append the value to the word.
        }
        for(char c = 0; c < ASCII_MAX; c++){            //we call recursively upon this method whilst appending a new
            prefix.append(c);                           //char to the prefix, if the pattern matches a previous key in
            collect2(x.children[c], prefix, results);    //the trie, we add this appended prefix to our list.
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }*/

    public void counterReset(){
        assCount = 0;
    }       //reset the assCount variable for new search.

    public void distinctCounterReset() { distinctCount = 0; }   //resets the distinct counter to 0.
}