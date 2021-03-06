from typing import Dict


def break_single_byte_xor(s: bytes) -> str:
    def xor(byte: int) -> str:
        return ''.join(chr(x) for x in bytearray(s_byte ^ byte for s_byte in s))

    eng_freq: Dict[str, float] = {
        ' ': 15, 'a': 8.167, 'b': 1.492, 'c': 2.782, 'd': 4.253,
        'e': 12.702, 'f': 2.228, 'g': 2.015, 'h': 6.094, 'i': 6.966,
        'j': 0.153, 'k': 0.772, 'l': 4.025, 'm': 2.406, 'n': 6.749,
        'o': 7.507, 'p': 1.929, 'q': 0.095, 'r': 5.987, 's': 6.327,
        't': 9.056, 'u': 2.758, 'v': 0.978, 'w': 2.36, 'x': 0.150,
        'y': 1.974, 'z': 0.074,
    }

    def possibility(x: str) -> float:
        return sum(eng_freq[c] for c in x if c in eng_freq)

    s_xor_gen = (xor(i) for i in range(256))
    possible = {possibility(s_xor): s_xor for s_xor in s_xor_gen}

    return possible[max(possible.keys())]


if __name__ == '__main__':
    t = '1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736'
    r = break_single_byte_xor(bytearray.fromhex(t))
    print(r)
    assert r == "Cooking MC's like a pound of bacon"
