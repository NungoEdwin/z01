package main

import (
	"testing"
)

func TestLinearProgression(t *testing.T) {
	expectedm, expectedy := 4.5, 50.833333333333336
	m, y := LinearRegression([]float64{0.0, 1.0, 2.0}, []float64{34.0, 89.0, 43.0})
	if m != expectedm || y != expectedy {
		t.Fatal("Wrong result")
	}

}
func TestPearsonCorrelation(t *testing.T) {
	expected := 0.152535069835373
	r := PearsonCorrelation([]float64{0.0, 1.0, 2.0}, []float64{34.0, 89.0, 43.0})
	if r != expected {
		t.Fatal("Wrong result")
	}
}
