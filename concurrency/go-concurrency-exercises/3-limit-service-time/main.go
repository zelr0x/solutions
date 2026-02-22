//////////////////////////////////////////////////////////////////////
//
// Your video processing service has a freemium model. Everyone has 10
// sec of free processing time on your service. After that, the
// service will kill your process, unless you are a paid premium user.
//
// Beginner Level: 10s max per request
// Advanced Level: 10s max per user (accumulated)
//

package main

import (
    "sync"
    "time"
)

// User defines the UserModel. Use this to check whether a User is a
// Premium user or not
type User struct {
	ID        int
	IsPremium bool
	TimeUsed  int64 // in seconds
    L         sync.RWMutex
}

// HandleRequest runs the processes requested by users. Returns false
// if process had to be killed
func HandleRequest(process func(), u *User) bool {
    done := make(chan struct{})
    go func() {
        defer close(done)
        start := time.Now()
    	process()
        elapsedSec := int64(time.Since(start) / time.Second)
        u.L.Lock()
        u.TimeUsed += elapsedSec
        u.L.Unlock()
    }()
    u.L.RLock()
    isPrem := u.IsPremium
    u.L.RUnlock()
    for {
        select {
        case <-done:
            return true
        case <-time.After(10 * time.Second):
            if !isPrem {
                return false
            }
            <-done
            return true
        }
    }
}

func main() {
	RunMockServer()
}

