package handlers

import (
	"net/http"

	"github.com/kh3rld/groupie-tracker/backend/utils"
)

// HomeHandler is an HTTP handler function that serves the home page.
func HomeHandler(w http.ResponseWriter, r *http.Request) {
	utils.RenderTemplate(w, "home.page.html", nil)
}

// AboutHandler is an HTTP handler function that serves the about-us page.
func AboutHandler(w http.ResponseWriter, r *http.Request) {
	utils.RenderTemplate(w, "about.page.html", nil)
}

// NotFoundHandler is an HTTP handler function that serves the notfound page.
func NotFoundHandler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusNotFound)
	utils.RenderTemplate(w, "notfound.page.html", nil)
}

// InternalServerHandler is an HTTP handler function that serves the internal server error page.
func InternalServerHandler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusInternalServerError)
	utils.RenderTemplate(w, "error500.page.html", nil)
}

// ForbiddenHandler is an HTTP handler function that serves the forbidden error page.
func ForbiddenHandler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusForbidden)
	utils.RenderTemplate(w, "error403.page.html", nil)
}
