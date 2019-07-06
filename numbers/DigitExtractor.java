/**
 * Extracts digits from other representations.
 */
public final class DigitExtractor {
    private static final int RADIX = 10;

    /**
     * Tries to convert a String representation of a number to
     * an array of digits in that number.
     * @param number a string representation of the number
     * @return an array of digits of the number
     * @throws NumberFormatException if the number contains
     * non-numeric data
     */
    public static int[] getDigits(final String number)
            throws NumberParseException {
        final String digits = number.trim();
        if (digits.chars().allMatch(Character::isDigit)) {
            return digits.chars()
                    .map(Character::getNumericValue)
                    .toArray();
        }
        throw new NumberFormatException(
                "Number string should consist solely of integers");
    }

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
     * Returns an array of digits of a specified number.
     * @param number the target number
     * @param length a length of a target array
     * @return an array of digits of the number
     */
    public static int[] getDigits(final int number, final int length) {
        final int[] result = new int[length];
        for (int i = length - 1, temp = number; i >= 0; i--) {
            result[i] = temp % RADIX;
            temp /= RADIX;
        }
        return result;
    }
    
    /**
     * Restricts instantiation.
     */
    private DigitExtractor() {
        throw new AssertionError();
    }
}
