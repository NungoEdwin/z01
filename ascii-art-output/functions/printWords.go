package functions

import (
	"fmt"
	"strings"
)

// Prints the respective Ascii Art characters
func PrintWords(input1 string, asciiFields []string, match string) string {
	input2 := strings.ReplaceAll(input1, "\n", "\\n")
	input := strings.Split(input2, "\\n")
	big := ""
	arr := make([]string, 8)

	for a, word := range input {
		if word == "" && a != len(input)-1 {

			big += "\n"
			continue
		} else if word == "" && a == len(input)-1 {
			continue
		}
		c := 0
		matchs := 0
		z := 0
		for j := 0; j < len(word); j++ {
			char := rune(word[j])
			for i := 0; i < 8; i++ {
				if !validChar(char) {
					return ""
				}
				startPoint := Start(int(rune(char)))
				matchs = strings.Index(input[a], match) + z
				if j >= matchs && j < matchs+len(match) && matchs != -1 && strings.ContainsRune(match, char) && strings.Contains(input[a], match) {
					if Color() == "" {
						return ""
					}
					arr[i] += (Color() + asciiFields[startPoint+i] + "\033[0m")
					c++
				} else {
					arr[i] += (asciiFields[startPoint+i])
				}
			}

			if c/8 == len(match) {
				if j < len(word)-1 {
					input[a] = word[j+1:]
				}
				z = j + 1
				c = 0
			}

		}
		if a != len(input)-1 {
			big += strings.Join(arr, "\n") + "\n"
			arr = make([]string, 8)

		} else {
			big += strings.Join(arr, "\n")
		}

	}

	return big
}

// Determines starting position of the character
func Start(s int) int {
	pos := int(s-32)*9 + 1
	return pos
}

// Checks the validity of the character
func validChar(s rune) bool {
	if !(s >= ' ' && s <= '~') {
		fmt.Println("Error:" + string(s) + " " + "is not valid character")
		return false
	}
	return true
}
