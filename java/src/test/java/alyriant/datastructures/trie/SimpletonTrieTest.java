package alyriant.datastructures.trie;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpletonTrieTest {
    @Test
    public void testContainsPrefix() throws Exception {
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
    public void testContains() throws Exception {
        SimpletonTrie s = new SimpletonTrie();
        s.add("Officer");

        assertTrue(s.contains("Officer"));

        assertFalse(s.contains("Office"));
        assertFalse(s.contains("Officers"));
    }

    @Test
    public void testRemove() throws Exception {
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