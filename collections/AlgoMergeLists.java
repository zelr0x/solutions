import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

class AlgoMergeLists {
    public static void main(String[] args) {
        var a = List.of(1, 2, 3, 4, 5);
        var b = List.of(-5, 2, 5, 10, 100);
        var c = List.of(-150, 7, 150, 300, 2000);
        var r1 = merge(List.of(a, b, c));
        System.out.println(r1);
    }

    public static <T extends Comparable<? super T>>
    List<T> merge(List<List<? extends T>> lists) {
        lists = prepareLists(lists);
        final int n = lists.size();
        if (n == 0 || lists.get(0) == null) return Collections.emptyList();

        Set<? extends Iterator<? extends T>> iterators = lists.stream()
                .map(List::iterator)
                .collect(toSet());
        PriorityQueue<T> q = new PriorityQueue<>(n);
        List<T> res = new ArrayList<>(n * lists.get(0).size());
        while (!iterators.isEmpty()) {
            var iteratorsIt = iterators.iterator();
            while (iteratorsIt.hasNext()) {
                var it = iteratorsIt.next();
                if (it.hasNext()) {
                    q.add(it.next());
                } else {
                    iteratorsIt.remove();
                }
            }
            pollInto(q, res);
        }
        return res;
    }

    private static <T extends Comparable<? super T>>
    void pollInto(Queue<? extends T> q, Collection<? super T> dest) {
        final int qLen = q.size();
        for (int i = 0; i < qLen; i++) {
            dest.add(q.poll());
        }
    }

    private static <T extends Comparable<? super T>>
    List<List<? extends T>> prepareLists(List<List<? extends T>> list) {
        return (list == null) ? Collections.emptyList() : list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
