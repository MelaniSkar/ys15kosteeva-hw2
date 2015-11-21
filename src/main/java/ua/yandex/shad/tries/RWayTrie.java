package ua.yandex.shad.tries;

import java.util.LinkedList;

public class RWayTrie implements Trie {

    public static final int SIZE = 26;
    private Node root = new Node();

    private static class Node {

        private Node[] next;
        private int weight;

        public Node() {
            next = new Node[SIZE];
            weight = 0;
        }

        public Node(int w) {
            weight = w;
        }
    }

    @Override
    public void add(Tuple t) {
        String term = t.getTerm();
        Node rootToWorkWith = root;
        int i;
        //System.out.println(t.getWeight());
        for (i = 0; i < t.getWeight(); i++) {
            if (rootToWorkWith.next[term.charAt(i) - 'a'] == null) {
                rootToWorkWith.next[term.charAt(i) - 'a'] = new Node();
                //System.out.println(i);
            }
            rootToWorkWith = rootToWorkWith.next[term.charAt(i) - 'a'];
        }
        //System.out.println(i);
        rootToWorkWith.weight = t.getWeight();
    }

    @Override
    public boolean contains(String word) {
        int i;
        Node rootToWorkWith = root;
        for (i = 0; i < word.length(); i++) {
            if (rootToWorkWith.next[word.charAt(i) - 'a'] == null) {
                //System.out.println(i);
                return false;
            } else {
                rootToWorkWith = rootToWorkWith.next[word.charAt(i) - 'a'];
            }
        }
        return rootToWorkWith.weight == word.length();
    }

    @Override
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        } else {
            int i;
            Node rootToWorkWith = root;
            for (i = 0; i < word.length(); i++) {
                rootToWorkWith = rootToWorkWith.next[word.charAt(i) - 'a'];
            }
            rootToWorkWith.weight = 0;
            return true;
        }

    }

    private LinkedList<String> allWordsWithPrefix(Node node, String prefix) {
        LinkedList<String> res = new LinkedList<>();
        LinkedList<Node> currentNodes = new LinkedList<>();
        LinkedList<String> currentStrings = new LinkedList<>();
        Node currentNode = node;
        String currentString;
        if (prefix != null) {
            for (int i = 0; i < prefix.length(); i++) {
                currentNode = currentNode.next[prefix.charAt(i) - 'a'];
            }
            currentStrings.addLast(prefix);
        } else {
            currentStrings.addLast("");
        }
        currentNodes.addLast(currentNode);
        while (!currentNodes.isEmpty()) {
            currentNode = currentNodes.pollFirst();
            currentString = currentStrings.pollFirst();
            if (currentNode.weight != 0) {
                res.add(currentString);
            }
            for (int i = 0; i < SIZE; i++) {
                if (currentNode.next[i] != null) {
                    currentNodes.add(currentNode.next[i]);
                    currentStrings.add(currentString + (char) (i + 'a'));
                }
            }
        }

        return res;
    }

    @Override
    public LinkedList<String> words() {
        return allWordsWithPrefix(root, "");

    }

    @Override
    public LinkedList<String> wordsWithPrefix(String s) {
        return allWordsWithPrefix(root, s);
    }

    private int size(Node node) {
        int result = 0;

        if (node.weight != 0) {
            result++;
        }
        for (int i = 0; i < SIZE; i++) {
            if (node.next[i] != null) {
                result += size(node.next[i]);
            }
        }

        return result;
    }

    @Override
    public int size() {
        return size(root);
    }

}
