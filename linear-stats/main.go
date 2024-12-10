package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	if len(os.Args) != 2 {
		fmt.Println("USAGE:go run your-program data.txt")
		os.Exit(0)
	}
	file, err := os.ReadFile(os.Args[1])
	if err != nil {
		fmt.Println("Error reading file")
		os.Exit(0)
	}
	slices := strings.Split(string(file), "\n")
	var numsliceY []float64
	var numsliceX []float64
	for _, word := range slices {
		if word == "" {
			continue
		}
		num, err := strconv.ParseFloat(strings.TrimSpace(word), 64)
		if err != nil {
			fmt.Println("Error converting to number")
			os.Exit(0)
		}
		numsliceY = append(numsliceY, num)
	}
	for i := 0; i < len(numsliceY); i++ {
		numsliceX = append(numsliceX, float64(i))
	}
	corr := PearsonCorrelation(numsliceX, numsliceY)
	m, c := LinearRegression(numsliceX, numsliceY)

	fmt.Printf("Linear Regression Line: y = %.06fx + %.06f\n", m, c)
	fmt.Printf("Pearson Correlation Coefficient: %.10f\n", corr)

}
