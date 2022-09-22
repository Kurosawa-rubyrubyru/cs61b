/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // tODO: Implement LSD Sort
        int lengthmax = 0;
        int length = asciis.length;
        for (String s : asciis) {
            lengthmax = Math.max(lengthmax, s.length());
        }
        int[] counts = new int[257];
        int[] pos = new int[257];
        int newpos;
        String[] sorted = new String[length];
        String[] newstring = new String[length];
        for (int i = length - 1; i >= 0; i -= 1) {
            newstring[i] = asciis[i];
        }
        for (int i = length - 1; i >= 0; i -= 1) {
            for (String s : newstring) {
                if (i < s.length()) {
                    counts[(s.charAt(i)) + 1] += 1;
                } else {
                    counts[0] += 1;
                }

            }
            pos[0] = 0;
            for (int j = 1; j < 257; j += 1) {
                pos[j] = pos[j - 1] + counts[j - 1];
            }
            for (String s : newstring) {
                if (i < s.length()) {
                    newpos = s.charAt(i) + 1;
                } else {
                    newpos = 0;
                }
                sorted[pos[newpos]] = s;
                pos[newpos] = pos[newpos] + 1;

            }
            for (int j = 0; j < length; j += 1) {
                newstring[j] = sorted[j];
            }
            pos = new int[257];
            counts = new int[257];
        }

        return sorted;


    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

//    public static void main(String[] args) {
//        String[] tester = {"a", "b", "aaa", "bbb", "aba", "bab", "abb", "baa", "bba", "aab"};
//        sort(tester);
//    }
}
