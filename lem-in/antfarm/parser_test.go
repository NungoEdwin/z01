package antfarm

import (
	"os"
	"testing"
)

// Helper function to create a temporary test file with given content
func createTestFile(content string) (*os.File, error) {
	tmpfile, err := os.CreateTemp("", "test-ant-farm-*.txt")
	if err != nil {
		return nil, err
	}

	if _, err := tmpfile.Write([]byte(content)); err != nil {
		tmpfile.Close()
		os.Remove(tmpfile.Name())
		return nil, err
	}

	if _, err := tmpfile.Seek(0, 0); err != nil {
		tmpfile.Close()
		os.Remove(tmpfile.Name())
		return nil, err
	}

	return tmpfile, nil
}

func TestGetStartValues(t *testing.T) {
	tests := []struct {
		name        string
		content     string
		wantAnts    int
		wantRooms   int
		wantError   bool
		errorString string
	}{
		{
			name: "Valid input",
			content: `3
##start
0 1 0
##end
1 5 0
2 9 0
0-2
2-1`,
			wantAnts:    3,
			wantRooms:   3,
			wantError:   false,
			errorString: "",
		},
		{
			name:        "Empty file",
			content:     "",
			wantAnts:    0,
			wantRooms:   0,
			wantError:   true,
			errorString: "ERROR: empty file",
		},
		{
			name: "Invalid number of ants",
			content: `0
##start
0 1 0`,
			wantAnts:    0,
			wantRooms:   0,
			wantError:   true,
			errorString: "ERROR: invalid number of ants",
		},
		{
			name: "Invalid ant format",
			content: `abc
##start
0 1 0`,
			wantAnts:    0,
			wantRooms:   0,
			wantError:   true,
			errorString: "ERROR: invalid number of ants",
		},
		{
			name: "With comments",
			content: `5
#comment
##start
0 1 0
#another comment
##end
1 5 0
#yet another comment
0-1`,
			wantAnts:    5,
			wantRooms:   2,
			wantError:   false,
			errorString: "",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			tmpfile, err := createTestFile(tt.content)
			if err != nil {
				t.Fatalf("Failed to create test file: %v", err)
			}
			defer os.Remove(tmpfile.Name())
			defer tmpfile.Close()

			gotAnts, gotRooms, err := GetStartValues(tmpfile)

			// Check error
			if tt.wantError {
				if err == nil {
					t.Error("Expected error but got none")
				} else if err.Error() != tt.errorString {
					t.Errorf("Expected error '%v' but got '%v'", tt.errorString, err)
				}
				return
			}
			if err != nil {
				t.Errorf("Unexpected error: %v", err)
				return
			}

			// Check number of ants
			if gotAnts != tt.wantAnts {
				t.Errorf("GetStartValues() gotAnts = %v, want %v", gotAnts, tt.wantAnts)
			}

			// Check number of rooms
			if len(gotRooms) != tt.wantRooms {
				t.Errorf("GetStartValues() got %v rooms, want %v", len(gotRooms), tt.wantRooms)
			}
		})
	}
}

func TestDetermineRoomRole(t *testing.T) {
	tests := []struct {
		name     string
		prev     string
		expected string
	}{
		{
			name:     "Start room",
			prev:     "##start",
			expected: "start",
		},
		{
			name:     "End room",
			prev:     "##end",
			expected: "end",
		},
		{
			name:     "Normal room",
			prev:     "1 2 3",
			expected: "normal",
		},
		{
			name:     "Empty string",
			prev:     "",
			expected: "normal",
		},
		{
			name:     "Comment",
			prev:     "#comment",
			expected: "normal",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := determineRoomRole(tt.prev); got != tt.expected {
				t.Errorf("determineRoomRole() = %v, want %v", got, tt.expected)
			}
		})
	}
}

func TestLinkRoom(t *testing.T) {
	tests := []struct {
		name          string
		initialRooms  []Room
		room1         string
		room2         string
		expectedLinks map[string][]string
	}{
		{
			name: "Valid link between two rooms",
			initialRooms: []Room{
				{Name: "room1", Neighbours: []string{}},
				{Name: "room2", Neighbours: []string{}},
			},
			room1: "room1",
			room2: "room2",
			expectedLinks: map[string][]string{
				"room1": {"room2"},
				"room2": {"room1"},
			},
		},
		{
			name: "Add link to existing links",
			initialRooms: []Room{
				{Name: "room1", Neighbours: []string{"existing"}},
				{Name: "room2", Neighbours: []string{}},
			},
			room1: "room1",
			room2: "room2",
			expectedLinks: map[string][]string{
				"room1": {"existing", "room2"},
				"room2": {"room1"},
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			rooms := make([]Room, len(tt.initialRooms))
			copy(rooms, tt.initialRooms)

			LinkRoom(rooms, tt.room1, tt.room2)

			// Verify links
			for roomName, expectedNeighbours := range tt.expectedLinks {
				found := false
				for _, room := range rooms {
					if room.Name == roomName {
						found = true
						if !equalStringSlices(room.Neighbours, expectedNeighbours) {
							t.Errorf("Room %s: got neighbours %v, want %v",
								roomName, room.Neighbours, expectedNeighbours)
						}
					}
				}
				if !found {
					t.Errorf("Room %s not found after linking", roomName)
				}
			}
		})
	}
}

func TestValidateRooms(t *testing.T) {
	tests := []struct {
		name        string
		rooms       []Room
		wantError   bool
		errorString string
	}{
		{
			name: "Valid rooms configuration",
			rooms: []Room{
				{Name: "start", Point: "start", Neighbours: []string{"middle"}},
				{Name: "middle", Point: "normal", Neighbours: []string{"start", "end"}},
				{Name: "end", Point: "end", Neighbours: []string{"middle"}},
			},
			wantError: false,
		},
		{
			name: "Duplicate room names",
			rooms: []Room{
				{Name: "room1", Point: "start"},
				{Name: "room1", Point: "normal"},
			},
			wantError:   true,
			errorString: "ERROR: duplicate room name: room1",
		},
		{
			name: "Missing start room",
			rooms: []Room{
				{Name: "room1", Point: "normal"},
				{Name: "room2", Point: "end"},
			},
			wantError:   true,
			errorString: "ERROR: no start room",
		},
		{
			name: "Missing end room",
			rooms: []Room{
				{Name: "room1", Point: "start"},
				{Name: "room2", Point: "normal"},
			},
			wantError:   true,
			errorString: "ERROR: no end room",
		},
		{
			name: "Multiple start rooms",
			rooms: []Room{
				{Name: "room1", Point: "start"},
				{Name: "room2", Point: "start"},
				{Name: "room3", Point: "end"},
			},
			wantError:   true,
			errorString: "ERROR: too many start rooms",
		},
		{
			name: "Multiple end rooms",
			rooms: []Room{
				{Name: "room1", Point: "start"},
				{Name: "room2", Point: "end"},
				{Name: "room3", Point: "end"},
			},
			wantError:   true,
			errorString: "ERROR: too many end rooms",
		},
		{
			name: "Invalid link",
			rooms: []Room{
				{Name: "room1", Point: "start", Neighbours: []string{"nonexistent"}},
				{Name: "room2", Point: "end"},
			},
			wantError:   true,
			errorString: "ERROR: invalid link: room1 > nonexistent",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			err := ValidateRooms(tt.rooms)
			if tt.wantError {
				if err == nil {
					t.Error("Expected error but got none")
				} else if err.Error() != tt.errorString {
					t.Errorf("Expected error '%v' but got '%v'", tt.errorString, err)
				}
			} else if err != nil {
				t.Errorf("Unexpected error: %v", err)
			}
		})
	}
}

func TestRoomExists(t *testing.T) {
	rooms := []Room{
		{Name: "room1"},
		{Name: "room2"},
		{Name: "room3"},
	}

	tests := []struct {
		name     string
		roomName string
		want     bool
	}{
		{
			name:     "Existing room",
			roomName: "room1",
			want:     true,
		},
		{
			name:     "Non-existing room",
			roomName: "room4",
			want:     false,
		},
		{
			name:     "Empty room name",
			roomName: "",
			want:     false,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := roomExists(rooms, tt.roomName); got != tt.want {
				t.Errorf("roomExists() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestStartIndex(t *testing.T) {
	tests := []struct {
		name     string
		rooms    []Room
		expected int
	}{
		{
			name: "Start room exists",
			rooms: []Room{
				{Name: "room1", Point: "normal"},
				{Name: "room2", Point: "start"},
				{Name: "room3", Point: "end"},
			},
			expected: 1,
		},
		{
			name: "No start room",
			rooms: []Room{
				{Name: "room1", Point: "normal"},
				{Name: "room2", Point: "end"},
			},
			expected: -1,
		},
		{
			name:     "Empty room list",
			rooms:    []Room{},
			expected: -1,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := StartIndex(tt.rooms); got != tt.expected {
				t.Errorf("StartIndex() = %v, want %v", got, tt.expected)
			}
		})
	}
}

// Helper function to compare string slices
func equalStringSlices(a, b []string) bool {
	if len(a) != len(b) {
		return false
	}
	for i := range a {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}
