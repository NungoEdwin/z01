package functions

import (
	"fmt"
	"os"
	"strings"
)

// FileChoice function reads the standard.txt banner file and returns its contents
func FileChoice(osArgs []string) []string {
	banner := "standard.txt"

	if len(os.Args) == 3 && !(strings.HasPrefix(os.Args[1], "--color")) {
		banner = osArgs[2] + ".txt"
	} else if len(os.Args) == 4 && strings.HasPrefix(os.Args[1], "--color") {
		if os.Args[3] == "shadow" || os.Args[3] == "thinkertoy" || os.Args[3] == "standard" {
			banner = os.Args[3] + ".txt"
		}
	} else if len(os.Args) == 5 && strings.HasPrefix(os.Args[1], "--color") {
		if os.Args[4] == "shadow" || os.Args[4] == "thinkertoy" || os.Args[4] == "standard" {
			banner = os.Args[4] + ".txt"
		} else {
			fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
			return []string{}
		}
	}

	file, err := os.ReadFile(banner)
	if err != nil {
		fmt.Println(err)
		return []string{}
	}
	asciiFields := strings.Split(string(file), "\n")

	if len(string(file)) == 0 {
		fmt.Println("Empty file")
		return []string{}
	}
	if len(asciiFields) != 856 {
		fmt.Println("The File Banner used has been tampered with")
		return []string{}
	}
	return asciiFields
}
