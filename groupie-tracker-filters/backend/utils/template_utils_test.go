package utils

import (
	"net/http"
	"net/http/httptest"
	"testing"
	//"errors"
)

func TestLoadTemplates(t *testing.T) {

	err := LoadTemplates()
	if err != nil {
		t.Fatalf("loading templates failed %v", err)
	}
}
func TestRenderTemplate(t *testing.T) {
	template := "about.page.html"
	data := "sasa"
	var err interface{}
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {

		w.Header().Set("Content-Type", "application/json")
		_, _ = w.Write([]byte(data))
		err = RenderTemplate(w, template, data)
		if err != nil {
			t.Fatalf("Rendering %v template failed ", template)
		}
	}))
	defer server.Close()
	if err != nil {
		t.Fatalf("error rendering template %v", err)
	}
}
