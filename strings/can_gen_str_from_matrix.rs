// https://www.hackerearth.com/practice/data-structures/arrays/multi-dimensional/practice-problems/algorithm/find-the-string-4014dec6/

use std::io::{self, BufRead};
use std::collections::HashMap;

pub struct MatrixDimensions {
    pub rows: usize,
    pub cols: usize
}

pub struct Matrix<T> {
    pub data: Vec<T>,
    pub dimensions: MatrixDimensions
}

type CharOccurrences = HashMap<char, usize>;

fn main() {
    let stdin = io::stdin();
    let tests = read_test_amount(&stdin);
    for _ in 0..tests {
        let matrix = read_occurrence_matrix(&stdin);
        let test_str = read_test_str(&stdin);
        if can_generate(&matrix, &test_str) {
            println!("Yes")
        } else {
            println!("No")
        }
    }
}

fn read_test_amount(stdin: &io::Stdin) -> usize {
    let mut tests = String::new();
    stdin.read_line(&mut tests).unwrap();
    tests.trim().parse::<usize>().unwrap()
}

fn read_dimensions(stdin: &io::Stdin) -> MatrixDimensions {
    let mut nm = String::new();
    stdin.read_line(&mut nm).unwrap();
    let mut nm = nm.trim()
        .split_whitespace()
        .take(2)
        .map(|i| i.parse::<usize>().unwrap());
    let n: usize = nm.next().unwrap();
    let m: usize = nm.next().unwrap();
    MatrixDimensions {
        rows: n,
        cols: m
    }
}

fn read_occurrence_matrix(stdin: &io::Stdin) -> Matrix<CharOccurrences> {
    let dimensions = read_dimensions(stdin);
    let data: Vec<CharOccurrences> = stdin.lock().lines()
        .take(dimensions.rows)
        .map(|s| char_map(s.unwrap()))
        .collect();
    Matrix::<CharOccurrences> {
        data: data,
        dimensions: dimensions
    }
}

fn read_test_str(stdin: &io::Stdin) -> String {
    let mut test_str = String::new();
    stdin.read_line(&mut test_str).unwrap();
    test_str.trim().to_string()
}

fn char_map(s: String) -> CharOccurrences {
    let mut res: CharOccurrences = HashMap::with_capacity(s.len());
    s.chars()
        .for_each(|c| *res.entry(c).or_insert(0) += 1);
    res
}

// TODO: make cleaner
fn can_generate(matrix: &Matrix<CharOccurrences>, test_str: &str) -> bool {
    let mut seen: Vec<CharOccurrences> = Vec::with_capacity(test_str.len());
    for (i, c) in test_str.char_indices() {
        let row_idx = i % matrix.dimensions.rows;
        let matrix_row = &matrix.data[row_idx];
        let res = match matrix_row.get_key_value(&c) {
            Some(e) => match seen.get_mut(row_idx) {
                Some(seen_row) => {
                    let seen_occurrences = seen_row.entry(*e.0).or_insert(1);
                    if *seen_occurrences <= *e.1 {
                        *seen_occurrences += 1;
                        true
                    } else {
                        false
                    }
                },
                None => {
                    let new_seen_row = [(*e.0, 1)].iter()
                        .cloned()
                        .collect::<HashMap<char, usize>>();
                    seen.push(new_seen_row);
                    true
                }
            },
            None => {
                false // tested char is not present in the matrix row.
            }
        };
        if res == false {
            return false
        }
    }
    true
}
