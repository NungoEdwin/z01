package routes

import (
	"log"
	"net/http"
	"strings"

	"github.com/kh3rld/groupie-tracker/backend/handlers"
	"github.com/kh3rld/groupie-tracker/backend/utils"
)

var rts = map[string]bool{
	"/":              true,
	"/api/artists":   true,
	"/api/locations": true,
	"/api/dates":     true,
	"/api/relations": true,
	"/about":         true,
}

func InitRoutes(mux *http.ServeMux) {
	dir := utils.FindProjectRoot("frontend", "static")
	fs := http.FileServer(http.Dir(dir))
	mux.Handle("/static/", http.StripPrefix("/static/", fs))
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		handlers.HomeHandler(w, r)
	})
	mux.HandleFunc("/about", func(w http.ResponseWriter, r *http.Request) {
		handlers.AboutHandler(w, r)
	})
	mux.HandleFunc("/api/artists", utils.GetArtists)
	mux.HandleFunc("/api/dates", utils.GetDates)
	mux.HandleFunc("/api/locations", utils.GetLocations)
	mux.HandleFunc("/api/relations", utils.GetRelations)
}

var validExtensions = []string{".css", ".js", ".jpg", ".png", ".gif", ".svg"}

func RouteChecker(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if strings.HasPrefix(r.URL.Path, "/static/") {
			if !isValidExtension(r.URL.Path, validExtensions) {
				handlers.ForbiddenHandler(w, r)
				return
			}
			next.ServeHTTP(w, r)
			return
		}
		if _, ok := rts[r.URL.Path]; !ok {
			handlers.NotFoundHandler(w, r)
			return
		}
		defer func() {
			if err := recover(); err != nil {
				handlers.InternalServerHandler(w, r)
				log.Println("Recovered from panic:", err)
			}
		}()
		next.ServeHTTP(w, r)
	})
}

// Function to check valid extensions
func isValidExtension(path string, validExtensions []string) bool {
	for _, ext := range validExtensions {
		if strings.HasSuffix(path, ext) {
			return true
		}
	}
	return false
}
