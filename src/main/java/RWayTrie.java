/**
 * Created by Artem_Lomako on 3/18/2016.
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Class RWayTrie is the implementation of Trie interface that represents
 * RWayTrie structure. This data structure supporting String keys, for fast
 * retrieval of values associated with string keys. In comparison to a Map, has
 * additional (fast) functions like list of keys with prefix and listing all
 * keys in sorted order.
 *
 * @author Artem Lomako
 * @version %I%, %G%
 * @since 1.0
 */
public class RWayTrie implements Trie {

    // Size of english alphabet
    private static final int ALPHABET_SIZE = 26;

    // First symbol of english alphabet
    private static final char ALPHABET_SHIFT = 'a';

    // root of trie
    private Node root;

    // R-way trie node
    private int size;

    /**
     * Static nested class to depict the idea of a Trie Node
     */

    private static class Node {
        private int weight;
        private final Node[] next = new Node[ALPHABET_SIZE];
    }


    /**
     * Constructor
     */
    public RWayTrie() {

    }


    @Override
    public void add(Tuple tuple) {
        if (tuple == null) {
            return;
        }
        if (tuple.getWord() == null) {
            delete(tuple.getWord());
        } else {
            root = add(root, tuple.getWord(), tuple.getWeight(), 0);
        }
    }

    /**
     * Method that add key-value to the trie. This is recursive method that
     * build sequence of nodes that has linked between each other
     *
     * @param x      node that contain pointer to next node and some value (may be null)
     * @param key    word that need to add into trie
     * @param weight value of current word
     * @param d      d symbol of key
     * @return last added node
     */

    private Node add(Node x, String key, int weight, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            if (x.weight == 0) {
                size++;
            }
            x.weight = weight;
            return x;
        }
        char c = (char) (key.charAt(d) - ALPHABET_SHIFT);
        x.next[c] = add(x.next[c], key, weight, d + 1);
        return x;
    }

    /**
     * Method that retrieve value of word
     *
     * @param word key to retrieve the some value of word
     * @return value of word
     */
    public int get(String word) {
        Node x = get(root, word, 0);
        if (x == null) {
            return 0;
        }
        return x.weight;
    }

    /**
     * Does this symbol table contain the given word?
     *
     * @param word - required word
     * @return <tt>true</tt> if this symbol table contains <tt>word</tt> and
     * <tt>false</tt> otherwise
     */
    @Override
    public boolean contains(String word) {
        return get(word) != 0;
    }

    /**
     * Method that retrieve value of word
     *
     * @param x    node that contain pointer to next node and some value (may be null)
     * @param word word that need to add into trie
     * @param d    d symbol of key
     * @return last added node
     */
    private Node get(Node x, String word, int d) {
        if (x == null || word == null) {
            return null;
        }
        if (d == word.length()) {
            return x;
        }
        char c = (char) (word.charAt(d) - ALPHABET_SHIFT);
        return get(x.next[c], word, d + 1);
    }


    @Override
    public boolean delete(String key) {
        if (key == null) {
            return false;
        }
        root = delete(root, key, 0);
        return root != null;
    }

    /**
     * Method that delete some word from RWayTrie
     *
     * @param x   node that contain pointer to next node and some value (may be null)
     * @param key word that need to add into trie
     * @param d   d symbol of key
     * @return last node
     */
    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            if (x.weight != 0) {
                size--;
            }
            x.weight = 0;
        } else {
            char c = (char) (key.charAt(d) - ALPHABET_SHIFT);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        if (x.weight != 0) {
            return x;
        }
        for (int c = 0; c < ALPHABET_SIZE; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    /**
     * Returns all words in the symbol table as an <tt>Iterable</tt>. To iterate
     * over all of the keys in the symbol table named <tt>st</tt>, use the
     * foreach notation: <tt>for (Key key : st.keys())</tt>.
     *
     * @return all keys in the sybol table as an <tt>Iterable</tt>
     */
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }


    /**
     * Returns iterator for trie that made BFS
     *
     * @param pref the prefix
     * @return iterator for trie that made BFS
     */
    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        return () -> new Iterator<String>() {
            private Node current = get(root, pref, 0);
            private Queue<Tuple> queue = new LinkedList<Tuple>() {
                {
                    if (current != null) {
                        add(new Tuple(pref, current.weight));
                    }
                }
            };

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public String next() {
                while (!queue.isEmpty()) {
                    Tuple t = queue.poll();
                    current.weight = t.getWeight();
                    StringBuilder str = new StringBuilder(t.getWord());
                    for (char c = 0; c < ALPHABET_SIZE; c++) {
                        int n = current.next[c].weight;
                        if (n != 0) {
                            queue.add(new Tuple(str.append((char) (c + ALPHABET_SHIFT)).toString(), n));
                            str.deleteCharAt(str.length() - 1);
                        }
                    }
                    if (current.weight != 0) {
                        return str.toString();
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    /**
     * Get size of trie
     *
     * @return size of trie
     */

    @Override
    public int size() {
        return size;
    }

}