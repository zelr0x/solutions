import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean isAnagram(final String s, final String t) {
        if (s == t) return true;
        if (s == null || t == null) return false;
        final int n = s.length();
        if (n != t.length()) return false;
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        s.codePoints().forEach(c -> freq.merge(c, 1, Integer::sum));
        for (int i = 0; i < n; ) {
            final int c = t.codePointAt(i);
            final Integer f = freq.get(c);
            if (f == null || f <  1) return false;
            freq.put(c, f-1);
            i += Character.charCount(c);
        }
        return true;
    }
}
