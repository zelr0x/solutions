#[derive(Copy, Clone)]
pub struct Item {
    pub w: u32,
    pub h: u32,
}

impl Item {
    pub fn new(w: u32, h: u32) -> Self {
        Self {
            w,
            h,
        }
    }
}

/// Select items to maximize the sum of h such that for min and max values of w
/// the given predicate pred(min_w, max_w) is always satisfied.
pub fn max_h_sum_with_w<F>(items: &[Item], pred: F) -> u32
where
    F: Fn(u32, u32) -> bool,
{
    let len = items.len();
    if len == 0 {
        return 0;
    }
    if len == 1 {
        return items[0].h;
    }

    let mut sorted = items.to_vec();
    sorted.sort_by_key(|item| item.w);

    let mut left = 0;
    let mut max_sum = 0;
    
    for right in 0..len {
        let mut min_w = sorted[left].w;
        let max_w = sorted[right].w;
        while left <= right && !pred(min_w, max_w) {
            left += 1;
            min_w = sorted[left].w;
        }
        let span = &sorted[left..=right];
        let span_sum = span.iter().map(|item| item.h).sum();
        if span_sum > max_sum {
            max_sum = span_sum;
        }
    }
    max_sum
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn works() {
        let xs = vec![Item::new(1, 3), Item::new(5, 2), Item::new(3, 2)];
        let got = max_h_sum_with_w(&xs, |min_w, max_w| max_w / min_w < 2);
        assert_eq!(got, 4);
    }
}

