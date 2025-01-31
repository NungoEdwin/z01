const API_URL = "http://localhost:8080/api/artists";
const RELATIONS_API_URL = "http://localhost:8080/api/relations";
const LOCATIONS_API_URL = "http://localhost:8080/api/locations";
const MAPBOX_API_KEY =
  "pk.eyJ1Ijoiam9zaWUtb3BvbmRvIiwiYSI6ImNtMXhlM3o2NDB4a2UyanM3NWxqMTFoaWoifQ.3A_vnVF8Lnt15xtKrUZTkg";
const artistGrid = document.getElementById("artistGrid");
const loading = document.getElementById("loading");
const skeletonContainer = document.getElementById("skeletonContainer");
const searchInput = document.getElementById("search");
const filterSelect = document.getElementById("filter");
const suggestionsContainer = document.getElementById("suggestions");
const overlay = document.getElementById("overlay");
const modal = document.getElementById("artistModal");
const modalContent = document.getElementById("modalArtistContent");
const closeModalButton = document.getElementById("closeModal");
const prevButton = document.getElementById("prevPage");
const nextButton = document.getElementById("nextPage");
const currentPageSpan = document.getElementById("currentPage");
const resetButton = document.getElementById("resetFilters");
let artists = [];
let filteredArtists = [];
let locationsData = {};
let relationsData = {};
let currentPage = 1;
const artistsPerPage = 12;

function showSkeletonLoaders() {
  console.log("skeleton loaded");
  // if (skeletonContainer) {
    skeletonContainer.style.display = "block";
  // }
  // if (artistGrid) {
    artistGrid.innerHTML = "";
    for (let i = 0; i < artistsPerPage; i++) {
      const skeletonCard = document.createElement("div");
      skeletonCard.className = "artist-card skeleton-loader";
      skeletonCard.innerHTML = `
        <div class="skeleton-image"></div>
        <div class="skeleton-info">
          <div class="skeleton-name"></div>
          <div class="skeleton-details"></div>
          <div class="skeleton-details"></div>
        </div>
      `;
      artistGrid.appendChild(skeletonCard);
    }
  // }
}
export const fetchArtists = async () => {
  // showSkeletonLoaders()
  try {
    // showSkeletonLoaders();
    const response = await fetch(API_URL);
    const data = await response.json();
    artists = data;
    skeletonContainer.style.display = "none";
    // renderArtists(artists);
    // updatePaginationControls();
    return data;
  } catch (error) {
    console.error("Error fetching artists:", error);
    // if (loading) {
    loading.textContent = "Error loading artists. Please try again later.";
    // }
  }
};
async function fetchLocations() {
  try {
    const response = await fetch(LOCATIONS_API_URL);
    locationsData = await response.json();
    localStorage.setItem("locationsData", JSON.stringify(locationsData));
  } catch (error) {
    console.error("Error fetching locations:", error);
  }
}
async function fetchRelations() {
  try {
    const response = await fetch(RELATIONS_API_URL);
    relationsData = await response.json();
    localStorage.setItem("relationsData", JSON.stringify(relationsData));
  } catch (error) {
    console.error("Error fetching relations:", error);
  }
}
function getCachedGeocode(location) {
  const cachedData = localStorage.getItem(location);
  return cachedData ? JSON.parse(cachedData) : null;
}
function cacheGeocode(location, geocodeResult) {
  localStorage.setItem(location, JSON.stringify(geocodeResult));
}
async function geocodeLocation(location) {
  const cachedGeocode = getCachedGeocode(location);
  if (cachedGeocode) {
    return cachedGeocode; // Return cached data if available
  }
  const encodedLocation = encodeURIComponent(location); // Encode location for the URL
  const url = `https://api.mapbox.com/geocoding/v5/mapbox.places/${encodedLocation}.json?access_token=${MAPBOX_API_KEY}&limit=1`;
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    if (data.features && data.features.length > 0) {
      const { center } = data.features[0]; // Mapbox returns [lng, lat] as the center
      const [lng, lat] = center;
      const geocodeResult = { lat, lng };
      // Cache the result
      cacheGeocode(location, geocodeResult);
      return geocodeResult;
    } else {
      console.warn(`Geocoding failed for location: ${location}`);
      return null;
    }
  } catch (error) {
    console.error("Error geocoding location:", error);
    return null;
  }
}
// Fetch artist locations and geocode them
async function fetchAndGeocodeLocations() {
  await fetchLocations();
  const geocodedLocations = {};
  for (const artistId in locationsData.index) {
    const artistLocations = locationsData.index[artistId].locations;
    geocodedLocations[artistId] = [];
    for (const location of artistLocations) {
      await new Promise((resolve) => setTimeout(resolve, 1000)); // 1 second delay
      const geocoded = await geocodeLocation(location.replace(/[_]/g, " "));
      if (geocoded) {
        geocodedLocations[artistId].push({ location, ...geocoded });
      }
    }
  }
  return geocodedLocations;
}

function renderArtists(artistsToRender) {
  // await fetchArtists();
  // showSkeletonLoaders()
  loading.style.display = "none";
  artistGrid.innerHTML = "";
  const startIndex = (currentPage - 1) * artistsPerPage;
  const endIndex = startIndex + artistsPerPage;
  const paginatedArtists = artistsToRender.slice(startIndex, endIndex);
  if (paginatedArtists.length === 0) {
    artistGrid.innerHTML = "Sorry, we cant find the artist you are looking for";
    return;
  }
  paginatedArtists.forEach((artist) => {
    const card = document.createElement("div");
    card.className = "artist-card";
    card.innerHTML = `
      <img loading="lazy" src="${artist.image}" alt="${artist.name}" class="artist-image">
      <div class="artist-info">
        <h2 class="artist-name">${artist.name}</h2>
        <p class="artist-details">Active since: ${artist.creationDate}</p>
        <p class="artist-details">First album: ${artist.firstAlbum}</p>
      </div>
    `;
    card.addEventListener("click", () =>
      displayArtistDetailsWithMap(artist.id)
    );
    artistGrid.appendChild(card);
  });
}
function updatePaginationControls() {
  const totalArtists =
    filteredArtists.length > 0 ? filteredArtists.length : artists.length;
  const totalPages = Math.ceil(totalArtists / artistsPerPage);
  prevButton.disabled = currentPage === 1;
  nextButton.disabled = currentPage === totalPages;
  currentPageSpan.textContent = `${currentPage} / ${totalPages}`;
}
// Display artist details with geolocation on a map
function displayArtistDetailsWithMap(artistId) {
  const artist = artists.find((a) => a.id === artistId);
  const concerts =
    relationsData.index.find((r) => r.id === artistId)?.datesLocations || {};
  const concertList = Object.entries(concerts).flatMap(([location, dates]) => {
    return dates.map((date) => ({ location, date }));
  });
  // Sort concerts by date
  concertList.sort((a, b) => {
    const dateA = new Date(a.date.split("-").reverse().join("-")); // Convert DD-MM-YYYY to YYYY-MM-DD
    const dateB = new Date(b.date.split("-").reverse().join("-"));
    return dateA - dateB;
  });
  if (artist) {
    modalContent.innerHTML = `
      <img loading="lazy" src="${artist.image}" alt="${
      artist.name
    }" class="artist-image">
      <div class="artist-info">
        <h2 class="artist-name">${artist.name}</h2>
        <p class="artist-details">Active since: ${artist.creationDate}</p>
        <p class="artist-details">First album: ${artist.firstAlbum}</p>
        <p class="artist-details">Members:</p>
        <ul class="artist-members">
          ${artist.members.map((member) => `<li>${member}</li>`).join("")}
        </ul>
        <p class="artist-details">Upcoming Concerts:</p>
        <ul class="artist-concerts">
          ${concertList
            .map(
              (concert) =>
                `<li>${concert.location.replace(/[_]/g, " ")} on ${
                  concert.date
                }</li>`
            )
            .join("")}
        </ul>
        <div id="map" style="height: 300px;"></div>  <!-- Map container -->
      </div>
    `;
    // Initialize the Mapbox map
    mapboxgl.accessToken = MAPBOX_API_KEY;
    const map = new mapboxgl.Map({
      container: "map", // container ID
      style: "mapbox://styles/mapbox/streets-v11", // style URL
      center: [0, 0], // starting position [lng, lat]
      zoom: 2, // starting zoom
    });
    // Wait for the map to load before adding markers and polylines
    map.on("load", async () => {
      const bounds = new mapboxgl.LngLatBounds();
      const latLngs = []; // Store coordinates for the polyline
      const markerPromises = concertList.map(async (concert) => {
        const { location, date } = concert;
        try {
          const geocoded = await geocodeLocation(location.replace(/[_]/g, " "));
          if (geocoded) {
            const marker = new mapboxgl.Marker()
              .setLngLat([geocoded.lng, geocoded.lat])
              .setPopup(
                new mapboxgl.Popup().setHTML(`${location.replace(/[_]/g, " ")}`)
              )
              .addTo(map);
            bounds.extend([geocoded.lng, geocoded.lat]); // Extend bounds to include marker location
            latLngs.push([geocoded.lng, geocoded.lat]); // Add the location to polyline points
          }
        } catch (error) {
          console.error(`Failed to geocode location: ${location}`, error);
          alert(
            `Network issue: Failed to load location: ${location.replace(
              /[_]/g,
              " "
            )}`
          );
        }
      });
      // Wait for all markers to be added
      await Promise.all(markerPromises);
      if (latLngs.length > 1) {
        // Fit the map to the bounds of the markers
        map.fitBounds(bounds, { padding: 50 });
        // Add the polyline after markers are added
        map.addSource("route", {
          type: "geojson",
          data: {
            type: "Feature",
            properties: {},
            geometry: {
              type: "LineString",
              coordinates: latLngs,
            },
          },
        });
        // Draw the polyline with a blue color
        map.addLayer({
          id: "route",
          type: "line",
          source: "route",
          layout: {
            "line-join": "round",
            "line-cap": "round",
          },
          paint: {
            "line-color": "#0074D9",
            "line-width": 4,
          },
        });
      }
    });
    modal.style.display = "block";
    overlay.style.display = "block";
  } else {
    modalContent.innerHTML = `<p>Artist not found. Please try again later.</p>`;
    modal.style.display = "block";
    overlay.style.display = "block";
  }
}
function debounce(func, delay) {
  let timeoutId;
  return function (...args) {
    if (timeoutId) {
      clearTimeout(timeoutId);
    }
    timeoutId = setTimeout(() => {
      func.apply(this, args);
    }, delay);
  };
}
String.prototype.toTitle = function () {
  return this.replace(/\w\S*/g, (txt) => {
    return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
  });
};

let artistRange = { minDate: 1958, maxDate: 2015 };
function rangeCalc(artists) {
  let creationDate = artists
    .map((artist) => artist.creationDate)
    .sort((a, b) => a - b);
  artistRange = {
    minDate: creationDate[0] || 1958,
    maxDate: creationDate[creationDate.length - 1] || 2015,
  };
}
let albumRange = { earliestYear: 1963, latestYear: 2018 };
function extractYear(dateString) {
  const date = new Date(dateString.split("-").reverse().join("-")); // Reversing to format as YYYY-MM-DD
  return date.getFullYear();
}
function sortFirstAlbumYears(artists) {
  const sorted = artists
    .map((artist) => ({
      ...artist,
      firstAlbumYear: extractYear(artist.firstAlbum),
    }))
    .sort((a, b) => a.firstAlbumYear - b.firstAlbumYear);
  albumRange = {
    earliestYear: sorted[0]?.firstAlbumYear || 1963,
    latestYear: sorted[sorted.length - 1]?.firstAlbumYear || 2018,
  };
}

rangeCalc(artists)
$(function() {
  $("#creation-date").slider({
      step: 1,
      range: true,
      min: artistRange.minDate,
      max: artistRange.maxDate,
      values: [artistRange.minDate, artistRange.maxDate],
      slide: function(event, ui) {
        $("#creationDate").val(ui.values[0] + " - " + ui.values[1]);
        filterArtists();
      }
  });

  // Initial display
  $("#creationDate").val($("#creation-date").slider("values", 0) + " - " + $("#creation-date").slider("values", 1));
  filterArtists();
});

sortFirstAlbumYears(artists);

$(function() {
  $("#first-album").slider({
      step: 1,
      range: true,
      min: albumRange.earliestYear,
      max: albumRange.latestYear,
      values: [albumRange.earliestYear, albumRange.latestYear],
      slide: function(event, ui) {
        $("#firstAlbum").val(ui.values[0] + " - " + ui.values[1]);
        filterArtists();
      }
  });

  // Initial display
  $("#firstAlbum").val($("#first-album").slider("values", 0) + " - " + $("#first-album").slider("values", 1));
  filterArtists();
});

function filterArtists() {
  const searchTerm = searchInput.value.toLowerCase();
  const selectedFilter = filterSelect.value;
  const memberFilter = Array.from(
    document.querySelectorAll('input[name="members"]:checked')
  ).map((checkbox) => parseInt(checkbox.value));

  //check that both sliders have been initialized
  if (!$("#creation-date").hasClass("ui-slider")) return;
  if (!$("#first-album").hasClass("ui-slider")) return;

  // Get the current values from the slider
  const minYear = $("#creation-date").slider("values", 0);
  const maxYear = $("#creation-date").slider("values", 1);

  const earliestYear = $("#first-album").slider("values", 0);
  const latestYear = $("#first-album").slider("values", 1);

  // Filter artists based on all criteria
  filteredArtists = artists.filter((artist) => {
    const matchesMembers =
      memberFilter.length === 0 || memberFilter.includes(artist.members.length);
    const matchesDateRange =
      artist.creationDate >= minYear && artist.creationDate <= maxYear;
    const artistFirstAlbumYear = extractYear(artist.firstAlbum); // Extract the year
    const matchesAlbumRange =
      artistFirstAlbumYear >= earliestYear &&
      artistFirstAlbumYear <= latestYear;
    let matchesSearchTerm = true;
    if (selectedFilter === "name") {
      matchesSearchTerm =
        artist.name.toLowerCase().includes(searchTerm) ||
        artist.members.some((member) =>
          member.toLowerCase().includes(searchTerm)
        );
    } else if (selectedFilter === "active_since") {
      matchesSearchTerm = artist.creationDate.toString().startsWith(searchTerm);
    } else if (selectedFilter === "first_album") {
      matchesSearchTerm = artist.firstAlbum
        .toLowerCase()
        .startsWith(searchTerm);
    } else if (selectedFilter === "location") {
      const normalizedSearchTerm = searchTerm
        .replace(/,/g, "")
        .replace(/\s+/g, "-")
        .toLowerCase();
      matchesSearchTerm = locationsData.index.some(
        (location) =>
          location.id === artist.id &&
          location.locations.some((loc) => {
            const normalizedLoc = loc.replace(/[_]/g, "-").toLowerCase();
            const altFormatLoc = loc.replace(/[-_]/g, " ").toLowerCase();
            return (
              normalizedLoc.includes(normalizedSearchTerm) ||
              altFormatLoc.includes(normalizedSearchTerm)
            );
          })
      );
    }
    return (
      matchesMembers &&
      matchesDateRange &&
      matchesAlbumRange &&
      matchesSearchTerm
    );
  });
  let suggestions = [];
  if (selectedFilter === "name") {
    const artistSuggestions = [
      ...new Set(
        artists
          .map((artist) => artist.name.toLowerCase())
          .filter((name) => name.includes(searchTerm))
      ),
    ].map((name) => ({ value: name.toTitle(), group: "artist" }));
    const memberSuggestions = [
      ...new Set(
        artists
          .flatMap((artist) =>
            artist.members.map((member) => member.toLowerCase())
          )
          .filter((member) => member.includes(searchTerm))
      ),
    ].map((member) => ({ value: member.toTitle(), group: "member" }));
    suggestions = [...artistSuggestions, ...memberSuggestions];
  } else if (selectedFilter === "active_since") {
    suggestions = [
      ...new Set(
        artists
          .map((artist) => artist.creationDate.toString())
          .filter((year) => year.startsWith(searchTerm))
      ),
    ].map((year) => ({ value: year, group: "active_since" }));
  } else if (selectedFilter === "first_album") {
    suggestions = [
      ...new Set(
        artists
          .map((artist) => artist.firstAlbum.toLowerCase())
          .filter((album) => album.includes(searchTerm))
      ),
    ].map((album) => ({ value: album, group: "first_album" }));
  } else if (selectedFilter === "location") {
    const normalizedSearchTerm = searchTerm
      .replace(/,/g, "")
      .replace(/\s+/g, "-")
      .toLowerCase();
    suggestions = [
      ...new Set(
        locationsData.index
          .flatMap((location) =>
            location.locations.flatMap((loc) => {
              const withHyphen = loc.replace(/[_]/g, "-").toLowerCase();
              return [withHyphen];
            })
          )
          .filter((loc) => loc.includes(normalizedSearchTerm))
      ),
    ].map((loc) => ({ value: loc, group: "location" }));
  }
  showSuggestions(suggestions);
  currentPage = 1;
  // Render the filtered artists and update pagination controls
  renderArtists(filteredArtists);
  updatePaginationControls();
}

//Displays suggestions based on the search input and selected filter.
function showSuggestions(suggestions) {
  suggestionsContainer.innerHTML = "";
  if (suggestions.length === 0) {
    suggestionsContainer.style.display = "none";
    return;
  }
  suggestionsContainer.style.display = "block";
  if (filterSelect.value === "name") {
    const artistNames = suggestions.filter((s) => s.group === "artist");
    if (artistNames.length) {
      const artistTitle = document.createElement("div");
      artistTitle.className = "suggestion-title";
      artistTitle.textContent = "Artist/Band Names";
      suggestionsContainer.appendChild(artistTitle);
      artistNames.forEach((suggestion) => {
        const suggestionItem = document.createElement("div");
        suggestionItem.className = "suggestion-item";
        suggestionItem.textContent = suggestion.value;
        suggestionItem.addEventListener("click", () => {
          searchInput.value = suggestion.value;
          filterArtists();
          suggestionsContainer.style.display = "none";
        });
        suggestionsContainer.appendChild(suggestionItem);
      });
    }
    const memberNames = suggestions.filter((s) => s.group === "member");
    if (memberNames.length) {
      const memberTitle = document.createElement("div");
      memberTitle.className = "suggestion-title";
      memberTitle.textContent = "Members";
      suggestionsContainer.appendChild(memberTitle);
      memberNames.forEach((suggestion) => {
        const suggestionItem = document.createElement("div");
        suggestionItem.className = "suggestion-item";
        suggestionItem.textContent = suggestion.value;
        suggestionItem.addEventListener("click", () => {
          searchInput.value = suggestion.value;
          filterArtists();
          suggestionsContainer.style.display = "none";
        });
        suggestionsContainer.appendChild(suggestionItem);
      });
    }
  } else {
    const titleMap = {
      active_since: "Active Since",
      first_album: "First Album",
      location: "Locations",
    };
    const title = titleMap[filterSelect.value];
    const groupSuggestions = suggestions.filter(
      (s) => s.group === filterSelect.value
    );
    if (groupSuggestions.length) {
      const filterTitle = document.createElement("div");
      filterTitle.className = "suggestion-title";
      filterTitle.textContent = title;
      suggestionsContainer.appendChild(filterTitle);
      groupSuggestions.forEach((match) => {
        const suggestionItem = document.createElement("div");
        suggestionItem.className = "suggestion-item";
        suggestionItem.textContent = match.value;
        suggestionItem.addEventListener("click", () => {
          searchInput.value = match.value;
          filterArtists();
          suggestionsContainer.style.display = "none";
        });
        suggestionsContainer.appendChild(suggestionItem);
      });
    }
  }
}
document.addEventListener("click", (event) => {
  if (!document.querySelector(".search-container").contains(event.target)) {
    suggestionsContainer.style.display = "none";
  }
});

// window.onload = showSkeletonLoaders;
document.addEventListener("DOMContentLoaded", async () => {
  // showSkeletonLoaders();
  const artistsData = await fetchArtists();
  fetchLocations();
  fetchRelations();
  fetchAndGeocodeLocations();
  artistGrid.innerHTML = "";

  if (artistsData) {
    artistGrid.innerHTML = ""
    renderArtists(artistsData);
  }
  // renderArtists(artistsData);
  closeModalButton.addEventListener("click", () => {
    modal.style.display = "none";
    overlay.style.display = "none";
  });
  window.addEventListener("click", (event) => {
    if (event.target === modal || event.target === overlay) {
      modal.style.display = "none";
      overlay.style.display = "none";
    }
  });
  filterArtists();
  searchInput.addEventListener(
    "input",
    debounce(() => {
      filterArtists();
      const searchTerm = searchInput.value.toLowerCase();
      if (!searchTerm) {
        suggestionsContainer.innerHTML = "";
        suggestionsContainer.style.display = "none";
      }
    })
  );
  filterSelect.addEventListener("change", () => {
    searchInput.value = "";
    suggestionsContainer.style.display = "none";
    debounce(filterArtists, 300)();
  });
  nextButton.addEventListener("click", () => {
    const TotalArtists =
      filteredArtists.length > 0 ? filteredArtists.length : artists.length;
    const totalPages = Math.ceil(TotalArtists / artistsPerPage);
    if (currentPage < totalPages) {
      currentPage++;
      renderArtists(filteredArtists.length > 0 ? filteredArtists : artists);
      updatePaginationControls();
    }
  });
  prevButton.addEventListener("click", () => {
    if (currentPage > 1) {
      currentPage--;
      renderArtists(filteredArtists.length > 0 ? filteredArtists : artists);
      updatePaginationControls();
    }
  });
  const memberCheckboxes = document.querySelectorAll('input[name="members"]');
  memberCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", filterArtists);
  });
  resetButton.addEventListener("click", () => {
    searchInput.value = "";
    filterSelect.value = "Filter by...";
    document
      .querySelectorAll('input[name="members"]')
      .forEach((checkbox) => (checkbox.checked = false));

    // Reset the creation date range slider
    $("#creation-date").slider("values", [1958, 2015]);
    $("#creationDate").val("1958 - 2015");

    // Reset the first album date range slider
    $("#first-album").slider("values", [1963, 2018]);
    $("#firstAlbum").val("1963 - 2018");

    filterArtists();
    suggestionsContainer.innerHTML = "";
    suggestionsContainer.style.display = "none";
  });
});
function adjustFooter() {
  var body = document.body;
  var html = document.documentElement;
  var footer = document.querySelector("footer");
  var height = Math.max(
    body.scrollHeight,
    body.offsetHeight,
    html.clientHeight,
    html.scrollHeight,
    html.offsetHeight
  );
  if (height <= window.innerHeight) {
    footer.style.position = "fixed";
    footer.style.bottom = "0";
    footer.style.width = "100%";
  } else {
    footer.style.position = "static";
  }
}
// Call adjustFooter on load and resize
window.onload = showSkeletonLoaders;
window.addEventListener("load", adjustFooter);
window.addEventListener("resize", adjustFooter);
