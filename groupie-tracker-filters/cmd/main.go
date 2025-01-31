package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/kh3rld/groupie-tracker/backend/routes"
	"github.com/kh3rld/groupie-tracker/backend/utils"
)

func main() {
	wrappedRouter, err := setup()
	if err != nil {
		log.Fatalf("Setup Failed to initialize, %v", err)
	}
	server := &http.Server{
		Addr:    ":8080",
		Handler: wrappedRouter,
	}
	fmt.Println("Server listening on http://localhost:8080")
	if err := server.ListenAndServe(); err != nil {
		log.Fatal(err)
	}
}

func setup() (http.Handler, error) {
	if err := utils.LoadTemplates(); err != nil {
		return nil, fmt.Errorf("error loading templates: %w", err)
	}
	r := http.NewServeMux()
	routes.InitRoutes(r)

	wrapper := routes.RouteChecker(r)
	return wrapper, nil
}
