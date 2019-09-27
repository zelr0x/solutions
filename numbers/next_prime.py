def next_prime(x):
    def is_prime(n):
        return (n == 2 
                or n == 3 
                or (n > 2 
                    and all(n % i != 0 for i in range(2, round(n / 2) + 1))))

    while True:
        x += 1
        if is_prime(x):
            return x
