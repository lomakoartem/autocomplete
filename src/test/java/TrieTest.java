/**
 * Created by Artem_Lomako on 3/18/2016.
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {

    Trie trie = new RWayTrie();

    @Test
    public void testContains() {
        trie.add(new Tuple("qwe", "qwe".length()));
        assertTrue(trie.contains("qwe"));
    }

    @Test
    public void testSize() {
        trie.add(new Tuple("qwe", "qwe".length()));
        assertEquals(trie.size(), 1);
    }

    @Test
    public void testDelete() {
        trie.add(new Tuple("qwe", "qwe".length()));
        trie.delete("qwe");
        assertFalse(trie.contains("qwe"));
    }

}