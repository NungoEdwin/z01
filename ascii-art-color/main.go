package main

import (
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

	functions.PrintWords(input, asciiFields, match)
}
