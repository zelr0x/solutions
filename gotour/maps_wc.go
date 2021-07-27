package main

import (
	"golang.org/x/tour/wc"
	"strings"
)

func WordCount(s string) map[string]int {
	sWords := words(s)
	res := make(map[string]int, len(sWords))
	for _, w := range sWords {
		res[w]++
	}
	return res
}

func words(s string) []string {
	return strings.Fields(s)
}

func main() {
	wc.Test(WordCount)
}
