package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"

	"math_skills/functions"
)

func main() {
	if len(os.Args) != 2 || os.Args[1] != "data.txt" {
		fmt.Println("Usage :go run your-program.go data.txt")
		os.Exit(0)
	}
	Args := os.Args[1]
	file, err := os.ReadFile(Args)
	if err != nil {
		fmt.Println("Error reading file")
		os.Exit(0)
	}
	slices := strings.Split(string(file), "\n")
	var numslice []int
	for _, word := range slices {
		if word == "" {
			continue
		}
		num, err := strconv.Atoi(strings.TrimSpace(word))
		if err != nil {
			fmt.Println("Error converting to number")
			os.Exit(0)
		}
		numslice = append(numslice, num)
	}
	// avverage := int(math.Round(Average(numslice)))
	fmt.Printf("Average:%.0f\n", math.Round(functions.Average(numslice)))
	fmt.Printf("Median:%v\n", int(math.Round(functions.Median(numslice))))
	fmt.Printf("Variance: %.0f\n", math.Round(functions.Variance(numslice)))
	fmt.Printf("Standard Deviation:%.0f\n", math.Round(functions.StandardDeviation(numslice)))
}
