package main

import (
	"context"
	"fmt"
	"os"
	"os/signal"
	"syscall"
	
	"github.com/libp2p/go-libp2p"
)

func main() {

	// create a bckgrnd context (non-cancelling)
	ctx := context.Background()
	
	// start a default libp2p node
	node, err := libp2p.New(ctx)
	if err != nil {
		panic(err)
	}
	
	// printing the node's listening addresses
	fmt.Println("listening addresses:", node.Addrs())
	
	// wait for a SIGINT or SIGTERM signal
	ch := make(chan os.Signal, 1)
	signal.Notify(ch, syscall.SIGINT, syscall.SIGTERM)
	<-ch
	fmt.Println("Received Signal, shutting down...")
	
	
	// shut the node down
	if err := node.Close(); err!= nil {
		panic(err)
	}
}
