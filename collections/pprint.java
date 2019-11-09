  private static void pprint(final int[] arr) {
    final String res = Arrays.stream(arr)
      .mapToObj(String::valueOf)
      .collect(Collectors.joining(", "));
    System.out.println(res);
  }
  
  private static <T> void pprint(final T[] arr) {
    final String res = Arrays.stream(arr)
      .map(Object::toString)
      .collect(Collectors.joining(", "));
    System.out.println(res);
  }
