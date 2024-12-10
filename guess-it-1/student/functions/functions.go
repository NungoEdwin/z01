package functions

import "math"

func Average(n []float64) float64 {
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

func StandardDeviation(n []float64) float64 {
	var varslice []float64
	number := 0.0
	variance := 0.0
	if len(n) > 0 {
		for _, num := range n {
			varslice = append(varslice, math.Pow(float64(num)-(Average(n)), 2))
		}
		for _, num := range varslice {
			number += num
		}
		variance = number / float64((len(varslice)))
	}
	return math.Sqrt(variance)
}
