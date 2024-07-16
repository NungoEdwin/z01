package functions

func Average(n []int) float64 {
	var sum float64
	Average := 0.0
	if len(n) != 0 {
		for _, num := range n {
			sum += float64(num)
		}

		Average = (sum / float64(len(n)))
	}
	return Average
}
