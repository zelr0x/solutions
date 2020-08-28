import java.util.*;

class AlgoMergeSort {

    public static<T extends Comparable<? super T>> List<T> sort(List<T> list) {
        if (list == null) return null;

        final int len = list.size();
        if (len <= 1) return list;

        final int mid = len / 2;
        return merge(sort(list.subList(0, mid)), sort(list.subList(mid, len)));
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> a, List<T> b) {
        final int aLen = a.size();
        final int bLen = b.size();
        final List<T> res = new ArrayList<>(aLen + bLen);
        int i = 0;
        int j = 0;
        while (i < aLen && j < bLen) {
            T fromA = a.get(i);
            T fromB = b.get(j);
            if (fromA.compareTo(fromB) > 0) {
                res.add(fromB);
                j++;
            } else {
                res.add(fromA);
                i++;
            }
        }
        final int remainingIdx;
        final List<T> remaining;
        if (i == aLen) {
            remainingIdx = j;
            remaining = b;
        } else {
            remainingIdx = i;
            remaining = a;
        }
        res.addAll(remaining.subList(remainingIdx, remaining.size()));
        return res;
    }

    public static void main(String[] args) {
        sortOdd_Works();
        sortEven_Works();
        sortEmpty_Works();
        sortNull_ReturnsNull();
        System.out.println("All tests passed");
    }

    public static void sortOdd_Works() {
        var a = List.of(5, 2, 1, 8, 4, 7, 3, -1, 6);
        a = sort(a);
        assert a.equals(List.of(-1, 1, 2, 3, 4, 5, 6, 7, 8));
    }

    public static void sortEven_Works() {
        var a = List.of(5, 2, 1, 8, 4, 7, 3, -1);
        a = sort(a);
        assert a.equals(List.of(-1, 1, 2, 3, 4, 5, 7, 8));
    }

    public static void sortEmpty_Works() {
        var a = List.<Integer>of();
        a = sort(a);
        assert a.equals(List.<Integer>of());
    }

    /** @noinspection ConstantConditions*/
    public static void sortNull_ReturnsNull() {
        assert sort(null) == null;
    }
}
