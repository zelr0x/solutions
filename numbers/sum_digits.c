#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>

uint8_t sum_digits(int64_t n, uint8_t base) {
    n = llabs(n);
    uint8_t acc = 0;
    while (n > 0) {
        acc += n % base;
        n /= base;
    }
    return acc;
}

int main() {	
    uint64_t n = 0;
    scanf("%" SCNd64, &n);    
    uint8_t sum = sum_digits(n, 10);
    printf("%" PRId8 "\n", sum);

    return 0;
}
