class AlgoSortedSquares {
    public int[] sortedSquares(final int[] a) {
        final int len = a.length;
        if (len == 1) return new int[] {a[0] * a[0]};
        
        final int[] res = new int[len];
        final int pivotIdx = getPivotIndex(a);
        if (pivotIdx == -1) {
            copySquared(a, res);
            return res;
        }
        
        for (int i = 0, b = pivotIdx - 1, f = pivotIdx; i < len; i++) {
            if (b < 0) {
                copySquared(a, res, f, i);
                return res;
            }
            if (f >= len) {
                copySquaredRev(a, res, b, i);
                return res;
            }

            int backAbs = Math.abs(a[b]);
            int front = a[f];
            if (backAbs <= front) {
                res[i] = backAbs * backAbs;
                b--;
            } else {
                res[i] = front * front;
                f++;
            }
        }
        return res;
    }
    
    private void copySquared(final int[] src, final int[] dst, int p, int c) {
        for (; c < dst.length && p < src.length; p++, c++) {
            final int n = src[p];
            dst[c] = n * n;
        }
    }
    
    private void copySquared(final int[] src, final int[] dst) {
        copySquared(src, dst, 0, 0);
    }
    
    private void copySquaredRev(final int[] src, final int[] dst, int p, int c) {
        for (; p >= 0 && c < dst.length && p < src.length; p--, c++) {
            final int n = src[p];
            dst[c] = n * n;
        }
    }
    
    private int getPivotIndex(final int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        int res = -1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            if (a[mid] >= 0) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return res;
    }
}
