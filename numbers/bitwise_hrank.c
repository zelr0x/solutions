/* Given n numbers, find maximum bitwise:
 * 1) AND
 * 2) OR
 * 3) XOR
 * of numbers from range [2, n] s.t. respective result is < k.
**/
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <inttypes.h>
#include <stdbool.h>

void calculate_the_maximum(int n, int k) {
    if (n < 2 || k < 2 || k > n) return;
    
    const bool eq = k == n;
    const int max_and = eq 
        ? k - 1 
        : (((k - 1) | k) <= n)
            ? k - 1 
            : k - 2;
    const int max_or = eq
        ? k - 2
        : (((k - 1) & (k - 2)) == 0) 
            ? ((k == 3) ? 0 : k - 2) 
            : k - 1;

    const char lbr = '\n';
    printf("%d%c", max_and, lbr);
    printf("%d%c", max_or, lbr);
    printf("%d%c", k - 1, lbr);
}

int main() {
    int n, k;
  
    scanf("%d %d", &n, &k);
    calculate_the_maximum(n, k);
 
    return 0;
}
