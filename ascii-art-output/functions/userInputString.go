package functions

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

// InputArgs function processes command-line arguments
func InputArgs(osArgs []string) (string, string, int) {
	var (
		color  = flag.String("color", "\033[0m", "Provide color code to be used for coloring.")
		output = flag.String("output", "banner.txt", "Provide the output file")
	)

	var args string
	var match string = ""
	Nflags := 0
	Nflags++

	flag.Parse()
	if strings.HasPrefix(*color, "--color") && strings.HasPrefix(*output, "--output") {
		Nflags = 2
	}

	if len(osArgs) > 6 || len(osArgs) < 2 {
		fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
		os.Exit(0)
	}

	var flagSet bool = false
	flagset2 := false

	flag.Visit(func(f *flag.Flag) {
		if f.Name == "color" {
			if !flagPattern(osArgs, "--color") {
				fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> something")
				os.Exit(0)
			}
			flagSet = true
		} else if f.Name == "output" {
			if !flagPattern(osArgs, "--output") {
				fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
				os.Exit(0)
			}
			flagset2 = true
		} else {
			fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
			return
		}
	})
	// color is set(flagSet) and output(!flagSet) is not set respectively
	if flagSet && !flagset2 {
		if len(flag.Args()) > 3 || len(flag.Args()) < 1 {
			fmt.Println("Usage: go run . [OPTION] [STRING]\n\nEX: go run . --color=<color> <substring to be colored> something")
			return "", "", 0
		} else if len(flag.Args()) == 2 {
			match = flag.Arg(0)
			args = flag.Arg(1)
		} else if len(flag.Args()) == 1 {
			match = flag.Arg(0)
			args = flag.Arg(0)
		} else if len(flag.Args()) == 3 {
			match = flag.Arg(0)
			args = flag.Arg(1)
		}
	} else if !flagSet && flagset2 {
		if len(flag.Args()) > 2 || len(flag.Args()) < 1 {
			fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
			return "", "", 0
		} else if len(flag.Args()) == 2 {
			args = flag.Arg(0)
		} else if len(flag.Args()) == 1 {
			args = flag.Arg(0)
		}
	} else if flag.NFlag() == 2 {
		if len(flag.Args()) == 0 {
			fmt.Println("Usage: go run . [OPTION] [STRING] [BANNER]\n\nEX: go run . --output=<fileName.txt> something standard")
			return "", "", 0
		}
		if len(os.Args) == 4 {
			args = flag.Arg(0)
			match = flag.Arg(0)

		} else {

			args = flag.Arg(1)
			match = flag.Arg(0)

		}
	} else {
		args = os.Args[1]
	}

	args = strings.ReplaceAll(args, "\\t", " ")

	return args, match, len(osArgs)
}

func flagPattern(s []string, flagord string) bool {
	for _, arg := range s {
		if strings.HasPrefix(arg, flagord) {
			return true
		}
	}
	return false
}
