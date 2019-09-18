package merge.impl;

import merge.impl.IntervalImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mgxa2d
 */
public class IntervalImplIT {

    /**
     * Test of intersects method, of class IntervalImpl.
     */
    @Test
    public void testIntersects_IntervalImpl() {
        assertFalse(_testIntersect("[2,1]", "[1,2]"));
        assertTrue(_testIntersect("[1,2]", "[1,2]"));
        assertFalse(_testIntersect("[1,2]", "[3,4]"));
        assertFalse(_testIntersect("[3,4]", "[1,2]"));
        assertTrue(_testIntersect("[1,10]", "[2,9]"));
        assertTrue(_testIntersect("[1,10]", "[9,10]"));
        assertTrue(_testIntersect("[1,10]", "[9,11]"));
        assertTrue(_testIntersect("[2,10]", "[1,11]"));
        assertTrue(_testIntersect("[2,10]", "[1,3]"));
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    boolean _testIntersect(String a, String b) {
        System.out.println(String.format("_testIntersect(%s,%s)", a,b));
        return new IntervalImpl(a).intersects(new IntervalImpl(b));
    }

    /**
     * Test of merge method, of class IntervalImpl.
     */
    @Test
    public void testMerge_IntervalImpl() {
        System.out.println("testMerge_IntervalImpl");
        assertEquals("[1,3]", _testMerge("[1,2]", "[2,3]"));
        assertEquals("[10,20]", _testMerge("[10,20]", "[11,19]"));
        assertEquals("[10,20]", _testMerge("[10,20]", "[10,20]"));
        assertEquals("[10,25]", _testMerge("[10,20]", "[15,25]"));
        assertEquals("[5,20]", _testMerge("[10,20]", "[5,15]"));
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    String _testMerge(String a, String b) {
        System.out.println(String.format("_testMerge(%s,%s)", a,b));
        IntervalImpl ia = new IntervalImpl(a);
        return ia.merge(new IntervalImpl(b)).toString();
    }

    /**
     * Test of toString method, of class IntervalImpl.
     */
    @Test
    public void testToString() {
        System.out.println("testToString");
        assertEquals("[1,2]", new IntervalImpl("[1,2]").toString());
        assertEquals("[1,20]", new IntervalImpl("[1,20]").toString());
    }

    /**
     * Test of getMin method, of class IntervalImpl.
     */
    @Test
    public void testGetMin() {
        System.out.println("testGetMin");
        assertEquals(2, new IntervalImpl("[2,5]").getMin());
        assertEquals(3, new IntervalImpl("[3,5]").getMin());
    }

    /**
     * Test of getMax method, of class IntervalImpl.
     */
    @Test
    public void testGetMax() {
        System.out.println("testGetMax");
        assertEquals(5, new IntervalImpl("[2,5]").getMax());
        assertEquals(6, new IntervalImpl("[3,6]").getMax());
    }
}
