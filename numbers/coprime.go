package main

import (
	"fmt"
)

func coprime(x, y int) bool {
	gcd := func(x, y int) int {
		for y != 0 {
			x, y = y, x % y
		}
		return x
	}
	return gcd(x, y) == 1
}

func main() {
	fmt.Println(coprime(2, 7))
}
