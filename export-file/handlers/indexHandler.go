package handlers

import (
	"fmt"
	"net/http"
	"text/template"
)

var templ *template.Template

func init() {
	var err error
	templ, err = template.ParseGlob("./templates/*.html")
	if err != nil {
		fmt.Println("Error parsing template:", err.Error())
		return
	}
}

func IndexHandler(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path != "/" {
		ErrorPage(w, r, http.StatusNotFound, "Page Not Found")
		fmt.Println("Page Not Found")
		return
	}

	err := templ.ExecuteTemplate(w, "index.html", nil)
	if err != nil {
		ErrorPage(w, r, http.StatusNotFound, "Error rendering index template")
		fmt.Println("Error rendering index template")
	}
}
