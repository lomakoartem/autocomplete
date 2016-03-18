import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Artem_Lomako on 3/15/2016.
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int add(String... strings) {
        int wordCount = trie.size();
        for (String str : strings) {
            System.out.println(str);
            str = str.trim();
            String[] subWords = str.split("\\s+");
            for (String newStr : subWords) {
                if (newStr.length() > 2 && !trie.contains(newStr)) {
                    trie.add(new Tuple(newStr, newStr.length()));
                }
            }
        }
        return trie.size() - wordCount;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public int size() {
        return trie.size();
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (prefToSmall(pref)) {
            return Collections.EMPTY_LIST;
        }
        return () -> new Iterator<String>() {
            int currLength = 0;
            int counter = 0;
            Iterator<String> iter = trie.wordsWithPrefix(pref).iterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext() && counter <= k;
            }

            @Override
            public String next() {
                String str = (String) iter.next();
                if (currLength != str.length()) {
                    currLength = str.length();
                    counter++;
                }
                while (iter.hasNext() && counter <= k) {
                    return str;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }

    private boolean prefToSmall(String pref) {
        if (pref.length() < 1) {
            System.out.println("The size of input prefix is small ");
            return true;
        }
        return false;
    }

}


