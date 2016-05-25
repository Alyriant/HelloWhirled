package alyriant.datastructures.trie;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * A simple trie for Strings, supporting add, containsPrefix, contains, and
 * remove. To support Unicode efficiently, strings are first converted to UTF-8
 * internally, and then a traditional byte array approach is used for storage.
 * Keys are converted to lowercase, and the string is converted to Unicode
 * Normalization Form KC (NFKC): Compatibility Decomposition, followed by
 * Canonical Composition. This converts, for example, the ligature ﬀ into the
 * characters ff, so "Oﬀ" and "Off" will be the same key, "off".
 * <p>
 * This implementation uses an ArrayList of Byte for traversal, which is not
 * terribly efficient.
 */
public class SimpleTrie {
    /**
     * Adds a string to the trie, if it doesn't already exist. Keys are
     * converted to lowercase. Unicode is supported, with a normalized form to
     * avoid inadvertent duplicate keys.
     */
    public void add(String s) {
        if (s == null || s.isEmpty()) {
            return;
        }

        if (root == null) {
            root = new Node();
            root.children = new Node[ARRAY_SIZE];
        }

        byte[] utf8Bytes = toNormalizedByteArray(s);

        if (utf8Bytes == null) {
            return;
        }

        Node current = root;

        for (byte b : utf8Bytes) {
            int byteAsIndex = signedByteToNonNegativeInt(b);

            if (current.children == null) {
                current.children = new Node[ARRAY_SIZE];
            }
            if (current.children[byteAsIndex] == null) {
                current.children[byteAsIndex] = new Node();
            }
            current = current.children[byteAsIndex];
        }

        if (current.children == null) {
            current.children = new Node[ARRAY_SIZE];
        }

        if (current.children[0] == null) {
            current.children[0] = new Node(); // end of word marker
            ++size;
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
     * Returns the number of elements in the trie.
     */
    public int getSize() {
        return size;
    }

    /**
     * Recursively prints every key in the trie.
     */
    public void printKeys() {
        System.out.println("Keys of " + this.toString());

        ArrayList<Byte> utf8Bytes = new ArrayList<>();
        for (int i = 1; i < ARRAY_SIZE; i++) {
            if (root.children[i] != null) {
                utf8Bytes.add(nonNegativeIntToSignedByte(i));
                printKeys(utf8Bytes, root.children[i]);
                utf8Bytes.clear();
            }
        }
    }

    /**
     * Removes a string from the trie, if it exists.
     */
    public void remove(String s) {
        Node n = nodeAtEndOfPrefix(s);
        if (n != null && n.children != null && n.children[0] != null) {
            n.children[0] = null;
            --size;
        }
    }

    //-----------------------------------------------------------------------------
    private final static int ARRAY_SIZE = 256;
    private Node root;
    private int size = 0;

    /**
     * Converts a non-negative integer (0 to 255) to a signed byte -128 to 127.
     * Useful when converting an array index to a byte value.
     */
    static byte nonNegativeIntToSignedByte(int i) {
        return (byte) i;
    }

    /**
     * Converts a byte (which is a signed number from -128 to 127) to the
     * equivalent non-negative integer (0 to 255). Useful when using the byte
     * value as an array index.
     */
    static int signedByteToNonNegativeInt(byte b) {
        return b & 0xff;
    }

    /**
     * Converts a string to a Unicode normalized, lowercase byte array in UTF8
     * format.
     */
    static byte[] toNormalizedByteArray(String s) {
        if (s == null) {
            return null;
        }

        byte[] utf8Bytes;
        try {
            String normalized = Normalizer.isNormalized(s, Normalizer.Form.NFKC)
                ? s
                : Normalizer.normalize(s, Normalizer.Form.NFKC);
            utf8Bytes = normalized.toLowerCase().getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return utf8Bytes;
    }

    /**
     * Converts an ArrayList of bytes in UTF8 format to a String.
     */
    static String utf8ToString(ArrayList<Byte> utf8Bytes) {
        if (utf8Bytes == null) {
            return null;
        }
        byte[] bytes = new byte[utf8Bytes.size()];
        for (int i = 0; i < utf8Bytes.size(); i++) {
            bytes[i] = utf8Bytes.get(i);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Returns the trie node corresponding to the last character of the string,
     * if the string is in the trie. Returns null if the string is not a prefix
     * in the trie.
     */
    private Node nodeAtEndOfPrefix(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        Node current = root;
        byte[] utf8bytes = toNormalizedByteArray(s);

        for (final byte utf8byte : utf8bytes) {
            int index = signedByteToNonNegativeInt(utf8byte);
            if (current.children == null || current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }

    /**
     * Helper for the public printKeys(). Prints the current key if this node
     * has one, then prints the keys of any words that this is a prefix of.
     *
     * @param utf8Bytes The characters encountered so far while traversing down
     * @param current   The current node in the trie traversal
     */
    private void printKeys(ArrayList<Byte> utf8Bytes, Node current) {
        if (current.children[0] != null) {
            String key = utf8ToString(utf8Bytes);
            System.out.println(key);
        }
        for (int i = 1; i < ARRAY_SIZE; i++) {
            if (current.children[i] != null) {
                utf8Bytes.add(nonNegativeIntToSignedByte(i));
                printKeys(utf8Bytes, current.children[i]);
                utf8Bytes.remove((utf8Bytes.size() - 1));
            }
        }
    }

    /**
     * Trie node is dumb data, with all logic in the outer class.
     */
    private class Node {
        Node[] children;
    }
}
