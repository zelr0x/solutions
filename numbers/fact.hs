-- Iterative
factorial x = product [x, x-1 .. 1]

-- Recursive
fact x = if x < 2 then x else x * (fact . pred) x
