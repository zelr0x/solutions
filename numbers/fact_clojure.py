def fact(x):
    def iterate(count, acc):
        return acc if count == x else iterate(count + 1, acc * count)

    if x < 0:
        return 0
    elif x == 0:
        return 1    
    return iterate(1, x)


print(fact(5))
