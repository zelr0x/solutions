/// https://leetcode.com/problems/remove-k-digits/
impl Solution {    
    pub fn remove_kdigits(num: String, k: i32) -> String {
        let mut k = k as usize;
        let len = num.len();
        if k >= len {
            return default()
        } else if k < 1 {
            return format_result(trim_invalid(&num))
        }
        
        let mut res = String::with_capacity(len - k);        
        for c in num.chars() {
            while !res.is_empty() && k > 0 && should_remove(&res, c) {
                k -= 1;
                res.pop();
            }
            res.push(c);
        }
        res = trim_invalid(&res);
        while !res.is_empty() && k > 0 {
            k -= 1;
            res.pop();
        }
        format_result(res)
    }
}

fn format_result(num: String) -> String {
    if num.is_empty() {
        default()
    } else {
        num
    }
}

fn default() -> String {
    String::from("0")
}

fn should_remove(num: &str, current_char: char) -> bool {
    num.chars().last().unwrap() > current_char
}

fn trim_invalid(num: &str) -> String {
    num.trim_start_matches(|c| !(c >= '1' && c <= '9')).to_string()
}
