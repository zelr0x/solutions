import base64
from collections import deque, namedtuple
from typing import Iterable
from numpy import transpose

from set1.break_single_byte_xor import break_single_byte_xor
from util.hamming_distance import hamming_dist_bit

MIN_KEYSIZE = 2
MAX_KEYSIZE = 40
MAX_KEYSIZE_CANDIDATES = 3


def break_xor(bs: bytes,
              min_keysize: int = MIN_KEYSIZE,
              max_keysize: int = MAX_KEYSIZE):
    Candidate = namedtuple('Candidate', 'hamming_dist, keysize')
    keysize_candidates = deque(maxlen=MAX_KEYSIZE_CANDIDATES)
    for keysize in range(min_keysize, max_keysize + 1):
        first = bs[:keysize]
        second = bs[keysize:(keysize*2)]
        hamming = hamming_dist_bit(first, second)
        normalized_hamming = hamming / keysize
        if (len(keysize_candidates) < MAX_KEYSIZE_CANDIDATES
                or normalized_hamming
                < max(map(lambda c: c.hamming_dist, keysize_candidates))):
            keysize_candidates.append(Candidate(normalized_hamming, keysize))
    for keysize in sorted(map(lambda c: c.keysize, keysize_candidates)):
        break_xor_with_keysize(bs, keysize)


def break_xor_with_keysize(bs: bytes, keysize: int) -> str:
    key = []
    for transposed in transpose(to_chunks(bs, keysize)):
        r = break_single_byte_xor(transposed)
        print(r)
    return ''.join(key)


def to_chunks(bs: bytes, keysize: int) -> Iterable[bytes]:
    return [bs[i:i+keysize] for i in range(0, len(bs), keysize)]


if __name__ == '__main__':
    assert hamming_dist_bit(bytearray('this is a test', 'utf-8'),
                            bytearray('wokka wokka!!!', 'utf-8')) == 37
    filename = '6.txt'
    with open(filename) as f:
        byte_str = base64.b64decode(f.read())
        break_xor(byte_str)
