package functions

import "math"

func StandardDeviation(n []int) float64 {
	sd := math.Sqrt(Variance(n))
	return sd
}
