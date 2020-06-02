// https://leetcode.com/explore/challenge/card/may-leetcoding-challenge/537/week-4-may-22nd-may-28th/3337/
use std::collections::BTreeMap;
use std::iter::FromIterator;

impl Solution {
    pub fn frequency_sort(s: String) -> String {
        let mut res = String::with_capacity(s.len());
        let freq = get_freq(&s);
        freq.iter().for_each(|(ch, count)| push_times(&mut res, *ch, *count));
        res
    }
}

fn push_times(s: &mut String, ch: char, count: usize) {
    for i in 0..count {
        s.push(ch);
    }
}

fn get_freq(s: &str) -> Vec<(char, usize)> {
    let mut res = Vec::from_iter(char_map(&s));
    res.sort_by(|&(_, a), &(_, b)| b.cmp(&a));
    res
}

fn char_map(s: &str) -> BTreeMap<char, usize> {
    let mut res: BTreeMap<char, usize> = BTreeMap::new();
    s.chars().for_each(|c| *res.entry(c).or_insert(0) += 1);
    res
}
