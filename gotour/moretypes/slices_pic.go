package main

import (
	"golang.org/x/tour/pic"
	"math"
)

// https://tour.golang.org/moretypes/18
func Pic(dx, dy int) [][]uint8 {
	res := make([][]uint8, dy)
	for i := 0; i < dy; i++ {
		res[i] = make([]uint8, dx)
		for j := 0; j < dx; j++ {
			res[i][j] = interestingFuncB(uint8(i), uint8(j))
		}
	}
	return res
}

func interestingFuncA(x, y uint8) uint8 {
	return (x + y) / 2
}

func interestingFuncB(x, y uint8) uint8 {
	return x * y
}

func interestingFuncC(x, y uint8) uint8 {
	return uint8(math.Pow(float64(x), float64(y)))
}

func main() {
	pic.Show(Pic)
}
