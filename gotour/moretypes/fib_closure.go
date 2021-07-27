package main

import "fmt"

// fibonacci is a function that returns
// a function that returns an int.
func fibonacci() func() int {
	x, y := 0, 1
	return func() int {
		x, y = y, y + x
		return y - x
	}
}

// fun with defers, could probably be better, but whatever :)
func fibonacciDefer() func() int {
	prev := -1
	next := 1
	assign := func(target *int, v int) {
		*target = v
	}
	return func() int {
		if (prev == -1) {
			prev = 0
			return prev
		}
		defer assign(&prev, next)
		defer assign(&next, next + prev)
		return next
	}
}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}
