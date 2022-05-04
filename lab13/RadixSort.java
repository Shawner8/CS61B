/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int index = Integer.MIN_VALUE;
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            sorted[i] = asciis[i];
            index = Math.max(index, asciis[i].length());
        }
        for (index -= 1; index >= 0; index -= 1) {
            sortHelperLSD(sorted, index);
        }
        return sorted;
    }

    private static int getChar(String s, int index) {
        /*
        try {
            return s.charAt(index);
        } catch (Exception e) {
            return 0;
        }*/
        if (index >= 0 && index < s.length()) {
            return s.charAt(index);
        } else {
            return 0;
        }
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] counts = new int[256];
        for (String s : asciis) {
            int i = getChar(s, index);
            counts[i] += 1;
        }
        int[] starts = new int[256];
        for (int i = 1; i < 256; i += 1) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }

        String[] sorted = new String[asciis.length];
        for (String s : asciis) {
            int i = getChar(s, index);
            int place = starts[i];
            sorted[place] = s;
            starts[i] += 1;
        }

        System.arraycopy(sorted, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void printStrings(String[] strings) {
        for (String s : strings) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] asciis = new String[3];
        asciis[0] = "puppy";
        asciis[1] = "cat";
        asciis[2] = "dogs";
        RadixSort.printStrings(asciis);
        String[] sorted = RadixSort.sort(asciis);
        RadixSort.printStrings(asciis);
        RadixSort.printStrings(sorted);
    }
}
