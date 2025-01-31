package asciiart

import (
	"errors"
	"strings"
)

// GenerateAsciiArt generates ASCII art representation of the given text
// using the specified banner type. It returns the ASCII art as a string
// or an error if there's any issue.

func GenerateAsciiArt(text string, characters []string) (string, error) {
	var asciiArt strings.Builder
	text = strings.ReplaceAll(text, "\r\n", "\n")
	lines := strings.Split(text, "\n")
	for _, line := range lines {
		if line != "" {
			for i := 1; i <= 8; i++ {
				for _, char := range line {
					// Convert the ASCII code of the character to an index (ASCII code - 32).
					index := int(char-32) * 9
					// Check if the calculated index is within the bounds of the characters slice.
					if index+i >= len(characters) || index < 0 {
						return "", errors.New("cannot handle non latin characters")
					}
					asciiArt.WriteString(characters[index+i])
				}
				asciiArt.WriteString("\n")
			}
		} else {
			asciiArt.WriteString("\n")
		}
	}
	return asciiArt.String(), nil
}
