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
    public void testAdd_VerifyAdd() {
        test.add("test");
        verify(trie).add(any(Tuple.class));
    }
    @Test
    public void prefixMatchesShoudInvokeTrieContainsMethodOnce() {
        test.contains("test");
        verify(trie, times(1)).contains(any(String.class));
    }

    @Test
    public void prefixMatchesShouldInvokeTrieDeleteMethodOnceTime() {
        test.delete("test");
        verify(trie, times(1)).delete("test");
    }

    @Test
    public void prefixMatchesShouldInvokeTrieSizeMethodOnceTime() {
       test.size();
        verify(trie, times(1)).size();
    }


    @Test
    public void prefixMatchesShouldInvokeTrieAddMethod5Times() {
        test.add("test test test test test");
        verify(trie, times(5)).add(any(Tuple.class));
    }


}
    
    
    