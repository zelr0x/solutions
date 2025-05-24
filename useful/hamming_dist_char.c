int hamming_char(const char *lhs, const char *rhs) {
    if (!lhs || !rhs) return -1;
    if (lhs == rhs) return 0;
    int diff = 0;
    for (; *lhs && *rhs; lhs++, rhs++) {
        if (*lhs != *rhs) diff++;
    }
    return (*lhs || *rhs) ? -1 : diff;
}
