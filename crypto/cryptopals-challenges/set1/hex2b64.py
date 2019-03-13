import base64


def hex2b64(s: str, encoding: str='utf-8') -> str:
    return base64.b64encode(bytearray.fromhex(s)).decode(encoding)


def test_hex2b64():
    test_in = ('49276d206b696c6c696e6720796f757220627261696e206c696b6520612070'
               '6f69736f6e6f7573206d757368726f6f6d')
    expected_out = (
        'SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t')
    res = hex2b64(test_in)
    assert res == expected_out


if __name__ == '__main__':
    print('Running tests...')
    test_hex2b64()
    print('OK. Passed all tests')
