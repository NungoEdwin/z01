package functions

func Median(n []int) float64 {
	median := 0.0
	if len(n) > 0 {
		for i := 0; i < len(n); i++ {
			for k := 0; k < len(n); k++ {
				if (i < len(n)) && (n[i] > n[k]) {
					temp := n[i]
					n[i] = n[k]
					n[k] = temp
				}
			}
		}

		if len(n)%2 == 0 {
			median = float64(n[len(n)/2]+n[len(n)/2-1]) / 2.0
		} else {
			median = float64(n[len(n)/2])
		}
	}
	return median
}
