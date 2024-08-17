package utils

import "strings"

/*IsValidExtensions checkes whether the output file is a correctly formatted text file*/
func IsValidExtension(s string) bool {
	splitRes := strings.Split(s, ".txt")
	if len(splitRes) != 2 || splitRes[0] == "" {
		return false
	}
	return true
}
