def fixed_xor_hex_str(a: str, b: str) -> str:
    return fixed_xor(bytearray.fromhex(a), bytearray.fromhex(b)).hex()


def fixed_xor(a: bytes, b: bytes) -> bytes:
    return bytearray(ba ^ bb for (ba, bb) in zip(a, b))


def test_fixed_xor():
    test_in_1 = '1c0111001f010100061a024b53535009181c'
    test_in_2 = '686974207468652062756c6c277320657965'
    expected_out = '746865206b696420646f6e277420706c6179'
    res = fixed_xor_hex_str(test_in_1, test_in_2)
    assert (res == expected_out)


if __name__ == '__main__':
    test_fixed_xor()
