package main

import (
	"bufio"
	"io"
	"os"
	"strings"
)

type rot13Reader struct {
	r io.Reader
}

func (self rot13Reader) Read(dst []byte) (n int, err error) {
	br := asByteReader(self.r, cap(dst))
	for i := range dst {
		b, err := br.ReadByte()
		if err != nil {
			return len(dst), err
		}
		dst[i] = rotAscii(b, 13)
	}
	return len(dst), nil
}

func asByteReader(rd io.Reader, size int) io.ByteReader {
	if byteReader, ok := rd.(io.ByteReader); ok {
		return byteReader
	}
	return bufio.NewReaderSize(rd, size)
}

func rotAscii(b byte, n uint) byte {
	upper := isAsciiUpper(b)
	if !(upper || isAsciiLower(b)) {
		return b
	}
	lcIdx := asciiLetterToIndex(b)
	lcIdxRot := byte((uint(lcIdx) + n) % 26)
	var offset byte
	if upper {
		offset = 'A'
	} else {
		offset = 'a'
	}
	return lcIdxRot + offset
}

// asciiLetterToIndex converts ascii letter to the index in the alphabet.
func asciiLetterToIndex(b byte) byte {
	return b | ' ' - 'a'
}

func isAsciiLower(b byte) bool {
	return b >= 'a' && b <= 'z'
}

func isAsciiUpper(b byte) bool {
	return b >= 'A' && b <= 'Z'
}

func main() {
	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
}
