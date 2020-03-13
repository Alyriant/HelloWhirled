package alyriant.datastructures.trie;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpletonTrieTest {
    @Test
    public void testContains() {
        SimpletonTrie s = new SimpletonTrie();
        s.add("Officer");

        assertTrue(s.contains("Officer"));

        assertFalse(s.contains("Office"));
        assertFalse(s.contains("Officers"));
    }

    @Test
    public void testContainsPrefix() {
        SimpletonTrie s = new SimpletonTrie();
        s.add("Office");
        s.add("Officer");

        assertTrue(s.containsPrefix("Off"));
        assertTrue(s.containsPrefix("Office"));
        assertTrue(s.containsPrefix("Officer"));

        assertFalse(s.containsPrefix("Officers"));
        assertFalse(s.containsPrefix("Offer"));
    }

    @Test
    public void testRemove() {
        SimpletonTrie s = new SimpletonTrie();
        s.add("Office");
        s.add("Officer");

        assertTrue(s.contains("Office"));
        assertTrue(s.contains("Officer"));

        s.remove("Office");
        assertFalse(s.contains("Office"));
        assertTrue(s.contains("Officer"));

        s.remove("Officer");
        assertFalse(s.contains("Officer"));
    }
}