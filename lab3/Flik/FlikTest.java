import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void testIsSameNumber() {
        int a = 0;
        int b = 0;
        assertTrue(Flik.isSameNumber(a, b));
        a = 25;
        b = 25;
        assertTrue(Flik.isSameNumber(a, b));
        b = 30;
        assertFalse(Flik.isSameNumber(a, b));
        a = 129;
        b = 129;
        assertTrue(Flik.isSameNumber(a, b));
    }
}
