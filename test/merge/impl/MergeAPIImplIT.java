/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merge.impl;

import merge.impl.IntervalImpl;
import merge.impl.MergeAPIImpl;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mgxa2d
 */
public class MergeAPIImplIT {

    /**
     * Test of getSortedSet method, of class MergeAPIImpl.
     */
    @Test
    public void testGetSortedSet() {
        System.out.println("testGetSortedSet");
        assertEquals(0, _testIs("").size());
        assertEquals(1, _testIs("[1,2]").size());
        assertEquals(3, _testIs("[3,4] [1,2]").last().getMin());
        assertEquals(2, _testIs("[3,4] [1,2]").size());
        assertEquals(3, _testIs("[3,4] [1,2]").last().getMin());
        assertEquals(2, _testIs("[11,12] [3,4] [1,2]").first().getMax());
    }

    /**
     *
     * @param intervallString
     * @return {@link TreeSet#IntervalImpl}
     */
    private TreeSet<IntervalImpl> _testIs(String intervallString) {
        System.out.println(String.format("_testIs(%s)", intervallString));
        MergeAPIImpl instance = new MergeAPIImpl();
        return instance.getSortedSet(intervallString);
    }

    /**
     * Test of merge method, of class MergeAPIImpl.
     */
    @Test
    public void testMerge() {
        System.out.println("testMerge");
        assertEquals(MergeAPIImpl.PARSE_ERROR, _testMerge("asdf"));
        assertEquals(MergeAPIImpl.PARSE_ERROR, _testMerge("1,2"));
        assertEquals(MergeAPIImpl.PARSE_ERROR, _testMerge("42"));
        assertEquals("", _testMerge(""));
        assertEquals("[1,2]", _testMerge("[1,2]"));
        assertEquals("[1,2] [3,4]", _testMerge("[1,2] [3,4]"));
        assertEquals("[1,4]", _testMerge("[1,2] [1,4]"));
        assertEquals("[1,3]", _testMerge("[1,2] [2,3]"));
        assertEquals("[1,5] [11,12]", _testMerge("[1,2] [1,3] [11,12] [1,4] [2,5]"));
    }

    /**
     *
     * @param intervallString
     * @return
     */
    String _testMerge(String intervallString) {
        System.out.println(String.format("_testMerge(%s)", intervallString));
        MergeAPIImpl instance = new MergeAPIImpl();
        return instance.merge(intervallString);
    }

    /**
     * Test of toString method, of class MergeAPIImpl.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        MergeAPIImpl instance = new MergeAPIImpl();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
