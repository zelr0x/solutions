import java.lang.Comparable;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class AlgoBubbleSort {

    public static <T extends Comparable<? super T>> void sort(final T[] arr,
                                                              final Comparator<? super T> cmp) {
        sort((Object[]) arr, cmp);
    }

    public static <T extends Comparable<? super T>> void sort(final List<T> list,
                                                              final Comparator<? super T> cmp) {
        final Object[] arr = list.toArray();
        sort(arr, cmp);
        final ListIterator<T> iter = list.listIterator();
        for (int i = 0; i < arr.length; i++) {
            iter.next();
            iter.set((T) arr[i]);
        }
    }

    private static void sort(final Object[] arr, final Comparator cmp) {
        final int len = arr.length;
        if (len < 2) return;

        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                final int nextIdx = i + 1;
                if (cmp.compare(arr[i], arr[nextIdx]) > 0) {
                    swap(arr, i, nextIdx);
                }
            }
        }
    }

    private static void swap(final Object[] arr, final int i, final int j) {
        final Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
