import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import net.jcip.annotations.NotThreadSafe;

/**
 * A doubly linked list-deque.
 * Not thread safe. Not capacity restricted.
 * Incomplete! Not fully tested!
 *
 * <p>It is essentially a worse version of standard LinkedList
 * implemented for the sake of implementing it.
 *
 * <p>In order to be thread safe it requires a modification management
 * and, if subList() is supported, comodification management.
 * subList() requires extending AbstractList or implementing a class for it.
 *
 * @param <E> the type of elements held in this list
 */
@NotThreadSafe
public final class DequeList<E> implements List<E>, Deque<E> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    /**
     * Constructs an empty list.
     */
    public DequeList() {
    }

    /**
     * Constructs a list from a specified collection.
     * @param c a collection from which to copy the elements
     */
    public DequeList(final Collection<? extends E> c) {
        this();
        addAll(c);
    }

    /**
     * Returns an iterator over this list.
     * @return an Iterator over this list from first to last
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> node = new Node<>(
                    null, null, DequeList.this.first);

            @Override
            public boolean hasNext() {
                return node.next != null;
            }

            @Override
            public E next() {
                if (node.next == null) throw new NoSuchElementException();
                node = node.next;
                return node.element;
            }
        };
    }

    /**
     * Returns a reverse iterator over this list.
     * @return an Iterator over this list from last to first
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {
            private Node<E> node = new Node<>(
                    DequeList.this.last, null, null);

            @Override
            public boolean hasNext() {
                return node.prev != null;
            }

            @Override
            public E next() {
                if (node.prev == null) throw new NoSuchElementException();
                node = node.prev;
                return node.element;
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        throw new UnsupportedOperationException(); // TODO
    }

    /**
     * Returns the number of the elements in the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the list contains no elements.
     * @return true if list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if this list contains a specified element.
     * @param o an element to search for
     * @return true if this list contains this element, false otherwise
     */
    @Override
    public boolean contains(final Object o) {
        for (final var element : this) {
            if (Objects.equals(element, o)) return true;
        }
        return false;
    }

    /**
     * Checks if this list contains all elements of a specified collection.
     * @param c a collection containing elements to search for
     * @return true if this list contains all the elements of c,
     * false otherwise
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final var element : c) {
            if (!this.contains(element)) return false;
        }
        return true;
    }

    /**
     * Finds the index of a first occurrence of a specified object
     * starting from the start of the list.
     * @param o an object which index to find
     * @return an index of the first occurrence of o -1 if not found
     */
    @Override
    public int indexOf(final Object o) {
        int index = 0;
        for (final var element : this) {
            if (Objects.equals(element, o)) {
                return index;
            }
            index++;
        }
        return -1;
    }
    /**
     * Finds the index of a first occurrence of a specified object
     * starting from the end of the list.
     * @param o an object which index to find
     * @return an index of the last occurrence of o or -1 if not found
     */
    @Override
    public int lastIndexOf(final Object o) {
        final var iter = descendingIterator();
        int index = size;
        while (iter.hasNext()) {
            index--;
            final var element = iter.next();
            if (Objects.equals(element, o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public List<E> subList(final int from, final int to) {
        throw new UnsupportedOperationException(); // TODO
//        if (from < 0 || to > size || from > to) {
//            throw new IndexOutOfBoundsException();
//        }
//        final var res = new DequeList<E>();
//        final var iter = iterator();
//        int index = 0;
//        while (index != to) {
//            final var node = iter.next();
//
//            index++;
//        }
//        return res;
    }

    /**
     * Retrieves but not removes the element with a specified index.
     * @param index zero-based index of an element to return
     * @return an element with index specified by the index parameter
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public E get(final int index) {
        checkIndex(index);
        return nodeAt(index).element;
    }

    /**
     * Retrieves but not removes the first element.
     * @return the first element if the collection is not empty
     * @throws NoSuchElementException when the collection is empty
     */
    @Override
    public E getFirst() {
        checkSizeThrow();
        return first.element;
    }

    /**
     * Retrieves but not removes the last element.
     * @return the last element if the collection is not empty
     * @throws NoSuchElementException when the collection is empty
     */
    @Override
    public E getLast() {
        checkSizeThrow();
        return last.element;
    }

    /**
     * Retrieves but not removes the first element.
     * Equivalent to {@link #getFirst}
     * @return the first element if the collection is not empty
     * @throws NoSuchElementException when the collection is empty
     */
    @Override
    public E element() {
        return getFirst();
    }

    /**
     * Retrieves but not removes the first element.
     * @return the first element if the collection is not empty, null otherwise
     */
    @Override
    public E peekFirst() {
        return (size > 1) ? first.element : null;
    }

    @Override
    public E peekLast() {
        return (size > 1) ? last.element : null;
    }

    /**
     * Retrieves but not removes the first element.
     * Equivalent to {@link #peekFirst}
     * @return the first element if the collection is not empty, null otherwise
     */
    @Override
    public E peek() {
        return peekFirst();
    }

    /**
     * Adds an element to the end.
     * @param element an element to add
     * @return true (always)
     */
    @Override
    public boolean add(final E element) {
        linkLast(element);
        return true;
    }

    /**
     * Adds an element at a specified index.
     * @param index an index at which to insert
     * @param element an element to add
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * which is less than 0 or greater than size for this operation
     */
    @Override
    public void add(final int index, final E element) {
        if (index == size) {
            linkLast(element);
            return;
        }
        checkIndex(index);
        final var oldNode = nodeAt(index);
        oldNode.prev = new Node<>(oldNode.prev, element, oldNode);
    }

    /**
     * Adds an element to the start.
     * @param element an element to add
     */
    @Override
    public void addFirst(final E element) {
        linkFirst(element);
    }

    /**
     * Adds an element to the end.
     * @param element an element to add
     */
    @Override
    public void addLast(final E element) {
        linkLast(element);
    }

    /**
     * Adds an element to the start.
     * Equivalent to {@code #addFirst}
     * @param element an element to add
     */
    @Override
    public void push(final E element) {
        addFirst(element);
    }

    /**
     * Inserts an element at a specified index.
     * Equivalent to {@code #add}
     * @param index an index at which to insert
     * @param element an element to add
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * which is less than 0 or greater than size for this operation
     */
    public void insert(final int index, final E element) {
        add(index, element);
    }

    /**
     * Adds an element at the start. There are no capacity restrictions.
     * @param element an element to add
     * @return true if the element was added, false otherwise
     */
    @Override
    public boolean offerFirst(final E element) {
        final int oldSize = size;
        linkFirst(element);
        return size != oldSize;
    }

    /**
     * Adds an element at the start. There are no capacity restrictions.
     * @param element an element to add
     * @return true if the element was added, false otherwise
     */
    @Override
    public boolean offerLast(final E element) {
        final int oldSize = size;
        linkLast(element);
        return size != oldSize;
    }

    /**
     * Adds an element at the end. There are no capacity restrictions.
     * Equivalent to {@code #offerLast}
     * @param element an element to add
     * @return true if the element was added, false otherwise
     */
    @Override
    public boolean offer(final E element) {
        return offerLast(element);
    }

    /**
     * Inserts all elements of a specified collection at the end.
     * starting at a specified index.
     * @param index an index at which to start the insertion
     * @param c a collection containing elements to add
     * @return true if at least one element was added
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        checkIndex(index);
        final var oldSize = size;
        var i = index;
        for (final var element : c) {
            this.add(i++, element);
        }
        return size != oldSize;
    }

    /**
     * Adds all elements of a specified collection.
     * @param c a collection containing elements to add
     * @return true if at least one element was added
     */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c.size() < 1) return false;
        addAll(size, c);
        return true;
    }

    /**
     * Sets a specified element as an element of a node at a specified index.
     * @param index an index at which to set
     * @param element an element to set instead of replaced element
     * @return a replaced element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public E set(final int index, final E element) {
        checkIndex(index);
        final var replaced = nodeAt(index).element;
        nodeAt(index).element = element;
        return replaced;
    }

    /**
     * Removes an element.
     * @param o an object to remove
     * @return true if the object was found and removed, false otherwise
     */
    @Override
    public boolean remove(final Object o) {
        for (var node = first; node != null; node = node.next) {
            if (Objects.equals(node.element, o)) {
                unlink(node);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an element.
     * @param index an index of an element to remove
     * @return true if an element was found and removed, false otherwise
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public E remove(final int index) {
        checkIndex(index);
        return unlink(nodeAt(index));
    }

    /**
     * Removes the first element.
     * @return true if the element was removed, false otherwise
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E removeFirst() {
        checkSizeThrow();
        return unlink(first);
    }

    /**
     * Removes the last element.
     * @return true if the element removed, false otherwise
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E removeLast() {
        checkSizeThrow();
        return unlink(last);
    }

    /**
     * Removes the first element.
     * Equivalent of {@code #removeFirst}
     * @return true if the element was removed, false otherwise
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E remove() {
        return removeFirst();
    }

    /**
     * Removes the first element.
     * Equivalent of {@code #removeFirst}
     * @return true if the element was removed, false otherwise
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E pop() {
        return removeFirst();
    }

    /**
     * Removes the first occurrence of a specified object in this list.
     * Equivalent to {@code #remove}
     * @param o an object to remove
     * @return true if the object was found and removed, false otherwise
     */
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        return remove(o);
    }

    /**
     * Removes the last occurrence of a specified object in this list.
     * @param o an object to remove
     * @return true if the object was found and removed, false otherwise
     */
    @Override
    public boolean removeLastOccurrence(final Object o) {
        for (var node = last; node != null; node = node.prev) {
            if (Objects.equals(node.element, o)) {
                unlink(node);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the first element.
     * @return removed element or null if the list is empty.
     */
    @Override
    public E pollFirst() {
        return (size > 0) ? unlink(first) : null;
    }

    /**
     * Removes the last element.
     * @return removed element or null if the list is empty.
     */
    @Override
    public E pollLast() {
        return (size > 0) ? unlink(last) : null;
    }

    /**
     * Removes the first element.
     * Equivalent of {@code #pollFirst}
     * @return removed element or null if the list is empty.
     */
    @Override
    public E poll() {
        return (size > 0) ? unlink(first) : null;
    }

    /**
     * Removes all elements of a specified collection.
     * @param c a collection containing elements to remove
     * @return true if at least one element was removed
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        final var oldSize = size;
        c.forEach(this::remove);
        return size != oldSize;
    }

    /**
     * Removes all elements that are not in a specified collection.
     * @param c a collection containing the only elements to leave intact
     * @return true if at least one element was removed
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        final var oldSize = size;
        this.removeIf(e -> !c.contains(e));
        return size != oldSize;
    }

    /**
     * Removes all the elements from the collection.
     */
    @Override
    public void clear() {
        for (var node = first; node != null;) {
            final var next = node.next;
            node.element = null;
            node.prev = null;
            node.next = null;
            node = next;
        }
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Creates an array containing the elements in this list.
     * @return an array of elements in this list
     */
    @Override
    public Object[] toArray() {
        final Object[] res = new Object[size];
        var i = 0;
        for (final var element : this) {
            res[i++] = element;
        }
        return res;
    }

    /**
     * Creates an array containing the elements in this list.
     * @param a an array into which to write the result if its capacity
     *          is sufficient
     * @return an array of elements in this list
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (size > a.length) {
            a = (T[]) Array.newInstance(
                    a.getClass().getComponentType(),
                    size);
        }
        final Object[] res = a;
        var i = 0;
        for (final var element : this) {
            res[i++] = element;
        }
        return a;
    }

    /**
     * Returns the node at a specified index.
     * The method performs NO BOUND CHECKING.
     * @param index an index at which to start
     * @return Node at a specified index
     */
    private Node<E> nodeAt(final int index) {
        var node = this.first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Checks that index is within the size bounds for access.
     * @param index an index to check
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    private void checkIndex(final int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is not within 0.." + size);
        }
    }

    /**
     * Checks the size of the collection and throws exception if it is empty.
     * @throws NoSuchElementException if the list is empty
     */
    private void checkSizeThrow() {
        if (size == 0) throw new NoSuchElementException();
    }

    /**
     * Link the new node with a specified element at the start of the list.
     * @param e an element of the new node
     */
    private void linkFirst(final E e) {
        final var oldFirst = this.first;
        final var newFirst = new Node<>(null, e, oldFirst);
        this.first = newFirst;
        if (oldFirst == null) {
            this.last = newFirst;
        } else {
            oldFirst.prev = newFirst;
        }
        size++;
    }

    /**
     * Link the new node with a specified element at the end of the list.
     * @param e an element of the new node
     */
    private void linkLast(final E e) {
        final var oldLast = this.last;
        final var newLast = new Node<>(oldLast, e, null);
        this.last = newLast;
        if (oldLast == null) {
            this.first = newLast;
        } else {
            oldLast.next = newLast;
        }
        size++;
    }

    /**
     * Unlink the specified node from this list.
     * @param node the node to unlink.
     * @return the element contained in the unlinked node
     */
    private E unlink(final Node<E> node) {
        final var prev = node.prev;
        final var next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        final var element = node.element;
        node.element = null;
        size--;
        return element;
    }

    /**
     * Doubly linked list node.
     * @param <E> the type of elements held in the list
     */
    private static final class Node<E> {
        private Node<E> prev;
        private E element;
        private Node<E> next;

        /**
         * Constructs the node with the specified previous node,
         * element and the next node.
         * @param prev a node previous to this
         * @param element an element of this node
         * @param next a node next to this
         */
        private Node(final Node<E> prev, final E element,
                     final Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
