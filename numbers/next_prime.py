def next_prime(x):
    def is_prime(n):
        if n == 2 or n == 3:
            return True
        elif n < 2 or any(n % i == 0 for i in range(2, round(n / 2) + 1)):
            return False
        return True

    while True:
        x += 1
        if is_prime(x):
            return x
