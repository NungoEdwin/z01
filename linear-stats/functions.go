package main

import (
	"math"
)

func LinearRegression(xSlice, ySlice []float64) (float64, float64) {
	number := float64(len(xSlice))
	sumX, sumY, sumXY, sumX2 := 0.0, 0.0, 0.0, 0.0

	for i := 0; i < len(xSlice); i++ {
		sumX += xSlice[i]
		sumY += ySlice[i]
		sumXY += xSlice[i] * ySlice[i]
		sumX2 += xSlice[i] * xSlice[i]
	}

	// Slope (m)
	m := (number*sumXY - sumX*sumY) / (number*sumX2 - sumX*sumX)

	/*  y Intercept (c)
	y = mx + c
	hence:
	c = y - mx
	*/

	c := (sumY / number) - m*(sumX/number)
	return m, c
}

func PearsonCorrelation(xSlice, ySlice []float64) float64 {
	number := float64(len(xSlice))
	sumX, sumY, sumXY, sumX2, sumY2 := 0.0, 0.0, 0.0, 0.0, 0.0

	for i := 0; i < len(xSlice); i++ {
		sumX += xSlice[i]
		sumY += ySlice[i]
		sumXY += xSlice[i] * ySlice[i]
		sumX2 += xSlice[i] * xSlice[i]
		sumY2 += ySlice[i] * ySlice[i]
	}

	// Pearson correlation coefficient
	numerator := (number*sumXY - sumX*sumY)
	denominator := math.Sqrt((number*sumX2 - sumX*sumX) * (number*sumY2 - sumY*sumY))
	correlation := numerator / denominator

	return correlation
}
