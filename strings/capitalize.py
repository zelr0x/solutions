# Capitalize first letter of each word. 
# Works with arbitrary number of spaces.
def capitalize(s: str) -> str:
    return (s[0].upper() + ''.join(s[i].upper() if s[i-1].isspace() else s[i]
            for i in range(1, len(s)))
            if len(s) > 0 else s)
