package antfarm

import (
	"reflect"
	"testing"
)

// Helper function to create test rooms
func createTestRooms() []Room {
	return []Room{
		{Name: "start", Point: "start", Occupants: make(map[int]bool)},
		{Name: "1", Point: "", Occupants: make(map[int]bool)},
		{Name: "2", Point: "", Occupants: make(map[int]bool)},
		{Name: "end", Point: "end", Occupants: make(map[int]bool)},
	}
}

func TestOptimalsToRooms(t *testing.T) {
	rooms := createTestRooms()
	optimals := [][]Route{
		{
			{"start", "1", "end"},
			{"start", "2", "end"},
		},
	}

	result := OptimalsToRooms(optimals, &rooms)

	if len(result) != len(optimals) {
		t.Errorf("OptimalsToRooms() returned wrong number of combinations: got %v, want %v",
			len(result), len(optimals))
	}

	// Check first route in first combination
	expectedFirstRoute := []*Room{&rooms[0], &rooms[1], &rooms[3]}
	if !reflect.DeepEqual(result[0][0], expectedFirstRoute) {
		t.Errorf("First route conversion failed")
	}
}

func TestMakeAnts(t *testing.T) {
	optimals := [][]Route{
		{
			{"start", "1", "end"},
			{"start", "2", "end"},
		},
	}
	antCount := 3

	result := MakeAnts(optimals, antCount)

	if len(result) != len(optimals) {
		t.Errorf("MakeAnts() returned wrong number of ant sets: got %v, want %v",
			len(result), len(optimals))
	}

	if len(result[0]) != antCount {
		t.Errorf("MakeAnts() created wrong number of ants: got %v, want %v",
			len(result[0]), antCount)
	}

	// Check ant names are correctly assigned
	for i, ant := range result[0] {
		if ant.Name != i+1 {
			t.Errorf("Ant name incorrectly assigned: got %v, want %v", ant.Name, i+1)
		}
	}
}

func TestAssignRoutes(t *testing.T) {
	rooms := createTestRooms()
	optimals := [][]Route{
		{
			{"start", "1", "end"},
			{"start", "2", "end"},
		},
	}
	optiRooms := OptimalsToRooms(optimals, &rooms)
	setsOfAnts := MakeAnts(optimals, 3)

	AssignRoutes(optimals, optiRooms, &setsOfAnts)

	// Check that all ants have been assigned a route
	for _, antSet := range setsOfAnts {
		for _, ant := range antSet {
			if ant.Route == nil {
				t.Error("Ant was not assigned a route")
			}
		}
	}
}

func TestPopulateStart(t *testing.T) {
	rooms := createTestRooms()
	ants := []Ant{
		{Name: 1},
		{Name: 2},
	}

	PopulateStart(&rooms, ants)

	startRoom := &rooms[StartIndex(rooms)]
	for _, ant := range ants {
		if !startRoom.Occupants[ant.Name] {
			t.Errorf("Ant %d not found in start room", ant.Name)
		}
	}
}

func TestNextIsFine(t *testing.T) {
	rooms := createTestRooms()
	ant := Ant{
		Name:      1,
		Route:     []*Room{&rooms[0], &rooms[1], &rooms[3]},
		RoomIndex: 0,
	}
	linksUsed := make(map[string]bool)

	canMove, currentRoom, nextRoom := NextIsFine(ant, linksUsed)

	if !canMove {
		t.Error("NextIsFine() should allow movement to empty room")
	}

	if currentRoom.Name != "start" {
		t.Errorf("Wrong current room: got %v, want start", currentRoom.Name)
	}

	if nextRoom.Name != "1" {
		t.Errorf("Wrong next room: got %v, want 1", nextRoom.Name)
	}

	// Test when link is already used
	linksUsed["start-1"] = true
	canMove, _, _ = NextIsFine(ant, linksUsed)
	if canMove {
		t.Error("NextIsFine() should not allow movement when link is used")
	}
}

func TestMoveAnts(t *testing.T) {
	rooms := createTestRooms()
	ants := []Ant{
		{
			Name:      1,
			Route:     []*Room{&rooms[0], &rooms[1], &rooms[3]},
			RoomIndex: 0,
		},
	}
	PopulateStart(&rooms, ants)

	turns := MoveAnts(ants)

	if len(turns) != 2 {
		t.Errorf("MoveAnts() returned wrong number of turns: got %v, want 2", len(turns))
	}

	// Check if ant reached end
	if !ants[0].AtEnd {
		t.Error("Ant did not reach end")
	}
}
