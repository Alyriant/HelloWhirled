package alyriant.datastructures.trie;

/**
 * A too-simple trie for Strings, supporting add, containsPrefix, contains, and
 * remove. No Unicode support, only ASCII characters supported. No null checks.
 * <p>
 * This implementation uses an ArrayList of Character for traversal, which is
 * not terribly efficient.
 */
public class SimpletonTrie {
    /**
     * Adds a string to the trie, if it doesn't already exist. Case-sensitive.
     */
    public void add(String s) {
        if (root == null) {
            root = new Node();
            root.children = new Node[ARRAY_SIZE];
        }

        char[] characters = s.toCharArray();

        Node current = root;

        for (char c : characters) {
            if (current.children == null) {
                current.children = new Node[ARRAY_SIZE];
            }
            if (current.children[(int) c] == null) {
                current.children[(int) c] = new Node();
            }
            current = current.children[(int) c];
        }

        if (current.children == null) {
            current.children = new Node[ARRAY_SIZE];
        }

        if (current.children[0] == null) {
            current.children[0] = new Node(); // end of word marker
        }
    }

    /**
     * Returns true if the string is an entire word in the trie, and not just a
     * prefix.
     */
    public boolean contains(String s) {
        Node n = nodeAtEndOfPrefix(s);
        return n != null && n.children != null && n.children[0] != null;
    }

    /**
     * Returns true if the string is in the trie, either as an entire word or as
     * a prefix of a word.
     */
    public boolean containsPrefix(String s) {
        return nodeAtEndOfPrefix(s) != null;
    }

    /**
     * Removes a string from the trie, if it exists.
     */
    public void remove(String s) {
        Node n = nodeAtEndOfPrefix(s);
        if (n != null && n.children != null && n.children[0] != null) {
            n.children[0] = null;
        }
    }

    //-----------------------------------------------------------------------------
    private final static int ARRAY_SIZE = 128;
    private Node root;

    /**
     * Returns the trie node corresponding to the last character of the string,
     * if the string is in the trie. Returns null if the string is not a prefix
     * in the trie.
     */
    private Node nodeAtEndOfPrefix(String s) {
        Node current = root;

        char[] characters = s.toCharArray();

        for (char c : characters) {
            if (current.children == null || current.children[(int) c] == null) {
                return null;
            }
            current = current.children[(int) c];
        }
        return current;
    }

    /**
     * Trie node is dumb data, with all logic in the outer class.
     */
    private class Node {
        Node[] children;
    }
}
