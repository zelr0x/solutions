use std::io::{self, Write};

fn main() {
    loop {
        print!("n: ");
        io::stdout().flush().unwrap();
        
        let mut n = String::new();
        io::stdin().read_line(&mut n)
            .expect("Failed to read line");

        if n[..1].chars().next().unwrap().eq_ignore_ascii_case(&'q') {
            break;
        }
        
        match n.trim().parse::<u32>() {
            Err(_) => continue,
            Ok(num) => println!("{}", fibonacci(num)),
        };
    };
}

fn fibonacci(n: u32) -> u64 {
    match n {
        0 => 0,
        1 => 1,
        _ => fibonacci(n - 1) + fibonacci(n - 2),
    }
}
