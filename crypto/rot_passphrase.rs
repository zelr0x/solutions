// "Playing with passphrases" codewars kata
fn play_pass(s: &str, n: u32) -> String {
    s.char_indices().rev()
        .map(|(i, c)| pass_char(c, n, i % 2 == 0))
        .collect()
}

const fn pass_char(c: char, n: u32, to_upper: bool) -> char {
    match c {
        '0'..='9' => (9 - (c as u8 - b'0') + b'0') as char,
        'a'..='z' | 'A'..='Z' => ascii_rot_fixed_case(c, n, to_upper),
        _ => c
    }
}

const fn ascii_rot_fixed_case(c: char, n: u32, to_upper: bool) -> char {
    let lc = (c as u32 | 0b10_0000).wrapping_sub('a' as u32);
    let ib = ((lc + n) % 26) as u8;
    let b = ib + if to_upper { b'A' } else { b'a' };
    b as char
}
