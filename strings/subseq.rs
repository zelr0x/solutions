use std::str::Utf8Error;

fn substrings(s: &str) -> Vec<Result<&str, Utf8Error>> {
    subsequences(s.as_bytes()).iter()
        .map(|ss| std::str::from_utf8(*ss))
        .collect()
}

fn ascii_substrings(s: &str) -> Vec<&str> {
    subsequences(s.as_bytes()).iter()
        .map(|ss| unsafe { std::str::from_utf8_unchecked(*ss) })
        .collect()
}

fn subsequences(bytes: &[u8]) -> Vec<&[u8]> {
    let len = bytes.len();
    let mut res = Vec::with_capacity(len.pow(2));
    for i in 0..len {
        for j in (i + 1)..=len {
            res.push(&bytes[i..j])
        }
    }
    res
}

fn main() {
    println!("{:?}", substrings("abcd"));
    println!("{:?}", ascii_substrings("abcd"));
}
