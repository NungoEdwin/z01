package utils

import (
	"fmt"
	"os"
	"strings"
)

/*
FindingColor function determines the appropriate ANSI escape code for a given color specified in a string.
The string can contain a color name, an RGB value, or a hexadecimal color code.
The function handles each type of color specification,
converts it to the corresponding ANSI escape code, and returns it.
*/
func FindingColor(s string) string {
	if !strings.HasPrefix(s, "--color=") {
		fmt.Println(`Usage: go run . [OPTION] [STRING]

EX: go run . --color=<color> <substring to be colored> "something"`)
		os.Exit(0)
	}
	colorWanted := ""
	for i, ch := range s {
		if ch == '=' {
			colorWanted = s[i+1:]

			break
		}
	}

	if strings.Contains(colorWanted, "rgb") {
		rgbarray := stringSplitter(colorWanted)
		for _, ch := range rgbarray {
			if ch > 255 {
				fmt.Println("Invalid RGB value;", ch)
				os.Exit(0)
			}
		}
		ansiNum := RGBtoAnsi(rgbarray[0], rgbarray[1], rgbarray[2])
		color := ansiColor(ansiNum)
		return color
	} else if strings.Contains(colorWanted, "#") {
		hexInt := HexToDec(colorWanted)
		ansiNum := RGBtoAnsi(hexInt[0], hexInt[1], hexInt[2])
		color := ansiColor(ansiNum)
		return color

	} else {
		color := color(colorWanted)
		if color == "" {
			fmt.Println(`Usage: go run . [OPTION] [STRING]

EX: go run . --color=<color> <substring to be colored> "something"`)
			os.Exit(0)
		}
		return color
	}
}
