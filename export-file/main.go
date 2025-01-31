package main

import (
	"fmt"
	"net/http"
	"os"

	"export/router"
)

func main() {
	if len(os.Args) > 1 {
		fmt.Println("Usage: [go run .]")
		return
	}

	router.Router()

	server := http.Server{
		Addr:    "127.0.0.1:5000",
		Handler: nil,
	}

	fmt.Println("Server running at http://localhost:5000")
	err := server.ListenAndServe()
	if err != nil {
		fmt.Printf("Error starting the server %v", err.Error())
	}
}
