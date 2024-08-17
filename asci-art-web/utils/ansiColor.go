package utils

import "strconv"

/*
ansiColor function generates an ANSI escape code string
that sets the text color based on a given 256-color code.
*/
func ansiColor(asciiNum int) string {
	return "\033[38;5;" + strconv.Itoa(asciiNum) + "m "
}
