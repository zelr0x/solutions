/// https://leetcode.com/problems/single-element-in-a-sorted-array/
impl Solution {
    pub fn single_non_duplicate(nums: Vec<i32>) -> i32 {
        let len = nums.len();
        if len == 0 {
            panic!("Input vector should not be empty")
        } else if len == 1 {
            return nums[0]
        }

        let mut curr_count = 0;
        let last_idx = len - 1;
        for i in 0..last_idx {
            let n = nums[i];
            let next_idx = i + 1;
            let next = nums[next_idx];
            if n != next {
                if curr_count == 0 {
                    return n
                } else if next_idx == last_idx {
                    return next
                } else {
                    curr_count = 0;
                }
            } else {
                curr_count += 1;
            }
        }
        panic!("Input vector contains only duplicates")
    }
}
