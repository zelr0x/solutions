use std::ptr;

pub fn lrot<T>(a: &mut [T], k: usize) {
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

pub fn rrot<T: std::fmt::Debug>(a: &mut [T], k: usize) {
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
    fn lrot_1_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![2, 3, 4, 5, 1];
        lrot(&mut a, 1);
        assert_eq!(a, exp);
    }

    #[test]
    fn lrot_2_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![3, 4, 5, 1, 2];
        lrot(&mut a, 2);
        assert_eq!(a, exp);
    }

    #[test]
    fn rrot_1_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![5, 1, 2, 3, 4];
        rrot(&mut a, 1);
        assert_eq!(a, exp);
    }


    #[test]
    fn rrot_2_works() {
        let mut a = vec![1, 2, 3, 4, 5];
        let exp = vec![5, 4, 1, 2, 3];
        rrot(&mut a, 2);
        assert_eq!(a, exp);
    }
}
