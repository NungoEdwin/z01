package utils

// IndicesInArr function checks if a given integer num exists in a slice of integers arr.*/
func IndicesInArr(arr []int, num int) bool {
	for _, x := range arr {
		if x == num {
			return true
		}
	}
	return false
}
