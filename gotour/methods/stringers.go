package main

import "fmt"

type IPAddr [4]byte

func (self IPAddr) String() string {
	return fmt.Sprintf("%d.%d.%d.%d", self[0], self[1], self[2], self[3])
}

func main() {
	hosts := map[string]IPAddr{
		"loopback":  {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}
