/**
 * Solution to https://www.hackerrank.com/challenges/between-two-sets
 */
 
int gcd(const int a, const int b) {
    return (b != 0) ? gcd(b, a % b) : a; 
}

int gcd(const vector<int>& v) {
    int res{v[0]};
    for (int i = 1; i < v.size(); i++) res = gcd(res, v[i]);
    return res;
}

int lcm(const int a, const int b) { 
    return abs(a * b) / gcd(a, b); 
}

int lcm(const vector<int>& v) {
    int res{v[0]};
    for (int i = 1; i < v.size(); i++) res = lcm(res, v[i]);
    return res;
}

int getTotalX(const vector<int>& a, const vector<int>& b) {
    const auto a_lcm = lcm(a);
    const auto b_gcd = gcd(b);
    auto res{0};
    for (int n = a_lcm, x = 2; n <= b_gcd; n = a_lcm * x, x++) {
      if (b_gcd % n == 0) res++;
    }
    return res;
}
