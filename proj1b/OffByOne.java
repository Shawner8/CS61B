/** A class for off-by-1 comparators. */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (Math.abs(diff) == 1);
    }
}
