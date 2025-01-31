package main

import (
	"fmt"
	"net/http"
	"strings"
	"text/template"

	"ascii-web-art/printingasciipackage"
)

// indexHandler handles requests to the root URL and serves the index.html template.
type indexHandler struct{}

func (h *indexHandler) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	if request.Method != "GET" {
		fmt.Println(http.StatusBadRequest)
		return
	}
	tmpl := template.Must(template.ParseFiles("templates/index.html"))
	tmpl.Execute(writer, nil)
}

// generateHandler handles the ASCII art generation based on user input.
type generateHandler struct{}

func (h *generateHandler) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	text := request.FormValue("text")
	banner := request.FormValue("artFile")
	text = strings.ReplaceAll(text, string(rune(13)), "") // Remove carriage return characters.
	if text == "" {
		http.Error(writer, "400 Bad Request", http.StatusBadRequest)
		return
	}
	ap := ""

	switch banner {
	case "standard":
		ap = printingasciipackage.PrintingAscii(text, "standard.txt", "\033[0m", "")
		ap = strings.ReplaceAll(ap, string(rune(13)), "") // Remove carriage return characters for Windows.
	case "shadow":
		ap = printingasciipackage.PrintingAscii(text, "shadow.txt", "\033[0m", "")
		ap = strings.ReplaceAll(ap, string(rune(13)), "") // Remove carriage return characters for Windows.
	case "thinkertoy":
		ap = printingasciipackage.PrintingAscii(text, "thinkertoy.txt", "\033[0m", "")
	}

	if ap == "" { // Handle errors in ASCII art generation.
		http.Error(writer, "500 Internal Server error", http.StatusInternalServerError)
		return
	} else if ap == "1" { // Handle specific error case for bad requests.
		http.Error(writer, "400 Bad Request", http.StatusBadRequest)
		return
	}

	tmpl := template.Must(template.ParseFiles("templates/index.html")) // Parse the HTML template again.
	tmpl.Execute(writer, struct{ Art string }{Art: ap})                // Execute the template with the generated ASCII art.
}

// notFound handles 404 errors for undefined routes.
type notFound struct{}

func (h *notFound) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	http.Error(writer, "404", 404)
}

func main() {
	index := &indexHandler{}
	generate := &generateHandler{}
	notfound := &notFound{}

	// Create a new HTTP server.
	server := http.Server{
		Addr: "127.0.0.1:8080",
	}

	http.HandleFunc("/static/", func(w http.ResponseWriter, r *http.Request) {
		http.ServeFile(w, r, "./static/index.css")
	})
	// Route requests to the appropriate handler based on the URL path.
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path != "/" && r.URL.Path != "/ascii-art" {
			notfound.ServeHTTP(w, r)
		} else {
			index.ServeHTTP(w, r)
		}
	})
	http.Handle("/ascii-art", generate) // Handle ASCII art generation requests.
	http.Handle("/404", notfound)       // Handle 404 errors.

	fmt.Println("Server Initiated at http://127.0.0.1:8080")
	server.ListenAndServe() // Start the server and listen for requests.
}
