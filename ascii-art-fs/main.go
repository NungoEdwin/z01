package main

import (
	"fmt"
	"os"
	"strings"

	asciiart "asciiart/functionFiles"
)

func main() {
	// Check if command-line arguments are appropriate, providing usage instructions if not.
	if len(os.Args) > 3 || len(os.Args) == 1 {
		fmt.Println("Usage: go run . [STRING] [BANNER]\n\nEX: go run . something standard")
		return
	}
	// Validate input string for printable characters.
	input := os.Args[1]
	if !asciiart.IsPrintable(input) {
		fmt.Println("Error: Input string contains non-printable characters")
		return
	}
	// Handle special cases for empty input or newline character.
	if input == "" {
		return
	}
	if input == "\\n" {
		fmt.Println()
		return
	}
	// Determine the file path based on the number of command-line arguments and their values.
	var filePath string
	var flag string
	if len(os.Args) == 2 {
		filePath = "standard.txt"
	}
	if len(os.Args) == 3 {
		if strings.HasSuffix(os.Args[2], ".txt") {
			slicepath := strings.Split(os.Args[2], ".")

			flag = slicepath[0]
		} else {
			flag = os.Args[2]
		}

		if flag == "shadow" {
			filePath = "shadow.txt"
		} else if flag == "thinkertoy" {
			filePath = "thinkertoy.txt"
		} else if flag == "standard" {
			filePath = "standard.txt"
		} else {
			filePath = flag + ".txt"
		}
	}

	// Read the ASCII art map from the specified file.
	characterMap, err := asciiart.CreateMap(filePath)
	if err != nil {
		fmt.Println("Error reading map:", err)
		return
	}
	// Display ASCII art corresponding to the input string using the provided map
	if len(characterMap) == 0 {
		fmt.Println("File is empty")
		return
	}
	asciiart.DisplayAsciiArt(characterMap, input)
}
