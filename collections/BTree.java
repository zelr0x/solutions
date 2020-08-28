import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * A binary tree.
 *
 * @param <T> the type of the node value.
 *           
 * @author zelr0x 
 */
public class BTree<T extends Comparable<? super T>> {

    private T value;
    private BTree<T> left;
    private BTree<T> right;

    public BTree(T value) {
        this.value = value;
        right = null;
        left = null;
    }

    public BTree(T value, BTree<T> left, BTree<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public BTree<T> getLeft() {
        return left;
    }

    public BTree<T> getRight() {
        return right;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setLeft(BTree<T> left) {
        this.left = left;
    }

    public void setRight(BTree<T> right) {
        this.right = right;
    }

    public Optional<BTree<T>> searchPreOrder(T target) {
        T value = getValue();
        if (value == null) return Optional.empty();
        if (value.equals(target)) return Optional.of(this);

        BTree<T> left = getLeft();
        if (left != null) {
            Optional<BTree<T>> res = left.searchPreOrder(target);
            if (res.isPresent()) return res;
        }
        return Optional.ofNullable(getRight())
                .flatMap(r -> r.searchPreOrder(target));
    }

    public void forEachPreOrder(Consumer<? super T> action) {
        T value = getValue();
        if (value == null) return;

        action.accept(value);
        BTree<T> left = getLeft();
        if (left != null) {
            left.forEachPreOrder(action);
        }
        BTree<T> right = getRight();
        if (right != null) {
            right.forEachPreOrder(action);
        }
    }

    public void forEachInOrder(Consumer<? super T> action) {
        T value = getValue();
        if (value == null) return;

        BTree<T> left = getLeft();
        if (left != null) {
            left.forEachInOrder(action);
        }
        action.accept(value);
        BTree<T> right = getRight();
        if (right != null) {
            right.forEachInOrder(action);
        }
    }

    public void forEachPostOrder(Consumer<? super T> action) {
        T value = getValue();
        if (value == null) return;

        BTree<T> left = getLeft();
        if (left != null) {
            left.forEachPostOrder(action);
        }
        BTree<T> right = getRight();
        if (right != null) {
            right.forEachPostOrder(action);
        }
        action.accept(value);
    }

    public void forEachLevel(Consumer<? super T> action) {
        Queue<BTree<T>> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            BTree<T> head = q.poll();
            T value = head.getValue();
            if (value == null) break;

            Optional.ofNullable(head.getLeft()).ifPresent(q::offer);
            Optional.ofNullable(head.getRight()).ifPresent(q::offer);
            action.accept(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BTree<?> bTree = (BTree<?>) o;
        return Objects.equals(value, bTree.value) &&
                Objects.equals(left, bTree.left) &&
                Objects.equals(right, bTree.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }

    @Override
    public String toString() {
        return "BTree{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
