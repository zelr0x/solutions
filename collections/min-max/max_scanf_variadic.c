/* Read integers from STDIN with scanf (required by original task),
 * write the highest of them to STDOUT. 
 * The first integer is the number of consequent integer inputs.
**/
#include <inttypes.h>
#include <stdarg.h>
#include <stdio.h>

int64_t max(uint64_t argc, int64_t *argv) {
  int64_t current_max = INT64_MIN;
  for (size_t i = 0; i < argc; i++) {
    const int64_t n = argv[i];
    if (n > current_max) current_max = n;
  }
  return current_max;
}

int main() {
  printf("N = ");
  size_t n = 0;
  scanf("%zu", &n);

  int64_t numbers[n];
  for (size_t i = 0; i < n; i++) {
    scanf("%" SCNd64, &numbers[i]);
  }

  const int64_t ans = max(n, numbers);
  printf("Max is %" PRId64 "\n", ans);

  return 0;
}
