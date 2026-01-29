#include <vector>
#include <functional>

int sum_rec(std::vector<int> numbers) {
    auto n = numbers.size();
    std::function<int(int, int)> sum = [n, &numbers, &sum](int acc, int offset) {
        return (offset == n)
            ? acc
            : sum(acc+numbers[offset], offset+1);
    };
    return sum(0, 0);
}

/*
// Alternatively, with external helper, allowing to make it constexpr and drop <functional> include:
constexpr int sum(const std::vector<int>& numbers, const size_t size,
                  int acc, size_t offset) {
    return offset == size
        ? acc
        : sum(numbers, size, acc + numbers[offset], offset + 1);
}

int sum_rec(std::vector<int> numbers) {
    return sum(numbers, numbers.size(), 0, 0);
}

*/
