/** 
 * Finds the maximum hourglass sum of 2d vector.
 * Hourglass looks like this:
 *   a b c
 *     d
 *   e f g
 * @returns maximum hourglass sum as int (as stated in exercise)
 * @see https://www.hackerrank.com/challenges/2d-array/problem
 */
int hourglassSum(const vector<vector<int>>& a) {
    constexpr int hourglass_size{3};
    constexpr int excess_values{hourglass_size - 1};

    auto height{a.size()};
    if (height < hourglass_size) return 0;
    auto width{a[0].size()};
    if (width < hourglass_size) return 0;
    height -= excess_values;
    width -= excess_values;

    long long max_sum = LLONG_MIN;
    for (auto i = 0; i < height; i++) {
        for (auto j = 0; j < width; j++) {
            long long sum = a[i][j] + a[i][j + 1] + a[i][j + 2]
                + a[i + 1][j + 1]
                + a[i + 2][j] + a[i + 2][j + 1] + a[i + 2][j + 2];
            if (sum > max_sum) max_sum = sum;
        }
    }

    return static_cast<int>(max_sum);
}
