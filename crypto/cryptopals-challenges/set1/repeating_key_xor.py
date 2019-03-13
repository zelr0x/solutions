from itertools import cycle


def repeated_xor(s: str, k: str, enc: str='utf-8') -> str:
    return (bytearray(bs ^ bk for (bs, bk)
                      in zip(bytearray(s, enc), cycle(bytearray(k, enc))))
            ).hex()


def test_repeated_xor():
    test_in = ("Burning 'em, if you ain't quick and nimble\n"
               "I go crazy when I hear a cymbal")
    key = "ICE"
    expected_out = (
        "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
        "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f")
    res = repeated_xor(test_in, key)
    assert (res == expected_out)


if __name__ == "__main__":
    print("Running tests...")
    test_repeated_xor()
    print("OK. Passed all tests")
