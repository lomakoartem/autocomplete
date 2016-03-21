import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that contain in-memory dictionary and manipulate with it. Dictionary is
 * implemented as RWayTrie.
 *
 * @author Lomako Artem
 * @version %I%, %G%
 * @since 1.0
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    /**
     * Create in-memory dictionary of words, gets as argument word, string,
     * array of string/words. If string comes it will be split by space. There
     * are only words that longer 2 symbols will be added to dictionary.
     * Punctuation is not available.
     *
     * @param strings
     *            word, array of words, string, array of strings
     * @return number of actually added words
     */
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

    /**
     * If the word is available in the dictionary current method returns true,
     * else - false;
     *
     * @param word
     *            word to search
     * @return true - if trie contain this word, else - false
     */
    public boolean contains(String word) {
        return trie.contains(word);
    }

    /**
     * Delete word from trie-dictionary
     *
     * @param word
     *            word to delete
     * @return successfulness of operation
     */
    public boolean delete(String word) {
        return trie.delete(word);
    }

    /**
     * Return the size of trie-dictionary
     *
     * @return size
     */
    public int size() {
        return trie.size();
    }

    /**
     * Method that return k-different words length from the minimum pref only if
     * pref is longer than one symbol
     *
     * @param pref
     *            pref to search
     * @param k
     *            k-different words
     * @return list of words
     */
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

    /**
     * Method that return k-different words length from the minimum pref only if
     * pref is longer than one symbol and k = 3
     *
     * @param pref
     *            pref to search
     * @return list of words
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }

    /**
     * Method that return false if length of input prefix less then condition
     *
     * @param pref
     *            pref to search
     * @return list of words
     */
    private boolean prefToSmall(String pref) {
        if (pref.length() < 1) {
            System.out.println("The size of input prefix is small ");
            return true;
        }
        return false;
    }

}


