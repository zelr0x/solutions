# str.swapcase() was not allowed for obvious reasons.
def swapcase(s):
    return ''.join((x.upper() if x.islower() else x.lower() for x in s))
