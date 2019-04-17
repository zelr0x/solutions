vector<int> parseInts(const string& str) {
    stringstream ss{str};
    vector<int> res;
    for (int i; ss >> i; ss.ignore(1, ',')) {
        res.push_back(i);
    }
    return res;
}
