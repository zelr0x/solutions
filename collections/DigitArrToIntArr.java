/**
 * Only getDigits() method matters here.
 */
public final class DigitArrToIntArr {
    /**
     * Returns array of all items in digits converted to int
     * or throws an exception.
     * @param digits digit array
     * @return digit (String) array as array of int
     * @throws NumberFormatException if the number contains
     * non-numeric data
     */
    public static int[] getDigits(final String[] digits)
            throws NumberFormatException {
        return Arrays.stream(digits)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    
    /**
     * Restricts instantiation.
     */
    private DigitArrToIntArr() {
        throw new AssertionError();
    }
}
