use rand::Rng;
use std::cmp::Ordering;
use std::io::{self, Write}; // cargo.toml [dependencies]: rand = "0.6"

fn main() {
    let mut rng = rand::thread_rng();
    let mut v: Vec<i32> = (0..10).map(|_| rng.gen_range(0, 10)).collect();
    v.sort_unstable();

    let target: i32;
    loop {
        print!("Search for: ");
        io::stdout().flush().unwrap();

        let mut input = String::new();
        io::stdin()
            .read_line(&mut input)
            .expect("Failed to read line");
        target = match input.trim().parse::<i32>() {
            Ok(n) => n,
            Err(_) => continue,
        };
        break;
    }

    println!("V: {:?}", v);
    match b_search(target, &v) {
        None => println!("There is no \"{}\" in V", target),
        Some(res) => println!("First index of {} is {}", target, res),
    }
}

fn b_search(key: i32, v: &Vec<i32>) -> Option<i32> {
    let mut lo = 0;
    let mut hi = (v.len() as i32) - 1;
    while lo <= hi {
        let mid = lo + (hi - lo) / 2;
        let mid_item = v[mid as usize];
        match key.cmp(&mid_item) {
            Ordering::Less => hi = mid - 1,
            Ordering::Greater => lo = mid + 1,
            Ordering::Equal => return Some(mid),
        }
    }
    None
}
