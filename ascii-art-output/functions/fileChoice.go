package functions

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

// FileChoice function reads the standard.txt banner file and returns its contents
func FileChoice(osArgs []string) []string {
	banner := "standard.txt"

	if len(osArgs) > 6 || len(osArgs) < 2 {
		fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
		os.Exit(0)
	}

	flag1 := false
	flag2 := false
	flag.Visit(func(f *flag.Flag) {
		if f.Name == "output" {
			flag1 = true
		}
		if f.Name == "color" {
			flag2 = true
		}
	})

	if len(os.Args) == 3 && flag.NFlag() == 0 {
		banner = osArgs[2] + ".txt"
	} else if len(os.Args) > 3 && flag.NFlag() == 0 {

		fmt.Println("Usage: go run . [STRING] [BANNER]\n\nEX: go run . something standard")
		return []string{}
	} else if flag.NFlag() == 1 && flag1 && len(osArgs) == 4 {
		banner = flag.Arg(1) + ".txt"
	} else if len(os.Args) == 5 && flag2 && flag.NFlag() == 1 {
		if os.Args[4] == "shadow" || os.Args[4] == "thinkertoy" || os.Args[4] == "standard" {
			banner = os.Args[4] + ".txt"
		} else {
			fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> \"something\"")
			return []string{}
		}
	} else if len(os.Args) < 2 {
		fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
		return []string{}
	} else if flag.NFlag() == 2 && len(osArgs) == 6 {
		banner = os.Args[5] + ".txt"
		if !(banner == "standard.txt" || banner == "shadow.txt" || banner == "thinkertoy.txt") {
			fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
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
