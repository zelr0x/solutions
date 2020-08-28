import java.util.Collections;
import java.util.List;
import java.util.Objects;

class AlgoBinarySearch {

    public static<T extends Comparable<? super T>> int bSearch(T e, List<T> list) {
        Objects.requireNonNull(e);
        if (list == null) return -1;

        int lo = 0;
        int hi = list.size() - 1;
        if (hi < 0) return -1;
        while (lo <= hi) {
            final int mid = (hi - lo) / 2 + lo;
            final int comp = e.compareTo(list.get(mid));
            if (comp == 0) return mid;
            if (comp < 0) hi = mid - 1;
            if (comp > 0) lo = mid + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        bSearchWith6ArgsFirst_Works();
        bSearchWith6ArgsMid_Works();
        bSearchWith6ArgsLast_Works();
        bSearchWith6ArgsAbsent_Works();
        bSearchWith0Args_Works();
        bSearchWithNullTarget_Throws();
        System.out.println("All tests passed");
    }

    public static void bSearchWith6ArgsFirst_Works() {
        var a = List.of(5, 10, 15, 20, 27, 42);
        var res = bSearch(5, a);
        assert res == 0;
    }

    public static void bSearchWith6ArgsMid_Works() {
        var a = List.of(5, 10, 15, 20, 27, 42);
        var res = bSearch(15, a);
        assert res == 2;
    }

    public static void bSearchWith6ArgsLast_Works() {
        var a = List.of(5, 10, 15, 20, 27, 42);
        var res = bSearch(42, a);
        assert res == a.size() - 1;
    }

    public static void bSearchWith6ArgsAbsent_Works() {
        var a = List.of(5, 10, 15, 20, 27, 42);
        var res = bSearch(10000, a);
        assert res == -1;
    }

    public static void bSearchWith0Args_Works() {
        var res = bSearch(10000, Collections.emptyList());
        assert res == -1;
    }

    public static void bSearchWithNullTarget_Throws() {
        boolean threw = false;
        try {
            Long target = null;
            bSearch(target, List.of(1L));
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw;
    }
}
