package routes

import (
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

// Mock NotFoundHandler for testing
func NotFoundHandler(w http.ResponseWriter, r *http.Request) {
	http.Error(w, "404 not found", http.StatusNotFound)
}

// Test the RouteChecker middleware
func TestRouteChecker(t *testing.T) {
	rts["/valid-route"] = true

	defer delete(rts, "/valid-route") // Clean up after test
	defer delete(rts, "/invalid-route")

	next := http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("OK"))
	})

	handler := RouteChecker(next)

	t.Run("Valid route", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodGet, "/valid-route", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("Expected status code %d, got %d", http.StatusOK, status)
		}
		if body := rr.Body.String(); body != "OK" {
			t.Errorf("Expected body 'OK', got %q", body)
		}
	})

	t.Run("Invalid route", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodGet, "/invalid-route", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNotFound {
			t.Errorf("Expected status code %d, got %d", http.StatusNotFound, status)
		}

		expectedBody :=
			"\n\t<html><head>\n<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n<title>404 - Page Not Found | Groupie Trackers</title>\n<style>\n  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');\n\n  :root {\n    --bg-color: #0a2a4a;\n    --text-color: #f0f0f0;\n    --accent-color: #ffd700;\n    --button-bg: #1c5d99;\n    --button-hover: #3a7ca5;\n  }\n\n  * {\n    box-sizing: border-box;\n    margin: 0;\n    padding: 0;\n  }\n\n  body {\n    font-family: 'Roboto', sans-serif;\n    background-color: var(--bg-color);\n    color: var(--text-color);\n    display: flex;\n    flex-direction: column;\n    justify-content: center;\n    align-items: center;\n    height: 100vh;\n    overflow: hidden;\n  }\n\n  .container {\n    text-align: center;\n    padding: 2rem;\n    max-width: 600px;\n  }\n\n  h1 {\n    font-family: 'Roboto', sans-serif;\n    font-size: 4rem;\n    font-weight: 700;\n    color: var(--accent-color);\n    margin-bottom: 1rem;\n    text-shadow: 0 0 10px rgba(255, 215, 0, 0.5);\n  }\n\n  p {\n    font-size: 1.2rem;\n    margin-bottom: 2rem;\n  }\n\n  .btn {\n    display: inline-block;\n    padding: 0.8rem 1.5rem;\n    background-color: var(--button-bg);\n    color: var(--text-color);\n    text-decoration: none;\n    border-radius: 25px;\n    font-family: 'Roboto', sans-serif;\n    font-weight: 700;\n    transition: background-color 0.3s ease, transform 0.3s ease;\n  }\n\n  .btn:hover {\n    background-color: var(--button-hover);\n    transform: translateY(-2px);\n  }\n\n  .search-container {\n    margin-top: 2rem;\n  }\n\n  .search-input {\n    padding: 0.5rem;\n    width: 200px;\n    border: none;\n    border-radius: 25px 0 0 25px;\n    font-family: 'Roboto', sans-serif;\n  }\n\n  .search-btn {\n    padding: 0.5rem 1rem;\n    background-color: var(--accent-color);\n    color: var(--bg-color);\n    border: none;\n    border-radius: 0 25px 25px 0;\n    cursor: pointer;\n    font-family: 'Roboto', sans-serif;\n    font-weight: 700;\n  }\n\n  .error-illustration {\n    margin-bottom: 2rem;\n  }\n\n  .star {\n    position: absolute;\n    background-color: var(--accent-color);\n    border-radius: 50%;\n    animation: twinkle 2s infinite alternate;\n  }\n\n  @keyframes twinkle {\n    0% { opacity: 0.2; }\n    100% { opacity: 1; }\n  }\n\n  @media (max-width: 768px) {\n    h1 {\n      font-size: 3rem;\n    }\n\n    p {\n      font-size: 1rem;\n    }\n\n    .search-input {\n      width: 150px;\n    }\n  }\n</style>\n</head>\n<body>\n  <div class=\"container\">\n    <svg class=\"error-illustration\" width=\"200\" height=\"200\" viewBox=\"0 0 200 200\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n      <circle cx=\"100\" cy=\"100\" r=\"90\" stroke=\"#FFD700\" stroke-width=\"4\"/>\n      <path d=\"M60 70L140 130\" stroke=\"#FFD700\" stroke-width=\"4\"/>\n      <path d=\"M140 70L60 130\" stroke=\"#FFD700\" stroke-width=\"4\"/>\n      <circle cx=\"70\" cy=\"80\" r=\"10\" fill=\"#FFD700\"/>\n      <circle cx=\"130\" cy=\"80\" r=\"10\" fill=\"#FFD700\"/>\n      <path d=\"M70 130Q100 150 130 130\" stroke=\"#FFD700\" stroke-width=\"4\" fill=\"none\"/>\n    </svg>\n\n    <h1>404</h1>\n    <p>Oops! Looks like this track doesn't exist in our playlist.</p>\n    <a href=\"/\" class=\"btn\">Return to Homepage</a>\n  </div>\n\n</body>\n</html>\n\t"
		if body := rr.Body.String(); !strings.Contains(body, expectedBody) {
			t.Errorf("Expected body to contain %q, got %q", expectedBody, body)
		}
	})

	t.Run("Static route", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodGet, "/static/file.css", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("Expected status code %d, got %d", http.StatusOK, status)
		}
		if body := rr.Body.String(); body != "OK" {
			t.Errorf("Expected body 'OK', got %q", body)
		}
	})

	t.Run("Empty rts map", func(t *testing.T) {
		// Clear the rts map for this test
		for k := range rts {
			delete(rts, k)
		}

		req := httptest.NewRequest(http.MethodGet, "/some-route", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNotFound {
			t.Errorf("Expected status code %d, got %d", http.StatusNotFound, status)
		}
	})

	// t.Run("Non-GET method on valid route", func(t *testing.T) {
	// 	req := httptest.NewRequest(http.MethodPost, "/valid-route", nil)
	// 	rr := httptest.NewRecorder()

	// 	handler.ServeHTTP(rr, req)

	// 	if status := rr.Code; status != http.StatusOK {
	// 		t.Errorf("Expected status code %d, got %d", http.StatusOK, status)
	// 	}
	// })

	t.Run("Non-GET method on invalid route", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodPost, "/invalid-route", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNotFound {
			t.Errorf("Expected status code %d, got %d", http.StatusNotFound, status)
		}
	})

	t.Run("Very long route", func(t *testing.T) {
		longRoute := "/" + strings.Repeat("a", 10000)
		req := httptest.NewRequest(http.MethodGet, longRoute, nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNotFound {
			t.Errorf("Expected status code %d, got %d", http.StatusNotFound, status)
		}
	})

	t.Run("Next handler failure", func(t *testing.T) {
		next := http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			http.Error(w, "500 internal server error", http.StatusInternalServerError)
		})

		handler := RouteChecker(next)
		req := httptest.NewRequest(http.MethodGet, "/valid-route", nil)
		rr := httptest.NewRecorder()

		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNotFound {
			t.Errorf("Expected status code %d, got %d", http.StatusNotFound, status)
		}
	})
}
