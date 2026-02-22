package main

const binSearchCutoff = 16

func BinSearchInt(a []int, target int) int {
	lo := 0
	hi := len(a)
	for {
		n := hi - lo
		if n <= binSearchCutoff {
			for i, v := range a[lo:hi] {
				if v == target {
					return lo + i
				}
			}
			return -1
		}
		mid := lo + n/2
		v := a[mid]
		switch {
		case v < target:
			lo = mid + 1
		case v > target:
			hi = mid
		default:
			return mid
		}
	}
}
