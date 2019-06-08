/**
 * Counts items from targets in collection.
 * @param <T> a type of the items held in both vectors
 * @param collection the items with which to compare
 * @param targets the items to count in collection
 * @returns a vector containing count of each target from targets in a collection
 */
template <typename T>
vector<int> count_matches(const vector<T>& collection,
                          const vector<T>& targets) {
    unordered_map<T, int> counter;
    counter.reserve(collection.size());
    for (const auto& e : collection) counter[e]++;

    vector<int> res;
    res.reserve(targets.size());
    for (const auto& target : targets) {
        const auto it = counter.find(target);
        res.push_back(it == counter.end() ? 0 : it->second);
    }
    return res;
}
