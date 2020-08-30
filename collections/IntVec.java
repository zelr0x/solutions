class Scratch {
    public static abstract class BaseVec {
        private static final int CAPACITY_FACTOR = 2;
        protected static final int CAPACITY = 16;

        protected final double capacityFactor;
        protected int capacity;
        protected int size = 0;

        public BaseVec() {
            this(CAPACITY, CAPACITY_FACTOR);
        }

        public BaseVec(int capacity) {
            this(capacity, CAPACITY_FACTOR);
        }

        public BaseVec(final double capacityFactor) {
            this(CAPACITY, capacityFactor);
        }

        public BaseVec(final int capacity, final double capacityFactor) {
            this.capacity = capacity;
            this.capacityFactor = capacityFactor > 1
                    ? capacityFactor
                    : CAPACITY_FACTOR;
        }

        protected int getExpandedCapacity(final int capacity) {
            return (int) Math.ceil(capacity * capacityFactor);
        }

        protected void expandCapacity() {
            this.capacity = getExpandedCapacity(this.capacity);
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public static final class IntVec extends BaseVec {
        private int[] data = null;

        public IntVec(final int capacity) {
            super(capacity);
        }

        public IntVec(final double capacityFactor) {
            super(capacityFactor);
        }

        public IntVec(final int capacity, final double capacityFactor) {
            super(capacity, capacityFactor);
        }

        public IntVec(int[] data) {
            this(data.length);
            this.data = data;
            this.size = data.length;
        }

        public IntVec(int[] data, final boolean preserveCapacity) {
            this(data);
            if (!preserveCapacity) expandCapacity();
        }

        public IntVec(int[] data, final double capacityFactor) {
            super(data.length, capacityFactor);
            this.data = data;
            this.size = data.length;
        }

        public IntVec(int[] data, final double capacityFactor, final boolean preserveCapacity) {
            this(data, capacityFactor);
            this.size = data.length;
            if (!preserveCapacity) expandCapacity();
        }

        public int get(final int index) {
            requireIndexInBounds(index);
            return this.data[index];
        }

        public boolean add(final int e) {
            if (data == null) {
                data = new int[capacity];
            } else {
                expandDataIfNeeded();
            }
            data[size++] = e;
            return true;
        }

        public int set(final int index, final int e) {
            requireIndexInBounds(index);
            final int prev = data[index];
            data[index] = e;
            return prev;
        }

        public int indexOf(final int e) {
            for (int i = 0; i < size; i++) {
                if (data[i] == e) return i;
            }
            return -1;
        }

        public void forEach(Consumer<? super Integer> action) {
            for (int i = 0; i < size; i++) {
                action.accept(data[i]);
            }
        }

        public IntStream stream() {
            IntStream.Builder builder = IntStream.builder();
            forEach(builder::accept);
            return builder.build();
        }

        public Stream<Integer> boxedStream() {
            return stream().boxed();
        }

        @Override
        public String toString() {
            if (size == 0) return "[]";
            StringBuilder sb = new StringBuilder(size * 3);
            sb.append('[');
            for (int i = 0; i < size - 1; i++) {
                sb.append(data[i]);
                sb.append(", ");
            }
            sb.append(data[size - 1]);
            sb.append(']');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntVec intVec = (IntVec) o;
            return Arrays.equals(data, intVec.data);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(data);
        }

        private void requireIndexInBounds(int index) {
            if (index < 0 || index >= this.size) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        private void expandDataIfNeeded() {
            if (size == capacity) {
                int newCapacity = getExpandedCapacity(capacity);
                int[] newData = new int[newCapacity];
                System.arraycopy(data, 0, newData, 0, size);
                capacity = newCapacity;
                data = newData;
            }
        }
    }
}
