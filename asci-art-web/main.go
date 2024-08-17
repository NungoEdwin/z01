package main

import (
	"fmt"
	"net/http"
	"os"
	"strings"
	"text/template"

	"ascii-art-color/printingasciipackage"
)

type indexHandler struct{}

func (h *indexHandler) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	if request.Method != "GET" {
		fmt.Println(http.StatusBadRequest)
		return
	}
	tmpl := template.Must(template.ParseFiles("templates/index.html"))
	tmpl.Execute(writer, nil)
}

type generateHandler struct{}

func (h *generateHandler) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	text := request.FormValue("text")
	banner := request.FormValue("artFile")
	text = strings.ReplaceAll(text, string(rune(13)), "")
	if text == "" {
		http.Error(writer, "400 Bad Request", http.StatusBadRequest)
		return
	}
	ap := ""

	switch banner {
	case "standard":
		ap = printingasciipackage.PrintingAscii(text, "standard.txt", "\033[0m", "")
		err := os.WriteFile("result.txt", []byte(ap), 0o600)
		if err != nil {
			fmt.Fprint(writer, err.Error())
		}
	case "shadow":
		ap = printingasciipackage.PrintingAscii(text, "shadow.txt", "\033[0m", "")
		err := os.WriteFile("result.txt", []byte(ap), 0o600)
		if err != nil {
			fmt.Fprint(writer, err.Error())
		}

	case "thinkertoy":
		ap = printingasciipackage.PrintingAscii(text, "thinkertoy.txt", "\033[0m", "")
		err := os.WriteFile("result.txt", []byte(ap), 0o600)
		if err != nil {
			fmt.Fprint(writer, err.Error())
		}
	}
	if ap == "" {
		http.Error(writer, "500 Internal Server error", http.StatusInternalServerError)
		return
	} else if ap == "1" {
		http.Error(writer, "400 Bad Request", http.StatusBadRequest)
		return
	}
	// asciiArt := text + banner

	// res,_ := http.Get("http:localhost/result.txt")

	tmpl := template.Must(template.ParseFiles("templates/ascii-art.html"))
	tmpl.Execute(writer, struct{ Art string }{Art: ap})
}

type notFound struct{}

func (h *notFound) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	http.Error(writer, "404", 404)
}

func main() {
	index := &indexHandler{}
	generate := &generateHandler{}
	notfound := &notFound{}
	server := http.Server{
		Addr: "127.0.0.1:8080",
	}
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path != "/" && r.URL.Path != "/ascii-art" {
			notfound.ServeHTTP(w, r)
		} else {
			index.ServeHTTP(w, r)
		}
	})
	http.Handle("/ascii-art", generate)
	http.Handle("/404", notfound)

	// http.HandleFunc("/",func(w http.ResponseWriter, r *http.Request) {
	// 	if r.URL.Path == "/"{
	// 		index.ServeHTTP(w,r)
	// 	}
	// 	if r.URL.Path != "/" && r.URL.Path != "/ascii-art"{
	// 		notfound.ServeHTTP(w, r)
	// 	}
	// })
	// http.HandleFunc("/", indexHandler)
	// http.HandleFunc("/ascii-art", generateHandler)
	fmt.Println("Server Initiated")
	server.ListenAndServe()
}
