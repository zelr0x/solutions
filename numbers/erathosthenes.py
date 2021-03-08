def erathosthenes(n):
    a = [True] * n
    a[0] = a[1] = False
    for k in range(2, n):
        if a[k]:
            for i in range(k * 2, n, k):
                a[i] = False
    return (k for k in range(n) if a[k])


if __name__ == '__main__':
    import sys

    try:
        n = int(sys.argv[1])
    except Exception as e:
        n = 100
    print(list(erathosthenes(n)))

