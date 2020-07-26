import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class SimultaneousThreadStart {
    public static <T extends Comparable<? super T>> void bubble(T[] a) throws InterruptedException {
        for (int j = a.length - 1; j >= 1; j--) {
            System.out.printf("Bubble\t\t{ j = %d, arr = %s }\n", j, Arrays.deepToString(a));
            for (int i = 0; i < j; i++) {
                if (more(a, i, i + 1)) {
                    swap(a, i, i + 1);
                }
            }
        }
    }

    public static <T extends Comparable<? super T>> void insertion(T[] a) throws InterruptedException {
        for (int j = 1; j < a.length; j++) {
            System.out.printf("Insertion\t{ j = %d, arr = %s }\n", j, Arrays.deepToString(a));
            final T e = a[j];
            int i = j - 1;
            while (i >= 0 && e.compareTo(a[i]) < 0) {
                swap(a, i, i + 1);
                i--;
            }
        }
    }

    private static <T extends Comparable<? super T>> boolean more(T[] a, final int q, final int p) {
        return a[q].compareTo(a[p]) > 0;
    }

    private static <T extends Comparable<? super T>> void swap(T[] a, final int q, final int p) {
        final T t = a[q];
        a[q] = a[p];
        a[p] = t;
    }

    public static void main(final String[] args) {
        final Integer[] arr1 = new Integer[]{5, 10, 10, -10, 0, 0, 1, 2, 18, -3};
        final Integer[] arr2 = new Integer[]{5, 10, 10, -10, 0, 0, 1, 2, 18, -3};
        try {
            final CyclicBarrier gate = new CyclicBarrier(3);
            final Thread thInsertion = new Thread(() -> {
                try {
                    gate.await();
                    insertion(arr1);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            final Thread thBubble = new Thread(() -> {
                try {
                    gate.await();
                    bubble(arr2);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            thInsertion.start();
            thBubble.start();
            gate.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
