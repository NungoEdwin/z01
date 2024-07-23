package main

import (
	"os"

	"ascii_art_color/functions"
)

func main() {

	asciiFields := functions.FileChoice(os.Args)
	if len(asciiFields) == 0 {
		return
	}
	input, match, inputLen := functions.InputArgs(os.Args)

	if inputLen == 0 {
		return
	}

	functions.PrintWords(input, asciiFields, match)
}
