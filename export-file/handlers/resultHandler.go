package handlers

import (
	"fmt"
	"net/http"
	"strconv"

	"export/asciiart"
)

type WebData struct {
	Text   string
	Banner string
	Art    string
}

// Result Handler function
func ResultHandler(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path != "/ascii-art" {
		ErrorPage(w, r, http.StatusNotFound, "Page Not Found")
		fmt.Println("Page Not Found")
		return
	}

	if r.Method == http.MethodPost {
		err := r.ParseForm()
		if err != nil {
			ErrorPage(w, r, http.StatusInternalServerError, "Internal Server Error")
			fmt.Println("Internal Server Error")
			return
		} else {
			// Getting form data
			text := r.FormValue("text")
			bannerType := r.FormValue("banner")
			action := r.FormValue("action")

			if len(text) == 0 || len(bannerType) == 0 {
				ErrorPage(w, r, http.StatusBadRequest, "Bad Request")
				fmt.Println("Bad Request")
				return
			}

			characters, err := asciiart.ReadFile(bannerType)
			if err != nil {
				ErrorPage(w, r, http.StatusInternalServerError, "Internal Server Error")
				fmt.Println("Internal Server Error")
				return
			}

			art, err := asciiart.GenerateAsciiArt(text, characters)
			if err != nil {
				ErrorPage(w, r, http.StatusBadRequest, "Bad Request")
				fmt.Println("Bad Request")
				return
			}
			// Handle the dowload action
			if action == "download" {
				w.Header().Set("Content-Disposition", "attachment; filename=ascii-art.txt")
				w.Header().Set("Content-Type", "text/plain")
				w.Header().Set("Content-Length", strconv.Itoa(len(art)))
				w.WriteHeader(http.StatusOK)
				w.Write([]byte(art))
				return
			}

			data := WebData{
				Art:    art,
				Text:   text,
				Banner: bannerType,
			}

			if err == nil {
				templ.ExecuteTemplate(w, "index.html", data)
				w.WriteHeader(http.StatusOK)
			} else {
				ErrorPage(w, r, http.StatusInternalServerError, "Error rendering template")
				fmt.Println("Error rendering template")
				return
			}
		}
	} else {
		ErrorPage(w, r, http.StatusInternalServerError, "Internal Server Error")
		fmt.Println("Internal Server Error")
		return
	}
}
