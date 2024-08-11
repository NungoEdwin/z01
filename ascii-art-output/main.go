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
		// os.WriteFile("")

		flag.Visit(func(f *flag.Flag) {
			if f.Name == "output" {
				outputfile := fmt.Sprint(f.Value)

				os.WriteFile(outputfile, []byte(output), 0o644)

			}
		})

	}
}
