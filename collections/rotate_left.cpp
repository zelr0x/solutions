/**
 * Rotates vector to the left for a specified number of positions.
 * Rotation is done without unnecessary memory allocations.
 * The vector is traversed only once since each of the three loops traverse
 * only its own part of the vector.
 * @param <T> a type of the elements held in the provided vector
 * @param v a vector to rotate
 * @param n a number of rotations to perform
 */
template <typename T>
void rotate_left(vector<T>& v, size_t n=1) {
    const auto size = v.size();
    if (n < 1 || size < 2) return;
    n %= size; // handle n > size

    vector<T> left(n);
    for (auto i = 0; i < n; i++) {
        left[i] = move(v[i]);
    }
    for (auto i = n; i < size; i++) {
        v[i - n] = move(v[i]);
    }
    for (size_t i = size - n, j = 0; i < size; i++, j++) {
        v[i] = move(left[j]);
    }
}
