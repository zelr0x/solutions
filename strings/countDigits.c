// Read string from STDIN. 
// Print frequency of each digit in that string separated by spaces to STDOUT.
#include <stdio.h>
#include <stdlib.h>

int main() {
    size_t size = 1024;
    char *s = malloc(size * sizeof(char));
    if (s == NULL) {
        perror("Unable to allocate buffer");
        return 1;
    }
    size_t len = getline(&s, &size, stdin);
    if (len < 1) {
        perror("Wrong input");
        return 1;
    }

    int freq[10] = {0};
    for (size_t i = 0; i < len; i++) {
        char c = s[i];
        if (c >= '0' && c <= '9') freq[atoi(&c)]++;
    }
    free(s);    
    for (int digit = 0; digit < 10; digit++) {
        printf("%d ", freq[digit]);
    }    
    return 0;
}
