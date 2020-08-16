class Trie {
    private static final int ASCII_LATIN_LC_A = (int) 'a';
    
    private Trie[] children = new Trie[26];
    private boolean word = false;
    
    public Trie[] getChildren() {
        return children;
    }
    
    public boolean isWord() {
        return word;
    }
    
    /** Inserts a word into the trie. */
    public void insert(final String word) {
        Trie node = this;
        for (final char c : word.toCharArray()) {
            final Trie[] children = node.getChildren();
            final int idx = getIdx(c);
            if (children[idx] == null) {
                children[idx] = new Trie();
            }
            node = children[idx];
        }
        node.word = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Trie node = this;
        for (final char c : word.toCharArray()) {
            final Trie[] children = node.getChildren();
            final int idx = getIdx(c);
            if (children[idx] == null) {
                return false;
            }
            node = children[idx];
        }
        return node.isWord();
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Trie node = this;
        for (final char c : prefix.toCharArray()) {
            final Trie[] children = node.getChildren();
            final int idx = getIdx(c);
            if (children[idx] == null) {
                return false;
            }
            node = children[idx];
        }
        return true;
    }
    
    private int getIdx(final char c) {
        return ((int) c) - ASCII_LATIN_LC_A;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
