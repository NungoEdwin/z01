package functions

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

// var args string
// InputArgs function processes command-line arguments
func InputArgs(osArgs []string) (string, string, int) {
	var args string
	var match string
	color := flag.String("color", "\033[0m", "Provide color code to be used for coloring.")

	// if len(os.Args) > 2 && !strings.HasPrefix(os.Args[1], "--color="){
	// 	fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
	// 	return []string{}, "", 0
	// }

	flag.Parse()

	// Variable to track if the flag was set
	var nameSet bool
	var flagSet bool = false
	// Enforce the flag format to be used to be --output=<filename.txt>
	flag.Visit(func(f *flag.Flag) {
		if f.Name == "color" {
			flagSet = true
			result := strings.Replace(os.Args[1], *color, "", 1)
			if !(result == "--color=") {
				nameSet = false
			} else {
				nameSet = true
			}
		} else {
			fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
			return
		}
	})

	if flagSet {
		if len(flag.Args()) > 2 || len(flag.Args()) < 1 || !nameSet {
			fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
			return "", "", 0
		} else if len(flag.Args()) == 2 {
			match = flag.Arg(0)
			args = flag.Arg(1)
		} else if len(flag.Args()) == 1 {
			match = flag.Arg(0)
			args = flag.Arg(0)
		}
	} else {
		// if len(os.Args) != 2 {
		// 	 fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
		// 	return []string{}, "", 0
		// }
		args = os.Args[1]
	}
	// var input []string

	// args = strings.ReplaceAll(args, "\n", "\\n")
	args = strings.ReplaceAll(args, "\\t", " ")
	// input := strings.Split(args, "\\n")
	// match = strings.ReplaceAll(match, "\\n", "\n")
	// if input[0] == "" && len(input) == 1 {
	// 	return "", "", 0
	// } else if input[0] == "" && input[1] == "" && len(input) == 3 {
	// 	fmt.Println()
	// 	fmt.Println()
	// 	return "", "", 0
	// }

	return args, match, len(osArgs)
}
