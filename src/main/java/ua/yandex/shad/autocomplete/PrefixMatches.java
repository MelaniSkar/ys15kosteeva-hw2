/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.RWayTrie;

public class PrefixMatches {

    public static final int MIN_LEN = 2;
    public static final int K = 3;
    private final Trie trie;

    public PrefixMatches() {
        this.trie = new RWayTrie();
    }

    public static class Iter implements Iterator<String> {
        
        private String prefix;
        private int countOfDifferentLength;
        private int currentLength;
        private int numberOfDifLength;
        

        public Iter() {
            countOfDifferentLength = 0;
            currentLength = 0;
            numberOfDifLength = K;
            prefix = "";
        }

        public Iter(int k, String pref) {
            countOfDifferentLength = 0;
            currentLength = 0;
            numberOfDifLength = k;
            prefix = pref;
        }

        @Override
        public boolean hasNext() {
            if (countOfDifferentLength < numberOfDifLength) {
                return trie.wordsWithPrefix(prefix)
                        .iterator()
                        .hasNext();
            } else {
                return false;
            }
        }

        @Override
        public String next() {
            if (hasNext()) {
                String next = trie.wordsWithPrefix(prefix)
                        .iterator()
                        .next();
                if (currentLength != next.length()) {
                    countOfDifferentLength++;
                    currentLength = next.length();
                }
                return next;
            } else {
                throw new NoSuchElementException();
            }

        }
    }

    public static class Iterate implements Iterable<String> {

        private Iter iter;

        public Iterate() {
            iter = new Iter();
        }

        public Iterate(int k, String pref) {
            iter = new Iter(k, pref);
        }

        @Override
        public Iterator<String> iterator() {

            return iter;

        }
    ;

    }

    public int load(String... strings) {
        String[] str;
        for (int i = 0; i < strings.length; i++) {
            str = strings[i].split(" ");
            for (int j = 0; j < str.length; j++) {
                if (str[j].length() > 2) {
                    trie.add(new Tuple(str[j], str[j].length()));
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return new Iterate(K, pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return new Iterate(k, pref);

    }

    public int size() {
        return trie.size();
    }
}
