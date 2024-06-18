package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
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
	//avverage := int(math.Round(Average(numslice)))
	fmt.Printf("Average:%.0f\n", math.Round(Average(numslice)))
	fmt.Printf("Median:%.0f\n", math.Round(Median(numslice)))
	fmt.Printf("Variance: %.0f\n", math.Round(Variance(numslice)))
	fmt.Printf("Standard Deviation:%.0f\n", math.Round(StandardDeviation(numslice)))
}

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

func Variance(n []int) float64 {
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
	return variance
}

func StandardDeviation(n []int) float64 {
	sd := math.Sqrt(Variance(n))
	return sd
}
