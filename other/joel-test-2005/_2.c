#include <stdio.h>

int main(int argc, char **argv) {
  for (int i = 1; i < argc; i++) {
    putchar(argv[i][0]);
  }
  putchar('\n');
}
