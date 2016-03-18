/**
 * Created by Artem_Lomako on 3/18/2016.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

    @Mock
    Trie trie;

    @InjectMocks
    PrefixMatches test;
    @Test
    public void testSize() {
        test.size();
        verify(trie).size();
    }


    @Test
    public void testAdd() {
        test.add("test");
        verify(trie).contains("test");
    }

    @Test
    public void testContains() {
        test.contains("test");
        verify(trie).contains("test");
    }

    @Test
    public void testDelete() {
        test.delete("test");
        verify(trie).delete("test");
    }


    @Test
    public void testAdd_VerifyAdd() {
        test.add("test");
        verify(trie).add(any(Tuple.class));
    }


}
    
    
    