package main

import (
	"context"
	"fmt"
	"os"
	"os/signal"
	"syscall"
	
	"github.com/libp2p/go-libp2p"
	peerstore "github.com/libp2p/go-libp2p-core/peer"
	"github.com/libp2p/go-libp2p/p2p/protocol/ping"
)

func main() {

	// create a bckgrnd context (non-cancelling)
	ctx := context.Background()
	
	// start a default libp2p node
	node, err := libp2p.New(ctx,
		libp2p.ListenAddrStrings("/ip4/127.0.0.1/tcp/0"),
		libp2p.Ping(false),
	)
	if err != nil {
		panic(err)
	}
	
	// configuring our our own ping protocol
	pingService := &ping.PingService{Host: node}
	node.SetStreamHandler(ping.ID, pingService.PingHandler)
	
	// printing the node's PeerInfo in the multiaddr format
	peerInfo := peerstore.AddrInfo{
		ID: node.ID(),
		Addrs: node.Addrs(),
	}
	addrs, err := peerstore.AddrInfoToP2pAddrs(&peerInfo)
	fmt.Println("libp2p node address:", addrs[0])
	
	// printing the node's listening addresses
//	fmt.Println("listening addresses:", node.Addrs())
	
	// wait for a SIGINT or SIGTERM signal
//	ch := make(chan os.Signal, 1)
//	signal.Notify(ch, syscall.SIGINT, syscall.SIGTERM)
//	<-ch
//	fmt.Println("Received Signal, shutting down...")
	
	
	// shut the node down
	if err := node.Close(); err!= nil {
		panic(err)
	}
}
