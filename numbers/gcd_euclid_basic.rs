pub fn gcd(p: isize, q: isize) -> isize {
    match q {
        0 => p,
        _ => gcd(q, p % q),
    }
}
