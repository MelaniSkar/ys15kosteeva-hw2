package ua.yandex.shad.autocomplete;
import org.junit.Test;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class PrefixMatchesTest {
    @Test
    public void testLoadIntWhichReturns() {
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 5;
        int result = prefixMatches.load("abc abcd abce abcde abcdef");
        assertEquals(expResult,result);
    }
    
    @Test
    public void testLoadWithSmallWords() {
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 4;
        int result = prefixMatches.load("ab abcd abce abcde abcdef");
        assertEquals(expResult,result);
    }
    
    @Test
    public void testLoadOnCorrectAdd() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("abc abcd abce abcde abcdef");
        boolean expResult = true;
        boolean result = (prefixMatches.contains("abc") && prefixMatches.contains("abcd") && prefixMatches.contains("abce"));
        assertEquals(expResult,result);
    }
    
    @Test
    public void testLoadOnCorrectAddWithSmallWords() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("abc abcd ab abcde abcdef");
        boolean expResult = false;
        boolean result = (prefixMatches.contains("ab") && prefixMatches.contains("abcd") && prefixMatches.contains("abce"));
        assertEquals(expResult,result);
    }
    
    @Test
    public void testWordsWithPrefixAndStandartK() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("abc abcd abce abcde abcdef  abcgjfns  abckdrrgfghtr");
        LinkedList<String> expList = new LinkedList<>();
        LinkedList<String> resList  = prefixMatches.wordsWithPrefix("abc");
        expList.add("abc");
        expList.add("abcd");
        expList.add("abce");
        expList.add("abcde");
        boolean expResult = true;
        boolean result = (expList.containsAll(resList) && resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("abc abcd abce abcde abcdef  abcgjfns  abckdrrgfghtr");
        LinkedList<String> expList = new LinkedList<>();
        LinkedList<String> resList  = prefixMatches.wordsWithPrefix("abc", 6);
        expList.add("abc");
        expList.add("abcd");
        expList.add("abce");
        expList.add("abcde");
        expList.add("abcdef");
        expList.add("abcgjfns");
        expList.add("abckdrrgfghtr");
        boolean expResult = true;
        boolean result = (expList.containsAll(resList) && resList.containsAll(expList));
        assertEquals(expResult, result);
    }
    @Test
    public void testSizeWithNoWordsInString() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("                 ");
        int expResult = 0;
        int result = prefixMatches.size();
        assertEquals(expResult, result);
    }
}