package ua.yandex.shad.tries;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RWayTrie implements Trie {

    private static final int ALFABET_SIZE = 26;
    private final Node root;

    public RWayTrie() {
        root = new Node();
    }

    public Node getRoot() {
        return root;
    }

    private static class DynamicArray<Type> {

        private Type[] elements;
        private int length;

        public DynamicArray() {
        }

        public Type get(int number) {
            return elements[number];
        }

        public int add(Type... strings) {
            int lengthAfterAdd = length;
            int stringsLength = strings.length;
            int numberOfElements = length + stringsLength;
            if (lengthAfterAdd == 0) {
                //this.elements = (Type[]) new Object[stringsLength];
                lengthAfterAdd = stringsLength;
            } else {
                while (lengthAfterAdd < numberOfElements) {
                    lengthAfterAdd *= 2;
                }
            }

            Type[] resultMas = (Type[]) new Object[lengthAfterAdd];
            for (int i = 0; i < length; i++) {
                resultMas[i] = this.elements[i];
            }

            for (int i = length; i < numberOfElements; i++) {
                resultMas[i] = strings[i - length];
            }

            this.elements = resultMas;
            this.length = numberOfElements;

            return numberOfElements;
        }
    }

    public static class Node {

        private final Node[] next;
        private int weight;

        public Node() {
            next = new Node[ALFABET_SIZE];
            weight = 0;
        }
    }

    public static class RWayBfsIterator implements Iterator<String> {

        private DynamicArray<Node> currentNodes;
        private DynamicArray<String> currentStrings;
        private int lastIndex;
        private String prefix;

        public RWayBfsIterator(Node startNode, String prefix) {
            this.prefix = prefix;
            Node currentNode = startNode;
            this.currentNodes = new DynamicArray<>();
            this.currentStrings = new DynamicArray<>();
            if (prefix != null) {
                for (int i = 0; i < prefix.length(); i++) {
                    currentNode = currentNode.next[prefix.charAt(i) - 'a'];
                }
                currentStrings.add(prefix);
            } else {
                currentStrings.add("");
            }
            currentNodes.add(currentNode);
            this.lastIndex = -1;
        }

        private void formNextLength() {
            DynamicArray<Node> nextNodeLay = new DynamicArray<>();
            DynamicArray<String> nextStringLay = new DynamicArray<>();
            for (int i = 0; i < currentNodes.length; i++) {
                Node currentNode = currentNodes.get(i);
                String currentString = currentStrings.get(i);
                for (int j = 0; j < ALFABET_SIZE; j++) {
                    if (currentNode.next[j] != null) {
                        nextNodeLay.add(currentNode.next[j]);
                        nextStringLay.add(currentString
                                + (char) (j + 'a'));
                    }
                }
            }
            currentNodes = nextNodeLay;
            currentStrings = nextStringLay;
            lastIndex = -1;
        }

        @Override
        public boolean hasNext() {
            if (prefix != null && prefix.length() < 2) {
                return false;
            }
            while (currentNodes.length > 0) {
                while (lastIndex + 1 < currentNodes.length) {
                    if (currentNodes.get(lastIndex + 1).weight != 0) {
                        return true;
                    } else {
                        lastIndex++;
                    }
                }
                formNextLength();
            }
            return false;
        }

        @Override
        public String next() {
            if (hasNext()) {
                lastIndex++;
                return currentStrings.get(lastIndex);

            } else {
                throw new NoSuchElementException();
            }
        }

    }

    public static class RWayBfsIterable implements Iterable<String> {

        private RWayBfsIterator iterator;

        public RWayBfsIterable(Node startNode, String prefix) {
            iterator = new RWayBfsIterator(startNode, prefix);
        }

        @Override
        public Iterator<String> iterator() {
            return iterator;
        }

    }

    @Override
    public void add(Tuple t) {
        String term = t.getTerm();
        Node rootToWorkWith = root;
        int i;
        for (i = 0; i < t.getWeight(); i++) {
            if (rootToWorkWith.next[term.charAt(i) - 'a'] == null) {
                rootToWorkWith.next[term.charAt(i) - 'a'] = new Node();
            }
            rootToWorkWith = rootToWorkWith.next[term.charAt(i) - 'a'];
        }
        rootToWorkWith.weight = t.getWeight();
    }

    @Override
    public boolean contains(String word) {
        int i;
        Node rootToWorkWith = root;
        for (i = 0; i < word.length(); i++) {
            if (rootToWorkWith.next[word.charAt(i) - 'a'] == null) {
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

    @Override
    public Iterable<String> words() {
        return new RWayBfsIterable(root, null);
    }

    @Override

    public Iterable<String> wordsWithPrefix(String s) {
        return new RWayBfsIterable(root, s);
    }

    private int size(Node node) {
        int result = 0;

        if (node.weight != 0) {
            result++;
        }
        for (int i = 0; i < ALFABET_SIZE; i++) {
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
