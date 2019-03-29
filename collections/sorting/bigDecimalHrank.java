// Given an array s of n strings containing big decimals, sort them in descending order.
// A code can only be written within "unlocked" section.

import java.math.BigDecimal;
import java.util.*;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] s = new String[n + 2];
        for(int i = 0; i < n; i++){
            s[i] = sc.next();
        }
        sc.close();
        
        // Begin unlocked
        Arrays.sort(s, 0, n, Collections.reverseOrder((a, b) 
            -> new BigDecimal(a).compareTo(new BigDecimal(b))));
        // End unlocked
        
        for(int i = 0; i < n; i++) {
            System.out.println(s[i]);
        }
    }
}
