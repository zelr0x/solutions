/* https://www.hackerrank.com/challenges/sum-numbers-c/problem
 1. Take two integers separated by space on one line.
 2. Output their sum and difference separated by space on the next line.
 3. Take two floats separated by space on the next line.
 4. Output their sum and difference separated by space on the next line.
*/
#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <wchar.h>

int main()
{   
    uint8_t const MAX_INPUT_WIDTH = 64;
    wchar_t buf[MAX_INPUT_WIDTH];
    wchar_t *endptr;

    wchar_t *const first_line = fgetws(buf, sizeof(buf), stdin);
    if (!first_line) {
        return 1;
    }
    int64_t const int1 = wcstoll(first_line, &endptr, 10);
    int64_t const int2 = wcstoll(endptr + 1, NULL, 10);
    printf("%" PRId64 " %" PRId64 "\n", int1 + int2, int1 - int2);

    memset(&buf[0], 0, sizeof(buf));

    wchar_t *const second_line = fgetws(buf, sizeof(buf), stdin);
    if (!second_line) {
        return 1;
    }
    endptr = second_line;
    long double const float1 = wcstold(second_line, &endptr);
    long double const float2 = wcstold(endptr + 1, NULL);
    printf("%.1Lf %.1Lf\n", float1 + float2, float1 - float2);

    return 0;
}
