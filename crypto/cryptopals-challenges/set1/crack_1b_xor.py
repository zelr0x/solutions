import string
from typing import Dict, Any, Union


def crack_1b_xor(s: str) -> str:
    def xor(a, b, encoding='utf-8'):
        return (bytearray(ba ^ bb for (ba, bb)
                          in zip(bytearray.fromhex(a), bytearray.fromhex(b)))
                ).decode(encoding)

    eng_freq: Dict[Union[str, Any], Union[Union[int, float], Any]] = {
        ' ': 15,     'a': 8.167, 'b': 1.492, 'c': 2.782, 'd': 4.253,
        'e': 12.702, 'f': 2.228, 'g': 2.015, 'h': 6.094, 'i': 6.966,
        'j': 0.153,  'k': 0.772, 'l': 4.025, 'm': 2.406, 'n': 6.749,
        'o': 7.507,  'p': 1.929, 'q': 0.095, 'r': 5.987, 's': 6.327,
        't': 9.056,  'u': 2.758, 'v': 0.978, 'w': 2.36,  'x': 0.150,
        'y': 1.974,  'z': 0.074,
    }

    def possibility(x: str) -> float:
        return sum(eng_freq[c] for c in x if c in eng_freq)

    s = s.casefold()
    alphabet_hex = [hex(ord(c))[2:] for c in string.ascii_letters]
    s_len = len(s)
    s_xor_gen = (xor(s, c * s_len) for c in alphabet_hex)
    possible = {possibility(s_xor): s_xor for s_xor in s_xor_gen}

    return possible[max(possible.keys())]


if __name__ == '__main__':
    i = '1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736'
    print(crack_1b_xor(i))
