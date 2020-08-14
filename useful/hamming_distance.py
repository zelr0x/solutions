from typing import Iterable, TypeVar
from itertools import chain


def s_hamming_dist_bit(a: str, b: str, encoding='utf-8') -> int:
    return hamming_dist_bit(a.encode(encoding), b.encode(encoding))


def hamming_dist_bit(a: Iterable[int], b: Iterable[int]) -> int:
    a_bits = to_bits(a)
    b_bits = to_bits(b)
    return sum(1 for (ab, bb) in zip(a_bits, b_bits) if ab != bb)


def to_bits(items: Iterable[int]) -> Iterable[int]:
    return flatten(byte_bits(item) for item in items)


def byte_bits(c: int) -> Iterable[int]:
    return ((c >> x) & 1 for x in range(7, -1, -1))


T = TypeVar('T')
def flatten(seq: Iterable[Iterable[T]]) -> Iterable[T]:
    return chain.from_iterable(seq)


if __name__ == '__main__':
    hd_t = s_hamming_dist_bit('this is a test', 'wokka wokka!!!')
    print(f'Hamming distance calculated: {hd_t}, expected: 37')
