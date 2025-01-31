package utils

import (
	"errors"
	"fmt"
	"html/template"
	"net/http"
	"path/filepath"

	"github.com/kh3rld/groupie-tracker/backend/renders"
)

var (
	TemplateCache map[string]*template.Template
	fn            = template.FuncMap{}
)

// Loads and caches templates
func LoadTemplates() error {
	cache := map[string]*template.Template{}
	baseDir := FindProjectRoot("frontend", "templates")
	tempDir := filepath.Join(baseDir, "*.page.html")
	pages, err := filepath.Glob(tempDir)
	if err != nil {
		return fmt.Errorf("error globbing templates: %v", err)
	}
	for _, page := range pages {
		name := filepath.Base(page)
		ts, err := template.New(name).Funcs(fn).ParseFiles(page)
		if err != nil {
			return fmt.Errorf("error parsing: %s %v ", name, err)
		}
		layoutPath := filepath.Join(baseDir, "*.layout.html")
		matches, err := filepath.Glob(layoutPath)
		if err != nil {
			return fmt.Errorf("error finding layout files %v", err)
		}
		if len(matches) > 0 {
			ts, err = ts.ParseGlob(layoutPath)
			if err != nil {
				return fmt.Errorf("error parsing layout files: %v", err)
			}
		}
		cache[name] = ts
	}
	TemplateCache = cache
	return nil
}

// Renders HTML template from the cache
func RenderTemplate(w http.ResponseWriter, tmpl string, data interface{}) error {
	t, ok := TemplateCache[tmpl]
	if !ok {
		renders.RenderServerErrorTemplate(w, http.StatusNotFound, fmt.Sprintf("Template %s not found", tmpl))
		return errors.New("template not found")
	}
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	err := t.Execute(w, data)
	if err != nil {
		renders.RenderServerErrorTemplate(w, http.StatusInternalServerError, fmt.Sprintf("Error rendering template: %v", err))
		return errors.New("error rendering template")
	}
	return nil
}
