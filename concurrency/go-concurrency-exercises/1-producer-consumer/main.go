//////////////////////////////////////////////////////////////////////
//
// Given is a producer-consumer scenario, where a producer reads in
// tweets from a mockstream and a consumer is processing the
// data. Your task is to change the code so that the producer as well
// as the consumer can run concurrently
//

package main

import (
	"context"
	"fmt"
	"time"
)

func producer(ctx context.Context, stream Stream) (<-chan *Tweet, <-chan error) {
	ch := make(chan *Tweet, 10)
	errCh := make(chan error, 1)
	go func() {
		defer close(ch)
		defer close(errCh)
		for {
			select {
			case <-ctx.Done():
				return
			default:
    			tweet, err := stream.Next()
	    		if err == ErrEOF {
		    		errCh <- err
			    	return
    			}
	    		select {
		    	case <-ctx.Done():
			    	return
    		    case ch <- tweet:
	    		}
            }
		}
	}()
	return ch, errCh
}

func consumer(ctx context.Context, tweetCh <-chan *Tweet) {
	for {
		select {
		case <-ctx.Done():
			return
		case t, ok := <-tweetCh:
			if !ok {
				return
			}
			if t.IsTalkingAboutGo() {
				fmt.Println(t.Username, "\ttweets about golang")
			} else {
				fmt.Println(t.Username, "\tdoes not tweet about golang")
			}
		}
	}
}

func main() {
	start := time.Now()
	stream := GetMockStream()

	// Producer
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	tweetCh, _ := producer(ctx, stream)

	// Consumer
	consumer(ctx, tweetCh)

	fmt.Printf("Process took %s\n", time.Since(start))
}
