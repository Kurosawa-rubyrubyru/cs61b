import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByFive = new OffByN(5);

    // Your tests go here.
//    Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'A'));
        assertFalse(offByOne.equalChars('a', 'E'));
    }

    @Test
    public void testOffByN() {
        assertTrue(offByFive.equalChars('a', 'f'));
        assertTrue(offByFive.equalChars('f', 'a'));
        assertFalse(offByFive.equalChars('a', 'e'));
        assertFalse(offByFive.equalChars('e', 'a'));
    }
}
