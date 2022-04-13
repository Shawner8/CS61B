/** A class for off-by-N comparators. */
public class OffByN implements CharacterComparator {

    int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (Math.abs(diff) == N) {
            return true;
        } else {
            return false;
        }
    }
}
