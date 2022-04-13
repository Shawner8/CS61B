import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> buggy = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> gold = new ArrayDequeSolution<>();
        String callSequence = "\n";
        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (gold.size() > 0) {
                if (numberBetweenZeroAndOne < 0.25) {
                    callSequence += String.format("addFirst(%d)\n", i);
                    buggy.addFirst(i);
                    gold.addFirst(i);
                } else if (numberBetweenZeroAndOne < 0.5) {
                    callSequence += String.format("addLast(%d)\n", i);
                    buggy.addLast(i);
                    gold.addLast(i);
                } else if (numberBetweenZeroAndOne < 0.75) {
                    callSequence += "removeFirst()\n";
                    Integer b = buggy.removeFirst();
                    Integer g = gold.removeFirst();
                    assertEquals(callSequence, g, b);
                } else {
                    callSequence += "removeLast()\n";
                    Integer b = buggy.removeLast();
                    Integer g = gold.removeLast();
                    assertEquals(callSequence, g, b);
                }
            } else {
                callSequence += "removeFirst()\n";
                assertNull(callSequence, buggy.removeFirst());
                callSequence += "removeLast()\n";
                assertNull(callSequence, buggy.removeLast());
                if (numberBetweenZeroAndOne < 0.5) {
                    callSequence += String.format("addFirst(%d)\n", i);
                    buggy.addFirst(i);
                    gold.addFirst(i);
                } else {
                    callSequence += String.format("addLast(%d)\n", i);
                    buggy.addLast(i);
                    gold.addLast(i);
                }
            }
        }
    }
}
