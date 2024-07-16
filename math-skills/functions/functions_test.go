package functions

import (
	"testing"
)

func TestAverage(t *testing.T) {
	data := []int{10, 15, 5}
	result := Average(data)
	if result != 10 {
		t.Fatal("Wrong Average")
	}
}

func TestMedian(t *testing.T) {
	data := []int{10, 15, 5}
	result := Median(data)
	if result != 10 {
		t.Fatal("wrong Median")
	}
}

func TestVariance(t *testing.T) {
	data := []int{10, 15, 5}
	result := Variance(data)
	if !(result > 16 && result < 17) {
		t.Fatal("wrong Variance")
	}
}

func TestStandardDeviation(t *testing.T) {
	data := []int{10, 15, 5}
	result := StandardDeviation(data)
	if !(result > 4 && result < 5) {
		t.Fatal(" Wrong Standard Variation")
	}
}
