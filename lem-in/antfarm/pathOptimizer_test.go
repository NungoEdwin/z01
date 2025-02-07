package antfarm

import (
	"reflect"
	"sync"
	"testing"
)

func TestFindRoutes(t *testing.T) {
	type args struct {
		curRoom  Room
		curRoute Route
		routes   *[]Route
		rooms    *[]Room
	}

	tests := []struct {
		name           string
		args           args
		expectedRoutes []Route
	}{
		{
			name: "Simple path from start to end",
			args: args{
				curRoom:  Room{Name: "start", Point: "start", Neighbours: []string{"A"}},
				curRoute: Route{},
				routes:   &[]Route{},
				rooms: &[]Room{
					{Name: "start", Point: "start", Neighbours: []string{"A"}},
					{Name: "A", Point: "normal", Neighbours: []string{"start", "end"}},
					{Name: "end", Point: "end", Neighbours: []string{"A"}},
				},
			},
			expectedRoutes: []Route{
				{"start", "A", "end"},
			},
		},
		{
			name: "Multiple paths to end",
			args: args{
				curRoom:  Room{Name: "start", Point: "start", Neighbours: []string{"A", "B"}},
				curRoute: Route{},
				routes:   &[]Route{},
				rooms: &[]Room{
					{Name: "start", Point: "start", Neighbours: []string{"A", "B"}},
					{Name: "A", Point: "normal", Neighbours: []string{"start", "end"}},
					{Name: "B", Point: "normal", Neighbours: []string{"start", "end"}},
					{Name: "end", Point: "end", Neighbours: []string{"A", "B"}},
				},
			},
			expectedRoutes: []Route{
				{"start", "A", "end"},
				{"start", "B", "end"},
			},
		},
		{
			name: "Circular path prevention",
			args: args{
				curRoom:  Room{Name: "start", Point: "start", Neighbours: []string{"A"}},
				curRoute: Route{},
				routes:   &[]Route{},
				rooms: &[]Room{
					{Name: "start", Point: "start", Neighbours: []string{"A"}},
					{Name: "A", Point: "normal", Neighbours: []string{"start", "end"}},
					{Name: "end", Point: "end", Neighbours: []string{"A"}},
					{Name: "C", Point: "normal", Neighbours: []string{"A", "start"}},
				},
			},
			expectedRoutes: []Route{
				{"start", "A", "end"},
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			FindRoutes(tt.args.curRoom, tt.args.curRoute, tt.args.routes, tt.args.rooms)

			if !reflect.DeepEqual(*tt.args.routes, tt.expectedRoutes) {
				t.Errorf("FindRoutes() got = %v, want = %v", *tt.args.routes, tt.expectedRoutes)
			}
		})
	}
}

func TestSortRoutes(t *testing.T) {
	type args struct {
		rts *[]Route
	}
	tests := []struct {
		name    string
		args    args
		want    []Route
		wantErr bool
	}{
		{
			name: "Sort multiple routes",
			args: args{
				rts: &[]Route{
					{"A", "B", "C", "D"},
					{"A", "E", "F"},
					{"A", "G", "H", "I", "J"},
					{"A", "K"},
				},
			},
			want: []Route{
				{"A", "K"},
				{"A", "E", "F"},
				{"A", "B", "C", "D"},
				{"A", "G", "H", "I", "J"},
			},
			wantErr: false,
		},
		{
			name: "Already sorted routes",
			args: args{
				rts: &[]Route{
					{"A", "K"},
					{"A", "E", "F"},
					{"A", "B", "C", "D"},
				},
			},
			want: []Route{
				{"A", "K"},
				{"A", "E", "F"},
				{"A", "B", "C", "D"},
			},
			wantErr: false,
		},
		{
			name: "Single route",
			args: args{
				rts: &[]Route{
					{"A", "B", "C"},
				},
			},
			want: []Route{
				{"A", "B", "C"},
			},
			wantErr: false,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if err := SortRoutes(tt.args.rts); (err != nil) != tt.wantErr {
				t.Errorf("SortRoutes() error = %v, wantErr %v", err, tt.wantErr)
			}
			for i, route := range *tt.args.rts {
				if len(route) != len(tt.want[i]) || !equalRoutes(route, tt.want[i]) {
					t.Errorf("SortRoutes() failed: got %v, want %v", *tt.args.rts, tt.want)
				}
			}
		})
	}
}

// Helper function to check if two routes are equal
func equalRoutes(a, b Route) bool {
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

func TestIsOnRoute(t *testing.T) {
	type args struct {
		route Route
		room  Room
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		{
			name: "Room is on the route",
			args: args{
				route: Route{"A", "B", "C"},
				room:  Room{Name: "B"},
			},
			want: true,
		},
		{
			name: "Room is not on the route",
			args: args{
				route: Route{"A", "B", "C"},
				room:  Room{Name: "D"},
			},
			want: false,
		},
		{
			name: "Empty route, room not present",
			args: args{
				route: Route{},
				room:  Room{Name: "A"},
			},
			want: false,
		},
		{
			name: "Single room on route matches",
			args: args{
				route: Route{"A"},
				room:  Room{Name: "A"},
			},
			want: true,
		},
		{
			name: "Case-sensitive match fails",
			args: args{
				route: Route{"a", "b", "c"},
				room:  Room{Name: "A"},
			},
			want: false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := IsOnRoute(tt.args.route, tt.args.room); got != tt.want {
				t.Errorf("IsOnRoute() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestFindRoom(t *testing.T) {
	type args struct {
		rooms []Room
		name  string
	}
	tests := []struct {
		name string
		args args
		want int
	}{
		{
			name: "Room found at index 0",
			args: args{
				rooms: []Room{
					{Name: "Start"},
					{Name: "Middle"},
					{Name: "End"},
				},
				name: "Start",
			},
			want: 0,
		},
		{
			name: "Room found at index 2",
			args: args{
				rooms: []Room{
					{Name: "Start"},
					{Name: "Middle"},
					{Name: "End"},
				},
				name: "End",
			},
			want: 2,
		},
		{
			name: "Room not found",
			args: args{
				rooms: []Room{
					{Name: "Start"},
					{Name: "Middle"},
					{Name: "End"},
				},
				name: "Unknown",
			},
			want: -1,
		},
		{
			name: "Empty rooms list",
			args: args{
				rooms: []Room{},
				name:  "AnyRoom",
			},
			want: -1,
		},
		{
			name: "Case-sensitive match",
			args: args{
				rooms: []Room{
					{Name: "Start"},
					{Name: "Middle"},
					{Name: "End"},
				},
				name: "start",
			},
			want: -1,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := FindRoom(tt.args.rooms, tt.args.name); got != tt.want {
				t.Errorf("FindRoom() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestSharedRoom(t *testing.T) {
	type args struct {
		rt1 *Route
		rt2 *Route
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		{
			name: "Shared room exists in the middle",
			args: args{
				rt1: &Route{"X", "Y", "Z"},
				rt2: &Route{"M", "Y", "N"},
			},
			want: true,
		},
		{
			name: "No shared room",
			args: args{
				rt1: &Route{"A", "B", "C"},
				rt2: &Route{"X", "Y", "Z"},
			},
			want: false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := SharedRoom(tt.args.rt1, tt.args.rt2); got != tt.want {
				t.Errorf("SharedRoom() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestFindSeparates(t *testing.T) {
	type args struct {
		routes            []Route
		curCombo          []Route
		combosOfSeparates *[][]Route
		ind               int
		wg                *sync.WaitGroup
	}
	tests := []struct {
		name string
		args args
	}{
		{
			name: "Single route combination",
			args: args{
				routes: []Route{
					{"A", "B", "C"},
				},
				curCombo:          []Route{{"A", "B", "C"}},
				combosOfSeparates: &[][]Route{},
				ind:               0,
				wg:                &sync.WaitGroup{},
			},
		},
		{
			name: "Multiple routes with separation",
			args: args{
				routes: []Route{
					{"A", "B", "C"},
					{"D", "E", "F"},
					{"G", "H", "I"},
				},
				curCombo:          []Route{{"A", "B", "C"}},
				combosOfSeparates: &[][]Route{},
				ind:               0,
				wg:                &sync.WaitGroup{},
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			tt.args.wg.Add(1)
			DetermineSeparates(tt.args.routes, tt.args.curCombo, tt.args.combosOfSeparates, tt.args.ind, tt.args.wg)
			tt.args.wg.Wait()
		})
	}
}

func TestComboAvgLength(t *testing.T) {
	type args struct {
		combo []Route
	}
	tests := []struct {
		name string
		args args
		want float64
	}{
		{
			name: "Single route combo",
			args: args{
				combo: []Route{
					{"A", "B", "C"},
				},
			},
			want: 3,
		},
		{
			name: "Multiple routes with equal length",
			args: args{
				combo: []Route{
					{"A", "B", "C"},
					{"D", "E", "F"},
				},
			},
			want: 3,
		},
		{
			name: "Multiple routes with different lengths",
			args: args{
				combo: []Route{
					{"A", "B", "C"},
					{"D", "E", "F", "G"},
				},
			},
			want: 3.5,
		},
		{
			name: "Combo with varying lengths",
			args: args{
				combo: []Route{
					{"A", "B", "C"},
					{"D", "E"},
					{"F", "G", "H", "I"},
				},
			},
			want: 3,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := CombinationAverageLength(tt.args.combo); got != tt.want {
				t.Errorf("ComboAvgLength() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestShortCombos(t *testing.T) {
	type args struct {
		combosOfSeparates [][]Route
		routes            []Route
	}
	tests := []struct {
		name string
		args args
		want [][]Route
	}{
		{
			name: "Single route in combos of separates",
			args: args{
				combosOfSeparates: [][]Route{
					{{"A", "B", "C"}},
				},
				routes: []Route{
					{"A", "B", "C"},
				},
			},
			want: [][]Route{
				{{"A", "B", "C"}},
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := ShortCombination(tt.args.combosOfSeparates, tt.args.routes); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("ShortCombos() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestLowAverages(t *testing.T) {
	type args struct {
		combosOfSeparates [][]Route
	}
	tests := []struct {
		name string
		args args
		want [][]Route
	}{
		{
			name: "Combos with low averages",
			args: args{
				combosOfSeparates: [][]Route{
					{{"A", "B", "C"}},
					{{"D", "E"}},
					{{"F", "G", "H", "I"}},
				},
			},
			want: [][]Route{
				{{"D", "E"}},
			},
		},
		{
			name: "Single combo with low average",
			args: args{
				combosOfSeparates: [][]Route{
					{{"A", "B"}},
				},
			},
			want: [][]Route{
				{{"A", "B"}},
			},
		},
		{
			name: "Combos with different route lengths",
			args: args{
				combosOfSeparates: [][]Route{
					{{"A", "B", "C"}},
					{{"D", "E", "F"}},
					{{"G", "H"}},
				},
			},
			want: [][]Route{
				{{"G", "H"}},
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := LowAverages(tt.args.combosOfSeparates); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("LowAverages() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestIsSubset(t *testing.T) {
	type args struct {
		combo1 []Route
		combo2 []Route
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		{
			name: "combo1 is not a subset of combo2",
			args: args{
				combo1: []Route{{"A", "B", "C"}},
				combo2: []Route{{"D", "E"}, {"F", "G"}},
			},
			want: false,
		},
		{
			name: "combo1 is equal to combo2",
			args: args{
				combo1: []Route{{"A", "B", "C"}, {"D", "E"}},
				combo2: []Route{{"A", "B", "C"}, {"D", "E"}},
			},
			want: true,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := IsSubset(tt.args.combo1, tt.args.combo2); got != tt.want {
				t.Errorf("IsSubset() = %v, want %v", got, tt.want)
			}
		})
	}
}
