package router

import (
	"net/http"

	"export/handlers"
)

func Router() {
	http.Handle("/static/", http.StripPrefix("/static", http.FileServer(http.Dir("./static"))))
	http.HandleFunc("/", handlers.IndexHandler)
	http.HandleFunc("/ascii-art", handlers.ResultHandler)
}
