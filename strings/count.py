# Count substring sub in string s.
def count_substring(s: str, sub: str) -> int:
    le = len(s)
    suble = len(sub)
    return (sum(1 for i in range(le - suble + 1) if s[i : i + suble] == sub)
            if le >= suble else 0)
