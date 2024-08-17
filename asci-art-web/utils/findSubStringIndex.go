package utils

/*
FindSubStringIndex function finds all occurrences
of a substring within a given string and returns a slice of the
indices where each character of the substring occurs in the main string.
*/
func FindSubStringIndex(mainString, subString string) []int {
	indices := []int{}
	for i := 0; i <= len(mainString)-len(subString); i++ {
		if mainString[i:i+len(subString)] == subString {
			for j := i; j < i+len(subString); j++ {
				indices = append(indices, j)
			}
		}
	}

	return indices
}
