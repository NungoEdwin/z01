package antfarm

import (
	"fmt"
	"reflect"
	"sync"
)

var sepMu sync.Mutex

// FindRoutes recursively finds all valid routes from the current room to the end room.
// It appends each valid route to the routes slice.
// curRoom: The current room being explored.
// curRoute: The current path being built.
// routes: A pointer to the slice of all valid routes.
// rooms: A pointer to the slice of all rooms in the farm.
func FindRoutes(curRoom Room, curRoute Route, routes *[]Route, rooms *[]Room) {
	// reached the end, add to routes
	if curRoom.Point == "end" {
		curRoute = append(curRoute, curRoom.Name)
		toSave := make(Route, len(curRoute))
		copy(toSave, curRoute) // copy values to a new route to avoid pointer problems
		*routes = append(*routes, toSave)
		return
	}

	// add new room to current route and proceed
	if !IsOnRoute(curRoute, curRoom) {
		curRoute = append(curRoute, curRoom.Name)
		for _, link := range curRoom.Neighbours {
			nextRoom := (*rooms)[FindRoom(*rooms, link)]
			FindRoutes(nextRoom, curRoute, routes, rooms)
		}
	}
}

// SortRoutes sorts a slice of routes from shortest to longest.
// rts: A pointer to the slice of routes to be sorted.
// Returns an error if the slice is empty.
func SortRoutes(rts *[]Route) error {
	if len(*rts) < 1 {
		return fmt.Errorf("ERROR: invalid data format, no valid routes")
	}

	for i := 0; i < len(*rts)-1; i++ {
		for j := i + 1; j < len(*rts); j++ {
			if len((*rts)[i]) > len((*rts)[j]) {
				(*rts)[i], (*rts)[j] = (*rts)[j], (*rts)[i]
			}
		}
	}

	return nil
}

// IsOnRoute checks if a room is already in the current route.
// route: The current route being checked.
// room: The room to check for.
// Returns true if the room is in the route, otherwise false.
func IsOnRoute(route Route, room Room) bool {
	for _, r := range route {
		if room.Name == r {
			return true
		}
	}
	return false
}

// FindRoom returns the index of a room in the rooms slice by its name.
// rooms: The slice of all rooms in the farm.
// name: The name of the room to find.
// Returns the index of the room, or -1 if not foun
func FindRoom(rooms []Room, name string) int {
	for i, r := range rooms {
		if r.Name == name {
			return i
		}
	}
	return -1
}

// SharedRoom checks if two routes share any intermediary rooms (excluding start and end rooms).
// rt1: The first route to compare.
// rt2: The second route to compare.
// Returns true if the routes share any intermediary rooms, otherwise false.
func SharedRoom(rt1, rt2 *Route) bool {
	for _, room1 := range (*rt1)[1 : len(*rt1)-1] {
		for _, room2 := range (*rt2)[1 : len(*rt2)-1] {
			if room1 == room2 {
				return true
			}
		}
	}
	return false
}

// DetermineSeparates recursively finds combinations of non-overlapping routes.
// routes: The slice of all available routes.
// curCombo: The current combination of routes being built.
// combosOfSeparates: A pointer to the slice of all valid combinations of non-overlapping routes.
// ind: The current index in the routes slice.
// wg: A pointer to a WaitGroup to synchronize goroutines.
func DetermineSeparates(routes, curCombo []Route, combosOfSeparates *[][]Route, ind int, wg *sync.WaitGroup) {
	curCombo = append(curCombo, routes[ind])
	routes = routes[ind+1:]

	newRoutes := []Route{}
	for _, potentialRoute := range routes {
		separate := true
		if SharedRoom(&curCombo[len(curCombo)-1], &potentialRoute) {
			separate = false
		}
		if separate {
			newRoutes = append(newRoutes, potentialRoute)
		}
	}

	if len(newRoutes) == 0 {
		sepMu.Lock()
		*combosOfSeparates = append(*combosOfSeparates, curCombo)
		sepMu.Unlock()
	}

	for i := range newRoutes {
		wg.Add(1)
		go DetermineSeparates(newRoutes, curCombo, combosOfSeparates, i, wg)
	}

	wg.Done()
}

// CombinationAverageLength calculates the average length of a combination of routes.
// combo: The combination of routes to calculate the average length for.
// Returns the average length as a float64.
func CombinationAverageLength(combo []Route) float64 {
	lens := 0.0
	for _, route := range combo {
		lens += float64(len(route))
	}
	return lens / float64(len(combo))
}

// ShortCombination returns all combinations of routes that include at least one of the shortest routes.
// combosOfSeparates: The slice of all valid combinations of non-overlapping routes.
// routes: The slice of all routes in the farm.
// Returns a slice of combinations that include the shortest routes.
func ShortCombination(combosOfSeparates [][]Route, routes []Route) [][]Route {
	shortestLength := len(routes[0])
	longestComboWithShortest := 0
	for _, combo := range combosOfSeparates {
		if len(combo[0]) == shortestLength && len(combo) > longestComboWithShortest {
			longestComboWithShortest = len(combo)
		}
	}

	shorts := [][]Route{}
	for _, combo := range combosOfSeparates {
		if len(combo[0]) == shortestLength && len(combo) == longestComboWithShortest {
			shorts = append(shorts, combo)
		}
	}

	return shorts
}

// LowAverages finds the combinations of routes with the lowest average length for each number of routes.
// combosOfSeparates: The slice of all valid combinations of non-overlapping routes.
// Returns a slice of combinations with the lowest average length.
func LowAverages(combosOfSeparates [][]Route) [][]Route {
	combosByLength := make(map[int][][]Route)
	bestCombosByLength := make(map[int][][]Route)
	var longestCombo int
	lowAvgs := [][]Route{}

	for _, combo := range combosOfSeparates {
		combosByLength[len(combo)] = append(combosByLength[len(combo)], combo)
		if len(combo) > longestCombo {
			longestCombo = len(combo)
		}
	}

	for key, category := range combosByLength {
		bestAvgLen := CombinationAverageLength(category[0])
		for _, combo := range category {
			if CombinationAverageLength(combo) < bestAvgLen {
				bestAvgLen = CombinationAverageLength(combo)
			}
		}
		for _, combo := range category {
			if CombinationAverageLength(combo) == bestAvgLen {
				bestCombosByLength[key] = append(bestCombosByLength[key], combo)
			}
		}
	}

	lowAvgs = append(lowAvgs, bestCombosByLength[longestCombo]...)

	benchmark := CombinationAverageLength(bestCombosByLength[longestCombo][0])
	for i := longestCombo - 1; i > 0; i-- {
		for _, combo := range bestCombosByLength[i] {
			if CombinationAverageLength(combo) < benchmark {
				lowAvgs = append(lowAvgs, combo)
				benchmark = CombinationAverageLength(combo)
			}
		}
	}

	return lowAvgs
}

// IsSubset checks if one combination of routes is a subset of another.
// combo1: The first combination of routes.
// combo2: The second combination of routes.
// Returns true if combo2 is a subset of combo1, otherwise false.
func IsSubset(combo1 []Route, combo2 []Route) bool {
	if len(combo1) < len(combo2) {
		return false
	}
	for i := range combo2 {
		if len(combo2[i]) != len(combo1[i]) {
			return false
		}
	}
	return true
}

// RemoveRedundant removes duplicate, subset, or functionally equal combinations of routes.
// optimals: The slice of all optimal combinations of routes.
// Returns a slice of unique and non-redundant combinations.
func RemoveRedundant(optimals [][]Route) [][]Route {
	uniques := [][]Route{}
	for i := 0; i < len(optimals); i++ {
		found := false
		for j := 0; j < len(uniques); j++ {
			if reflect.DeepEqual(optimals[i], uniques[j]) {
				found = true
			}
		}
		if !found {
			uniques = append(uniques, optimals[i])
		}
	}

	for i := 0; i < len(uniques)-1; i++ {
		for j := i + 1; j < len(uniques); j++ {
			if len(uniques[i]) < len(uniques[j]) {
				uniques[i], uniques[j] = uniques[j], uniques[i]
			}
		}
	}

	functionalUniques := [][]Route{uniques[0]}
	for _, uniq := range uniques {
		found := true
		for _, fUniq := range functionalUniques {
			if IsSubset(fUniq, uniq) {
				found = false
				break
			}
		}
		if found {
			functionalUniques = append(functionalUniques, uniq)
		}
	}

	return functionalUniques
}
