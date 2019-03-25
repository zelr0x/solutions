// Read line from STDIN and store it.
// Write it to STDOUT with each word on its own line.
#include <stdio.h>
#include <stdlib.h>

int main() {
    size_t size = 1024;
    char *s = malloc(size * sizeof(char));
    if (s == NULL) {
        perror("Unable to allocate buffer");
        return 1;
    }
    size_t read_size = getline(&s, &size, stdin);
    if (read_size < 1) {
        perror("Wrong input");
        return 1;
    }

    for (size_t i = 0; i < read_size; i++) {
        char c = s[i];
        printf("%c", (c != ' ' && c != '\t') ? c : '\n');
    }

    free(s);
    return 0;
}
