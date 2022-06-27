package main

import "golang.org/x/tour/reader"

type MyReader struct{}

func (self MyReader) Read(dst []byte) (int, error) {
	for i := range dst {
		dst[i] = 'A'
	}
	return len(dst), nil
}

func main() {
	reader.Validate(MyReader{})
}
