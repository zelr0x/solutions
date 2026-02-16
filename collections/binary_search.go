package main

const binSearchCutoff = 16

func BinSearchInt(a []int, target int) int {
	lo := 0
	hi := len(a)
	for lo < hi {
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
		aMid := a[mid]
		if aMid == target {
			return mid
		}
		if aMid < target {
			lo = mid + 1
		} else if aMid > target {
			hi = mid
		}
	}
	return -1
}
