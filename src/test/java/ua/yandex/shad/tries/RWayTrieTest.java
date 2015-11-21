package ua.yandex.shad.tries;
import org.junit.Test;
import java.util.LinkedList;
import static org.junit.Assert.*;
public class RWayTrieTest {
    @Test 
    public void testContainsOneElement() {
        String word = "abc";
        int weight = 3;
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple(word, weight));
        boolean expResult = true;
        boolean result = rwayTrie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsFail() {
        String word = "abc";
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("cdo", 3));
        rwayTrie.add(new Tuple("cba", 3));
        rwayTrie.add(new Tuple("apcd", 4));
        boolean expResult = false;
        boolean result = rwayTrie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDeleteOnFalse() {
        String word = "abc";
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("djfsn",5));
        rwayTrie.add(new Tuple("sdijhf",6));
        boolean expResult = false;
        boolean result = rwayTrie.delete(word);
        assertEquals(expResult,result);
    }
    
    @Test
    public void testDeleteElemenOnTrue() {
        String word = "abc";
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("djfsn",5));
        rwayTrie.add(new Tuple("sdijhf",6));
        rwayTrie.add(new Tuple("abc",3));
        boolean expResult = true;
        boolean result = rwayTrie.delete(word);
        assertEquals(expResult,result);
    }
    
    @Test
    public void testContainsAfterDelete() {
        String word = "abc";
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("djfsn", 5));
        rwayTrie.add(new Tuple("sdijhf", 6));
        rwayTrie.add(new Tuple("abc", 3));
        rwayTrie.delete(word);
        boolean expResult = false;
        boolean result = rwayTrie.contains(word);
        assertEquals(expResult,result);
    }
    
    @Test
    public void testIterableOnContainsAllOfAdded() {
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("aaa", 3));
        rwayTrie.add(new Tuple("ara", 3));
        rwayTrie.add(new Tuple("aaua", 4));
        
        LinkedList<String> expList = new LinkedList<>();
        expList.add("aaa");
        expList.add("ara");
        expList.add("aaua");
        LinkedList<String> resList = rwayTrie.words();
        boolean expResult = true;
        boolean result = (expList.containsAll(resList)&&resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIterableOnContainsAllOfAddedWithPrefix() {
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("aaa", 3));
        rwayTrie.add(new Tuple("ara", 3));
        rwayTrie.add(new Tuple("aaua", 4));
        LinkedList<String> expList = new LinkedList<>();
        expList.add("ara");
        LinkedList<String> resList = rwayTrie.wordsWithPrefix("ar");
        boolean expResult = true;
        boolean result = (expList.containsAll(resList)&&resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    @Test
    public void testIterableWithNullPrefix() {
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("aaa", 3));
        rwayTrie.add(new Tuple("ara", 3));
        rwayTrie.add(new Tuple("aaua", 4));
        LinkedList<String> expList = new LinkedList<>();
        expList.add("aaa");
        expList.add("ara");
        expList.add("aaua");
        LinkedList<String> resList = rwayTrie.wordsWithPrefix(null);
        boolean expResult = true;
        boolean result = (expList.containsAll(resList)&&resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIterableExpextFalse() {
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("aaa", 3));
        
        rwayTrie.add(new Tuple("ara", 3));
        rwayTrie.add(new Tuple("aaua", 4));
        rwayTrie.add(new Tuple("aauau", 5));
        LinkedList<String> resList = new LinkedList<>();
        LinkedList<String> expList = new LinkedList<>();
        expList.add("aaa");
        expList.add("ara");
        expList.add("aaua");
        resList = rwayTrie.wordsWithPrefix("a");
        boolean expResult = false;
        boolean result = (expList.containsAll(resList)&&resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSize() {
        RWayTrie rwayTrie = new RWayTrie();
        rwayTrie.add(new Tuple("jfhs", 4));
        rwayTrie.add(new Tuple("jdfs", 4));
        rwayTrie.add(new Tuple("gfhs", 4));
        rwayTrie.add(new Tuple("jfts", 4));
        rwayTrie.add(new Tuple("jahs", 4));
        rwayTrie.add(new Tuple("tghgfhs", 7));
        int expResult = 6;
        int result = rwayTrie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSizeOfEmptyNode() {
        RWayTrie rwayTrie = new RWayTrie();
        int expResult = 0;
        int result = rwayTrie.size();
        assertEquals(expResult, result);
    }
}