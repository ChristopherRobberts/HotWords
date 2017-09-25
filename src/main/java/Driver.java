/**
 * Created by Chris on 2017-09-23.
 */
import edu.princeton.cs.introcs.In;
import java.net.URL;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {
        URL url = ClassLoader.getSystemResource("kap1.txt");

        if (url != null) {
            System.out.println("Reading from: " + url);
        } else {
            System.out.println("Couldn't find file: kap1.txt");
        }

        In input = new In(url);
        Trie t = new Trie();
        while (!input.isEmpty()) {
            String line = input.readLine().trim();
            String[] words = line.split("(\\. )|:|,|;|!|\\?|( - )|--|(\' )| ");
            String lastOfLine = words[words.length - 1];

            if (lastOfLine.endsWith(".")) {
                words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
            }

            for (String word : words) {
                //String word2 = word.replaceAll("\"|\\(|\\)", " ");
                String word2 = word.replaceAll("\\P{Print}", "");
                word2 = word2.replaceAll("\"|\\(|\\)", "");
                if (word2.isEmpty()) {
                    continue;
                }
                System.out.println(word2);
                t.put(word2);
            }
        }
        ArrayList<String> listOfWords = t.keysWithPrefix("");
        ArrayList<String> secondListOfWords = t.keysWithPrefix("");
        ArrayList<String> highest = new ArrayList<String>();
        ArrayList<String> lowest = new ArrayList<String>();
        highestFrequency(t, listOfWords, highest, 0);
        lowestFrequency(t, secondListOfWords, lowest, 0);

        System.out.print("Highest frequency words : ");
        for (int i = 0; i < highest.size(); i++) {
            System.out.print(highest.get(i) + ", ");
        }
        System.out.println();
        System.out.print("Lowest frequency words : ");
        for (int i = 0; i < lowest.size(); i++) {
            System.out.print(lowest.get(i) + ", ");
        }
        System.out.println();

        System.out.println("th=" + t.countSamePrefix("th"));
        t.counterReset();
        System.out.println("it=" + t.countSamePrefix("it"));
        t.counterReset();
        System.out.println("if=" + t.countSamePrefix("if"));
        t.counterReset();
        System.out.println("an=" + t.countSamePrefix("an"));
        t.counterReset();
        System.out.println();
        System.out.println("s : " + t.distinctKey("s"));
        t.distinctCounterReset();
        System.out.println("t : " + t.distinctKey("t"));
        t.distinctCounterReset();
        System.out.println("a : " + t.distinctKey("a"));
        t.distinctCounterReset();
        System.out.println("i : " + t.distinctKey("i"));
        t.distinctCounterReset();

    }

    private static ArrayList<String> highestFrequency(Trie t, ArrayList<String> s, ArrayList<String> lst, int counter) {
        if (counter == 10) {
            return lst;
        }
        int max = t.getVal(s.get(0));
        StringBuilder l = new StringBuilder(s.get(0));
        for (int i = 0; i < s.size(); i++) {
            if (max < t.getVal(s.get(i))) {
                max = t.getVal(s.get(i));
                l = new StringBuilder(s.get(i));
            }
        }
        StringBuilder str = new StringBuilder(l);
        lst.add(str.append(" " + max).toString());
        s.remove(l.toString());
        return highestFrequency(t, s, lst, counter + 1);
    }

    private static ArrayList<String> lowestFrequency(Trie t, ArrayList<String> s, ArrayList<String> lst, int counter) {
        if (counter == 10) {
            return lst;
        }
        int min = t.getVal(s.get(0));
        StringBuilder l = new StringBuilder(s.get(0));
        for (int i = 0; i < s.size(); i++) {
            if (min > t.getVal(s.get(i))) {
                min = t.getVal(s.get(i));
                l = new StringBuilder(s.get(i));
            }
        }
        StringBuilder str = new StringBuilder(l);
        lst.add(str.append(" " + min).toString());
        s.remove(l.toString());
        return lowestFrequency(t, s, lst, counter + 1);
    }
}