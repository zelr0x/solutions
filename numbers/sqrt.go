package main

import (
	"fmt"
	"math"
)

func Sqrt(x float64) float64 {
	const e = 0.0001
	z := 1.0
	for d := diff(x, z); math.Abs(d) > e; d = diff(x, z) {
		z -= improve(d, z)
	}
	return z
}

func diff(x, guess float64) float64 {
	return guess * guess - x
}

func improve(diff, guess float64) float64 {
	return diff / (2 * guess)
}

func main() {
	fmt.Println(Sqrt(2))
	fmt.Println(math.Sqrt(2))
}

