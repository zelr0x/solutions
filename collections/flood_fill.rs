use std::collections::VecDeque;

pub mod matrix {
    use std::fmt::Binary;

    #[derive(Debug, Hash, PartialEq)]
    pub struct Index<T: Copy + Binary> {
        pub row: T,
        pub col: T
    }
}

use matrix::Index;

/// Solution to https://leetcode.com/problems/flood-fill/
impl Solution {
    pub fn flood_fill(image: Vec<Vec<i32>>, sr: i32, sc: i32, new_color: i32) -> Vec<Vec<i32>> {
        let sr = sr as usize;
        let sc = sc as usize;
        let orig_color = image[sr][sc];
        if orig_color == new_color {
            return image
        }
        
        let rows = image.len();        
        let mut res = Solution::clone_2d(&image);
        let mut q: VecDeque<Index<usize>> = VecDeque::new();
        q.push_back(Index{row: sr, col: sc});
        
        while !q.is_empty() {
            let current = q.pop_front().unwrap();
            let row = current.row;
            let col = current.col;
            if res[row][col] == orig_color {
                res[row][col] = new_color;
                if let Some(prev_row) = row.checked_sub(1) {
                    q.push_back(Index{ row: prev_row, col });
                }
                if row + 1 < rows {
                    q.push_back(Index{ row: row + 1, col });
                }
                if let Some(prev_col) = col.checked_sub(1) {
                    q.push_back(Index{ row, col: prev_col });
                }
                if col + 1 < image[row].len() {
                    q.push_back(Index{ row, col: col + 1 });
                }
            }
        }
        res
    }
    
    fn clone_2d<T: Clone>(list: &Vec<Vec<T>>) -> Vec<Vec<T>> {
        let mut res: Vec<Vec<T>> = Vec::with_capacity(list.len());
        list.iter().for_each(|inner| res.push(inner.clone()));
        res
    }
}
