package utils

/*IsValid functions checks if the output file name is same as the banner files */
func IsValid(s string) bool {
	if s == "standard.txt" || s == "shadow.txt" || s == "thinkertoy.txt" {
		return true
	}
	return false
}
