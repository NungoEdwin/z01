package main

import (
	"testing"

	ascii "ascii_art_color/functions"
)

func TestInputArgs(t *testing.T) {
	t.Run("len", func(t *testing.T) {
		_, lenos := ascii.InputArgs([]string{"main.go", "hekko\n", "-t"})
		// fmt.Println(lenos)
		if lenos < 2 || lenos > 3 {
			t.Fatal("The test has failed concerning length of args")
		}
	})
	t.Run("string", func(t *testing.T) {
		inputslice, _ := ascii.InputArgs([]string{"main.go", "hekko\n"})
		expected := []string{"hekko", ""}
		if len(inputslice) != len(expected) {
			t.Fatal("Not matching output")
		}
	})
}

func TestFileChoice(t *testing.T) {
	testCases := []struct {
		name   string
		osArgs []string
	}{
		{
			name:   "Test Thinkertoy File",
			osArgs: []string{".", "h", "-t"},
		},
		{
			name:   "Test Shadow File",
			osArgs: []string{".", "h", "-sh"},
		},
		{
			name:   "Test Standard File",
			osArgs: []string{".", "h"},
		},
	}

	for _, tc := range testCases {
		t.Run("flags", func(t *testing.T) {
			asciif := ascii.FileChoice(tc.osArgs)
			if len(asciif) < 1 {
				t.Fatal("error")
			}
		})
	}
}
