/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import ua.yandex.shad.tries.Trie;
import java.util.LinkedList;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.RWayTrie;

public class PrefixMatches {

    public static final int MIN_LEN = 3;
    public static final int K = 3;
    private Trie trie;

    public PrefixMatches() {
        this.trie = new RWayTrie();
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

    public LinkedList<String> wordsWithPrefix(String pref) {
        return this.wordsWithPrefix(pref, K);
    }

    public LinkedList<String> wordsWithPrefix(String pref, int k) {
        LinkedList<String> resWords = new LinkedList<>();
        if (pref.length() >= 3) {
            LinkedList<String> allWords = (LinkedList) trie.wordsWithPrefix(pref);
            int maxLength = 0;
            for (String word : allWords) {
                if (word.length() > maxLength) {
                    maxLength = word.length();
                }
            }
            int[] addedLength = new int[maxLength + 1];
            int countOfAddedLength = 0;
            for (String word : allWords) {
                if (addedLength[word.length()] == 1) {
                    resWords.add(word);
                } else if (countOfAddedLength < k) {
                    countOfAddedLength++;
                    addedLength[word.length()] = 1;
                    resWords.add(word);
                }
            }
        }
        return resWords;
    }

    public int size() {
        return trie.size();
    }
}
