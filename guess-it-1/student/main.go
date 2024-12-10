package main

import (
	"bufio"
	"fmt"
	"guess-it-1/functions"
	"os"
	"strconv"
)

func main() {
	data := []float64{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		str := scanner.Text()
		//fmt.Println(str)
		ints, err := strconv.ParseFloat(str, 64)
		if err != nil {
			fmt.Println("errror converting")
			continue
		}
		data = append(data, ints)
		if len(data) >= 2 {
			// fmt.Println(data)
			average := functions.Average(data[len(data)-2:])
			Sd := functions.StandardDeviation(data[len(data)-2:])
			upperlimit := average + (2.5 * Sd)
			lowerlimit := average - (2.5 * Sd)
			//fmt.Println(average, Sd)
			fmt.Printf("%.0f %.0f\n", lowerlimit, upperlimit)

		}

	}
}
