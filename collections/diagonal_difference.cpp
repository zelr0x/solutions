/**
 * Diagonal difference is the absolute difference of the sums of all elements in both diagonals.
 * Example:
 * a b c
 * d e f
 * g h i
 * diff = abs((a + e + i) - (c + e + g))
 */
long long diagonal_diff(const vector<vector<int>>& arr) {
    const auto size = arr.size();
    auto diff{0};
    for (auto i = 0; i < size; i++) {
        diff += arr[i][i];
        diff -= arr[i][size - i - 1];
    }
    return abs(diff);
}
