package utils

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"sync"
	"time"

	"github.com/kh3rld/groupie-tracker/backend/models"
)

type Cache[T any] struct {
	data      *T
	expiresAt time.Time
}

var (
	artistCache    Cache[[]models.Artist]
	locationCache  Cache[models.LocationIndex]
	datesCache     Cache[models.DatesIndex]
	relationsCache Cache[models.RelationIndex]
	cacheMutex     sync.Mutex
	cacheTTL       = 5 * time.Minute
)

// FetchAndUnmarshal makes a GET request to the specified URL, unmarshals the JSON response into the provided data structure,//+
// and returns any encountered errors.
// //+
// Parameters:
// url (string): The URL to make the GET request to.
// data (interface{}): A pointer to the data structure where the JSON response will be unmarshaled.
// /
// Returns:
// error: An error if any occurred during the request or unmarshalling process, or nil if successful.
func FetchAndUnmarshal(url string, target interface{}) error {
	resp, err := http.Get(url)
	if err != nil {
		return fmt.Errorf("error fetching data from %s: %w", url, err)
	}
	defer resp.Body.Close()
	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return fmt.Errorf("error reading response body: %w", err)
	}
	err = json.Unmarshal(body, target)
	if err != nil {
		return fmt.Errorf("error unmarshalling JSON: %w", err)
	}
	return nil
}

// GetArtists fetches artist data and writes the result to the response writer.
func GetArtists(w http.ResponseWriter, r *http.Request) {
	cacheMutex.Lock()
	defer cacheMutex.Unlock()
	if time.Now().Before(artistCache.expiresAt) && artistCache.data != nil {
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(artistCache.data)
		return
	}
	var artists []models.Artist
	err := FetchAndUnmarshal("https://groupietrackers.herokuapp.com/api/artists", &artists)
	if err != nil {
		http.Error(w, "Error fetching artists", http.StatusInternalServerError)
		fmt.Println(err)
		return
	}
	artistCache = Cache[[]models.Artist]{
		data:      &artists,
		expiresAt: time.Now().Add(cacheTTL),
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(artists)
}

// GetLocations fetches location data and writes the result to the response writer.
func GetLocations(w http.ResponseWriter, r *http.Request) {
	cacheMutex.Lock()
	defer cacheMutex.Unlock()
	if time.Now().Before(locationCache.expiresAt) && locationCache.data != nil {
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(locationCache.data)
		return
	}
	var locationIndex models.LocationIndex
	err := FetchAndUnmarshal("https://groupietrackers.herokuapp.com/api/locations", &locationIndex)
	if err != nil {
		http.Error(w, "Error fetching locations", http.StatusInternalServerError)
		fmt.Println(err)
		return
	}
	locationCache = Cache[models.LocationIndex]{
		data:      &locationIndex,
		expiresAt: time.Now().Add(cacheTTL),
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(locationIndex)
}

// GetDates fetches date data and writes the result to the response writer.
func GetDates(w http.ResponseWriter, r *http.Request) {
	cacheMutex.Lock()
	defer cacheMutex.Unlock()
	if time.Now().Before(datesCache.expiresAt) && datesCache.data != nil {
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(datesCache.data)
		return
	}
	var dateIndex models.DatesIndex
	err := FetchAndUnmarshal("https://groupietrackers.herokuapp.com/api/dates", &dateIndex)
	if err != nil {
		http.Error(w, "Error fetching dates", http.StatusInternalServerError)
		fmt.Println(err)
		return
	}
	datesCache = Cache[models.DatesIndex]{
		data:      &dateIndex,
		expiresAt: time.Now().Add(cacheTTL),
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(dateIndex)
}

// GetRelations fetches relations data and writes the result to the response writer.
func GetRelations(w http.ResponseWriter, r *http.Request) {
	cacheMutex.Lock()
	defer cacheMutex.Unlock()
	if time.Now().Before(relationsCache.expiresAt) && relationsCache.data != nil {
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(relationsCache.data)
		return
	}
	var relations models.RelationIndex
	err := FetchAndUnmarshal("https://groupietrackers.herokuapp.com/api/relation", &relations)
	if err != nil {
		http.Error(w, "Error fetching relations", http.StatusInternalServerError)
		fmt.Println(err)
		return
	}
	relationsCache = Cache[models.RelationIndex]{
		data:      &relations,
		expiresAt: time.Now().Add(cacheTTL),
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(relations)
}
