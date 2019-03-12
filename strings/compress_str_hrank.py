# Solution to "Compress the String!" Hackerrank problem
# https://www.hackerrank.com/challenges/compress-the-string/problem
# Problem summary: str -> [(k1, c1), ..., (kn, cn)]
# cX - char of str, kX - count of X in str

from itertools import groupby

print(*[(sum(1 for _ in g), int(i)) for i, g in groupby(input())])
