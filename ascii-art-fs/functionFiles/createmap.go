package asciiart

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
)

func CreateMap(filePaths string) (map[rune][]string, error) {
	// Open and read a file specified by the given file path(s), creating an ASCII art map.
	file, err := os.Open(filePaths)
	if err != nil {
		return nil, err
	}
	defer file.Close()
	// Check for empty file
	stat, err := file.Stat()
	if err != nil {
		return nil, err
	}
	if stat.Size() == 0 {
		return nil, fmt.Errorf("error: Character map file '%s' is empty", filePaths)
	}
	scanner := bufio.NewScanner(file)
	lines := []string{}
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	if len(lines) != 855 {
		fmt.Println("File is empty or tampered with")
		os.Exit(0)
	}
	err = scanner.Err()
	if err != nil {
		return nil, err
	}
	// Validate the file path and checks for empty files or non-text file extensions.
	if filepath.Ext(filePaths) != ".txt" {
		fmt.Println("Wrong extension, use .txt")
		return nil, err
	}
	// Parse the file line by line, constructing the ASCII art map.
	// Uses a map where the key is the ASCII character (rune) and the value is a slice of strings representing the lines of the ASCII art.
	characterMap := make(map[rune][]string)
	var currentChar rune = ' '
	for _, line := range lines {
		if len(line) == 0 { // Skip empty lines
			continue
		}
		if len(characterMap[currentChar]) == 8 {
			currentChar++
			characterMap[currentChar] = []string{}
		}
		characterMap[currentChar] = append(characterMap[currentChar], line)
	}
	// Returns the constructed map or an error if any occurs during file operations or map creation.
	return characterMap, nil
}
