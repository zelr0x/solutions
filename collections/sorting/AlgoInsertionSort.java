import java.util.ArrayList;
import java.util.List;

/**
 * Inplace insertion sort based on the algorithm from the Cormen's Introduction to algorithms book.
 */
public class AlgoInsertionSort {
    public static <T extends Comparable<? super T>> void insertionSort(List<T> a) {
        insertionSort(a, T::compareTo);
    }

    public static <T> void insertionSort(List<T> a, Comparator<? super T> comp) {
        if (comp == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int j = 1; j < a.size(); j++) {
            T key = a.get(j);
            int i =  j - 1;
            while (i >= 0 && comp.compare(a.get(i), key) > 0) {
                a.set(i + 1, a.get(i--));
            }
            a.set(i + 1, key);
        }
    }
}
