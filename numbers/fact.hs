-- Recursive
factorial n = if n < 2 then n else n * (fact . pred) n

-- Iterative
factorial n = product [1 .. n]
