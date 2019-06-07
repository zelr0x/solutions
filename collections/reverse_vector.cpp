// Reverse given vector without using reverse iterators or STL algorithms
template <typename T>
vector<T> reverse(const vector<T>& a) {
    auto n = a.size();
    vector<T> res;
    res.reserve(n);
    while (n-- > 0) {
      res.push_back(a[n]);
    }
    return res;
}
