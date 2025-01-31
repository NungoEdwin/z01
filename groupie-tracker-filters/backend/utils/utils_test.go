package utils

import (
	// "encoding/json"
	// "errors"
	"net/http"
	"net/http/httptest"
	"testing"
)

// Example struct to unmarshal into
type MockData struct {
	ID   int    `json:"id"`
	Name string `json:"name"`
}

func TestFetchAndUnmarshal(t *testing.T) {
	// Set up a test HTTP server that mocks an API
	mockResponse := `{"id": 1, "name": "Test User"}`
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path != "/test-endpoint" {
			t.Errorf("Expected to request '/test-endpoint', got: %s", r.URL.Path)
		}
		w.Header().Set("Content-Type", "application/json")
		_, _ = w.Write([]byte(mockResponse))
	}))
	defer server.Close()

	var data MockData

	err := FetchAndUnmarshal(server.URL+"/test-endpoint", &data)
	if err != nil {
		t.Fatalf("Expected no error, got %v", err)
	}

	expectedData := MockData{ID: 1, Name: "Test User"}
	if data != expectedData {
		t.Errorf("Expected %+v, got %+v", expectedData, data)
	}

	t.Run("Invalid URL", func(t *testing.T) {
		err := FetchAndUnmarshal("http://invalid-url", &data)
		if err == nil {
			t.Fatalf("Expected error for invalid URL, got none")
		}
	})

	t.Run("Non-200 Status Code", func(t *testing.T) {
		mockServer := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.WriteHeader(http.StatusNotFound) // Simulate 404
		}))
		defer mockServer.Close()

		err := FetchAndUnmarshal(mockServer.URL+"/test-endpoint", &data)
		if err == nil {
			t.Fatalf("Expected error for non-200 status code, got none")
		}
	})

	t.Run("Invalid JSON Response", func(t *testing.T) {
		mockServer := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set("Content-Type", "application/json")
			_, _ = w.Write([]byte(`{invalid json`)) // Simulate invalid JSON
		}))
		defer mockServer.Close()

		err := FetchAndUnmarshal(mockServer.URL+"/test-endpoint", &data)
		if err == nil {
			t.Fatalf("Expected error for invalid JSON response, got none")
		}
	})

	t.Run("Error Reading Response Body", func(t *testing.T) {
		mockServer := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set("Content-Type", "application/json")
			// Simulate an error while writing to the response
			http.Error(w, "Server error", http.StatusInternalServerError)
		}))
		defer mockServer.Close()

		err := FetchAndUnmarshal(mockServer.URL+"/test-endpoint", &data)
		if err == nil {
			t.Fatalf("Expected error for reading response body, got none")
		}
	})
}
