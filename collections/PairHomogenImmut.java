// Simple implementation of homogeneous (single-typed) immutable pair.
// Can be useful in pre java 9 environments where AbstractMap is disallowed 
// e.g. exercises with limited imports.
class Pair<T> {
    private final T first;
    private final T second;
    Pair(final T first, final T second) {
        this.first = first;
        this.second = second;
    }
    public T first() {
        return this.first;
    }
    public T second() {
        return this.second;
    }
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + 
            ((this.first == null) ? 0 : this.first.hashCode());
        result = prime * result + 
            ((this.second == null) ? 0 : this.second.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Pair)) return false;

        return this.first.equals(((Pair)other).first()) 
            && this.second.equals(((Pair)other).second());
    }
}
