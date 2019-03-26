import java.util.*;

public class Algo {
    /**
     * @param iterable Iterable of comparable objects.
     * @param <T> Comparable object.
     * @return Map.Entry with key being min and value being max of iterable.
     * @throws NoSuchElementException when iterable is empty.
     */
    public static <T extends Comparable<? super T>>
            Map.Entry<T, T> minMax(Iterable<T> iterable)
            throws NoSuchElementException {
        var it = iterable.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("Iterable is empty");
        }
        var min = it.next();
        var max = min;
        while(it.hasNext()) {
            var item = it.next();
            if (item.compareTo(min) < 0) min = item;
            if (item.compareTo(max) > 0) max = item;
        }
        return Map.entry(min, max);
    }

    public static void main(String... args) {
        var list = List.of("a", "b", "BB", "abba", "100", "Zas", "Saz", "Azs", "z");
        System.out.println(Arrays.toString(list.toArray()) +
                "\nMin: " + minMax(list).getKey() +
                "\nMax: " + minMax(list).getValue());

        var set = Set.of(1, 100, 20, 55, 3);
        System.out.println(Arrays.toString(set.toArray()) +
                "\nMin: " + minMax(set).getKey() +
                "\nMax: " + minMax(set).getValue());
    }

}
