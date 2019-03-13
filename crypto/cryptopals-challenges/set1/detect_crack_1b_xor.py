from typing import Dict, Any, Union


def possibility(s: str) -> float:
    eng_freq: Dict[Union[str, Any], Union[Union[int, float], Any]] = {
        ' ': 15, 'a': 8.167, 'b': 1.492, 'c': 2.782, 'd': 4.253,
        'e': 12.702, 'f': 2.228, 'g': 2.015, 'h': 6.094, 'i': 6.966,
        'j': 0.153, 'k': 0.772, 'l': 4.025, 'm': 2.406, 'n': 6.749,
        'o': 7.507, 'p': 1.929, 'q': 0.095, 'r': 5.987, 's': 6.327,
        't': 9.056, 'u': 2.758, 'v': 0.978, 'w': 2.36, 'x': 0.150,
        'y': 1.974, 'z': 0.074,
    }
    return sum(eng_freq[c] for c in s if c in eng_freq)


def crack_1b_xor(s: str) -> str:
    def xor(byte: int) -> str:
        return ''.join(chr(x) for x in bytearray(s_byte ^ byte for s_byte
                                                 in bytearray.fromhex(s)))

    s_xor_gen = (xor(i) for i in range(256))
    possible = {possibility(s_xor): s_xor for s_xor in s_xor_gen}

    return possible[max(possible.keys())]


if __name__ == '__main__':
    filename = '4.txt'
    line_prob = {}
    with open(filename, 'r') as file:
        for line in file:
            cracked = crack_1b_xor(line)
            line_prob[possibility(cracked)] = cracked

    print(line_prob[max(line_prob.keys())])
