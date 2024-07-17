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
