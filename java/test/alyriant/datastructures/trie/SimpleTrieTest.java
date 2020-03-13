package alyriant.datastructures.trie;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleTrieTest {
    @Test
    public void testAdd() {
        SimpleTrie s = new SimpleTrie();
        assertEquals(0, s.getSize());
        s.add("Hello Whirled");
        assertEquals(1, s.getSize());
    }

    @Test
    public void testAddDuplicate() {
        SimpleTrie s = new SimpleTrie();
        s.add("Hello Whirled");
        s.add("Hello Whirled");
        assertEquals(1, s.getSize());
    }

    @Test
    public void testAddDuplicateCaseInsensitive() {
        SimpleTrie s = new SimpleTrie();
        s.add("office");
        s.add("OFFICE");
        assertEquals(1, s.getSize());
    }

    @Test
    public void testAddDuplicateNormalized() {
        SimpleTrie s = new SimpleTrie();
        s.add("oﬀice");
        assertEquals(1, s.getSize());
        s.add("office");
        assertEquals(1, s.getSize());
    }

    @Test
    public void testAddFullOverlap() {
        SimpleTrie s = new SimpleTrie();
        s.add("Office");
        s.add("Off");
        s.add("Officer");
        assertEquals(3, s.getSize());
    }

    @Test
    public void testAddNoOverlap() {
        SimpleTrie s = new SimpleTrie();
        for (Character c = 'a'; c <= 'z'; c++) {
            s.add(c.toString());
        }
        assertEquals(26, s.getSize());
    }

    @Test
    public void testAddPartialOverlap() {
        SimpleTrie s = new SimpleTrie();
        s.add("Office");
        s.add("Offer");
        assertEquals(2, s.getSize());
    }

    @Test
    public void testAddWithEmpty() {
        SimpleTrie s = new SimpleTrie();
        s.add("");
        assertEquals(0, s.getSize());
    }

    @Test
    public void testAddWithNull() {
        SimpleTrie s = new SimpleTrie();
        s.add(null);
        assertEquals(0, s.getSize());
    }

    @Test
    public void testContains() {
        SimpleTrie s = new SimpleTrie();
        s.add("Officer");

        assertTrue(s.contains("OFFICER"));

        assertFalse(s.contains("Office"));
        assertFalse(s.contains("Officers"));
    }

    @Test
    public void testContainsPrefix() {
        SimpleTrie s = new SimpleTrie();
        s.add("Office");
        s.add("OFFICER");

        assertTrue(s.containsPrefix("Off"));
        assertTrue(s.containsPrefix("Office"));
        assertTrue(s.containsPrefix("Officer"));

        assertFalse(s.containsPrefix("Officers"));
        assertFalse(s.containsPrefix("Offer"));
    }

    @Test
    public void testNonNegativeIntToSignedByte() {
        assertEquals(-128, (int) SimpleTrie.nonNegativeIntToSignedByte(128));
        assertEquals(-1, (int) SimpleTrie.nonNegativeIntToSignedByte(255));
    }

    @Test
    public void testPrintKeys() {
        // For simplicity, just checking that it doesn't throw an exception
        SimpleTrie s = new SimpleTrie();
        s.add("Officer");
        s.add("Officers");
        s.add("Yo");
        s.printKeys();
    }

    @Test
    public void testRemove() {
        SimpleTrie s = new SimpleTrie();
        s.add("office");
        s.add("OFFICER");

        assertEquals(2, s.getSize());

        s.remove("Office");
        assertEquals(1, s.getSize());
        assertFalse(s.contains("Office"));
        assertTrue(s.contains("Officer"));

        s.remove("Officer");
        assertEquals(0, s.getSize());
        assertFalse(s.contains("Officer"));

        s.remove(null);
        s.remove("");
        s.remove("Not present");
        assertEquals(0, s.getSize());
    }

    @Test
    public void testSignedByteToNonNegativeInt() {
        assertEquals(128, SimpleTrie.signedByteToNonNegativeInt((byte) -128));
        assertEquals(255, SimpleTrie.signedByteToNonNegativeInt((byte) -1));
    }

    @Test
    public void testToNormalizedByteArray() {
        byte[] bytes =
            SimpleTrie.toNormalizedByteArray(LATIN_CAPITAL_LIGATURE_IJ);
        byte[] expected = {'i', 'j'};
        assertArrayEquals(expected, bytes);
    }

    @Test
    public void testToNormalizedByteArrayWithNull() {
        assertNull(SimpleTrie.toNormalizedByteArray(null));
    }

    @Test
    public void testUtf8ToString() {
        assertEquals(EURO_SIGN, SimpleTrie.utf8ToString(EURO_SIGN_UTF8));
    }

    @Test
    public void testUtf8ToStringWithNull() {
        assertNull(SimpleTrie.utf8ToString(null));
    }

    //-----------------------------------------------------------------------------
    private static final String EURO_SIGN = "\u20AC"; // €

    private static final ArrayList<Byte> EURO_SIGN_UTF8
        = new ArrayList<>(Arrays.asList((byte) 0xE2, (byte) 0x82, (byte) 0xAC));

    private static final String LATIN_CAPITAL_LIGATURE_IJ = "\u0132"; // "Ĳ";
}