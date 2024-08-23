package printingasciipackage

import (
	"fmt"
	"os"
	"strings"

	"ascii-web-art/mapPackage"
)

// PrintingAscii function reads input text,gets the pattern  and converts it to ascii art.
func PrintingAscii(text, patternFile, color, subString string) string {
	text = strings.ReplaceAll(text, "\n", "\\n")
	res := ""
	for i := 0; i < len(text); {
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 'a' {
			fmt.Fprintf(os.Stderr, "error: Special character %v%v is not supported \n", string(text[i]), string(text[i+1]))
			// os.Exit(1)
			return "1"
		}
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 'b' {
			l := len(text) - 2
			if i == 0 {
				text = text[i+2:]
			} else if i == l {
				text = text[:l]
			} else {
				text = text[:i-1] + text[i+2:]
				i = 0
			}
			continue
		}
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 't' {
			text = text[:i] + "   " + text[i+2:]
		}
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 'v' {
			fmt.Fprintf(os.Stderr, "error: Special character %v%v is not supported \n", string(text[i]), string(text[i+1]))
			// os.Exit(1)
			return "1"
		}
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 'f' {
			fmt.Fprintf(os.Stderr, "error: Special character %v%v is not supported \n", string(text[i]), string(text[i+1]))
			// os.Exit(1)
			return "1"
		}
		if i+1 < len(text) && text[i] == '\\' && text[i+1] == 'r' {
			fmt.Fprintf(os.Stderr, "error: Special character %v%v is not supported \n", string(text[i]), string(text[i+1]))
			// os.Exit(1)
			return "1"
		}
		if i+1 < len(text) && text[i] > 127 {
			fmt.Fprintln(os.Stderr, "error: Only ascii characters are allowed")
			// os.Exit(1)
			return "1"
		}
		i++
	}
	lines := strings.Split(text, "\\n")
	asciiMap := mapPackage.AsciiMapping(patternFile)
	if len(asciiMap) == 0 {
		return ""
	}

	for _, word := range lines { // case of multiple newlines
		for n := 0; n < 8; n++ {
			for _, ch := range word {
				res += asciiMap[ch][n] 
			}
			res += "\n"
		}
	}

	return res
}
