package main

import (
	"flag"
	"fmt"
	"os"

	"ascii_art_color/functions"
)

func main() {
	input, match, inputLen := functions.InputArgs(os.Args)

	if inputLen == 0 {
		return
	}

	asciiFields := functions.FileChoice(os.Args)
	if len(asciiFields) == 0 {
		return
	}
	output := functions.PrintWords(input, asciiFields, match)
	if output == "" {
		return
	} else {
		fmt.Println(output)

		flag.Visit(func(f *flag.Flag) {
			if f.Name == "output" {
				outputfile := fmt.Sprint(f.Value)
				if !(outputfile == "shadow.txt" || outputfile == "standard.txt" || outputfile == "thinkertoy.txt") {
					os.WriteFile(outputfile, []byte(output), 0o644)
				} else {
					fmt.Println("Cant write into a banner file")
				}

			}
		})

	}
}
