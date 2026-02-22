//////////////////////////////////////////////////////////////////////
//
// Given is a mock process which runs indefinitely and blocks the
// program. Right now the only way to stop the program is to send a
// SIGINT (Ctrl-C). Killing a process like that is not graceful, so we
// want to try to gracefully stop the process first.
//
// Change the program to do the following:
//   1. On SIGINT try to gracefully stop the process using
//          `proc.Stop()`
//   2. If SIGINT is called again, just kill the program (last resort)
//

package main

import (
    "os"
    "os/signal"
    "syscall"
)

func main() {
	// Create a process
	proc := MockProcess{}

    sigs := make(chan os.Signal, 2)
    defer close(sigs)
    signal.Notify(sigs, syscall.SIGINT, syscall.SIGTERM)

    done := make(chan struct{})
   	go func() {
        defer close(done)
	    // Run the process (blocking)
        proc.Run()
        done <- struct{}{}
    }()
    intRecvd := false
    for {
        select {
        case _, ok := <-sigs:
            if !ok || intRecvd {
                return
            }
            intRecvd = true
            go proc.Stop()
        case <- done:
            return
        }
    }
}
