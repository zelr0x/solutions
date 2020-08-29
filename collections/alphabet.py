#!/usr/bin/env python3

from typing import Iterable

UP_A_ORD = ord('A')
UP_Z_ORD = ord('Z')


def up_latin_idx_gen(start: chr, end: chr) -> Iterable[chr]:
    return (i for i in range(ord(start), ord(end) + 1))


def up_latin_letter_gen(start: chr, end: chr) -> Iterable[chr]:
    return (chr(i) for i in up_latin_idx_gen(start, end))


def to_lower(c: chr) -> chr:
    return chr(ord(c) | 32)


def alphabet(start: chr, end: chr) -> Iterable[chr]:
    for up in up_latin_letter_gen(start, end):
        yield (up, to_lower(up))


def print_alphabet(start: chr, end: chr):
    print(', '.join(lt[0] + lt[1] for lt in alphabet(start, end)))


def __prompt_bound(message: str) -> chr:
    while True:
        bound_str = input(message)
        if len(bound_str) != 1:
            print('Please input one character')
            continue

        bound = ord(bound_str[0])
        if bound < UP_A_ORD:
            print(f'Start must be >= {UP_ORD}')
        if bound > UP_Z_ORD:
            print(f'End must be <= {UP_Z_ORD}')
        else:
            return chr(bound)


if __name__ == '__main__':
    start = __prompt_bound('Start: ')
    end = __prompt_bound('End: ')
    print_alphabet(start, end)
