/**
 * Created by Artem_Lomako on 3/18/2016.
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class RWayTrie implements Trie {

    private static final int ALPHABET_SIZE = 26;

    private static final char ALPHABET_SHIFT = 'a';

    private Node root;

    private int size;

    private static class Node {
        private int weight;
        private final Node[] next = new Node[ALPHABET_SIZE];
    }

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

    public int get(String word) {
        Node x = get(root, word, 0);
        if (x == null) {
            return 0;
        }
        return x.weight;
    }

    @Override
    public boolean contains(String word) {
        return get(word) != 0;
    }

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


    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

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

    @Override
    public int size() {
        return size;
    }

}