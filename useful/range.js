function* range(current, end, step = 1) {
  if (arguments.length === 1) {
    end = current;
    current = 0;
  }
  
  if (current < end) yield current;
  for (; current + step < end; current += step) yield current + step;
}
