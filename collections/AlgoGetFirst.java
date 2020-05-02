public class AlgoGetFirst {
    /**
     * @param n an uppder bound for int range to search in. Can be adapted to a collection.
     * @param pred a predicate to match.
     * @return the first item of n (a [0, n) range in the case of n being an int)
     * matching the specified predicate.
     */
    public static int getFirst(final int n, final Predicate<Integer> pred) {
        int lo = 0;
        int hi = n;
        int res = -1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            if (pred.test(mid)) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return res;
    }
}
