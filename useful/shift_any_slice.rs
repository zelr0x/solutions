use std::ptr;

pub fn lshift<T>(a: &mut [T], k: usize) {
    let n = a.len();
    let k = k % n;
    if k == 0 {
        return
    }
    let mut t = Vec::with_capacity(k);
    unsafe {
        ptr::copy_nonoverlapping(a.as_ptr(), t.as_mut_ptr(), k);
        let c = n-k;
        ptr::copy(a.as_ptr().add(k), a.as_mut_ptr(), c);
        ptr::copy_nonoverlapping(t.as_ptr(), a.as_mut_ptr().add(c), k);
    }
}

pub fn rshift<T: std::fmt::Debug>(a: &mut [T], k: usize) {
    let n = a.len();
    let k = k % n;
    if k == 0 {
        return
    }
    let mut t = Vec::with_capacity(k);

    unsafe {
        let c = n-k;
        for i in (c..n).rev() {
            t.push(ptr::read(&a[i]));
        }

        ptr::copy(a.as_ptr(), a.as_mut_ptr().add(k), c);
        ptr::copy_nonoverlapping(t.as_ptr(), a.as_mut_ptr(), k);
    }
}


#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn lshift_1_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![2, 3, 4, 5, 1];
        lshift(&mut a, 1);
        assert_eq!(a, exp);
    }

    #[test]
    fn lshift_2_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![3, 4, 5, 1, 2];
        lshift(&mut a, 2);
        assert_eq!(a, exp);
    }

    #[test]
    fn rshift_1_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![5, 1, 2, 3, 4];
        rshift(&mut a, 1);
        assert_eq!(a, exp);
    }


    #[test]
    fn rshift_2_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![5, 4, 1, 2, 3];
        rshift(&mut a, 2);
        assert_eq!(a, exp);
    }
}
