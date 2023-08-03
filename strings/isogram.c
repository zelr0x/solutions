#include <stdint.h>

// Checks if a given string is an isogram.
// An isogram is a string in which no letter is repeated.
bool is_isogram(const char *s) {
    if (!s) return false;
    uint32_t seen = 0;
    char c;
    while ((c = *s++) != 0) {
        c &= 0xdf; // convert to uppercase
        if (c >= 'A' && c <= 'Z') {
            const int i = c - 'A';
            if ((seen >> i) & 1) return false;
            seen |= 1 << i;
        }
    }
    return true;
}
