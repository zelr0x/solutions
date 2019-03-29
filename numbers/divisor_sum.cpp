#include <iostream>
#include <cstdint>

// Sum all divisors of a positive integer including one and the number itself.
uint64_t divisor_sum(const uint32_t n) {
    if (n == 1) return 1;

    uint64_t res = n + 1;
    for (int i = 2, mid = n / 2; i <= mid; i++) {
        if (n % i == 0) res += i;
    }
    return res;
}

int main() {
    std::cout << divisor_sum(10) << std::endl;
}
